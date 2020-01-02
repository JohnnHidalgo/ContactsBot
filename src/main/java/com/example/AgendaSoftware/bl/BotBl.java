package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.ChatRepository;
import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.dao.UserRepository;
import com.example.AgendaSoftware.domain.Chat;
import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BotBl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotBl.class);

    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private ChatRepository chatRepository;
    private ContactRepository contactRepository;
    public Boolean firstMessage = true;
    public Boolean registerFlag = false;
    public Boolean deleteFlag = false;
    public Boolean updateFlag = false;
    public Boolean addNumberFlag = false;
    public int registerCounter =0;
    MessageBl messageBl;

    @Autowired
    public BotBl(UserRepository userRepository, PhoneRepository phoneRepository, ChatRepository chatRepository, ContactRepository contactRepository,MessageBl messageBl) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.chatRepository = chatRepository;
        this.contactRepository = contactRepository;
        this.messageBl = messageBl;
    }

    public void processUpdateMesage(Update update,SendMessage message, SendPhoto photo){
        LOGGER.info("RECIBIENDO UPDATE en SEND MESSAGE",update);
        User user = initUser(update.getMessage().getFrom());
        message.setChatId(update.getMessage().getChatId());
        photo.setChatId(update.getMessage().getChatId());
        coninueChatWithUser(update, user, message,photo);
    }

    public void coninueChatWithUser(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto) {
        Chat lastMessage = chatRepository.findLastChatByUserId(user.getIdUser());
        String messageInput = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        String messageTextReceived = update.getMessage().getText();
        LOGGER.info("Ultimo mensaje "+update.getMessage().getText());
        String imageFile = null;
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        sendMessage.setChatId(chatId);

        if (lastMessage == null) {
            sendMessage.setChatId(chatId)
                    .setText("DEFAULT por null");
        }
        else {
            if(!update.getMessage().hasPhoto()&& (messageInput.equals("Menú Principal") || messageInput.equals("Cancelar")) ){
                registerCounter=0;
                registerFlag=false;
                deleteFlag = false;
                updateFlag = false;
                addNumberFlag=false;
                messageBl.principalMenu(update, user,sendMessage,sendPhoto);
            }
            if(registerFlag == true){
                if (registerFlag = true && firstMessage==false&& !update.getMessage().hasPhoto()){
                    LOGGER.info(("Contador [RegisterCounterValue]"+registerCounter));
                    messageBl.registerConact(update, user,sendMessage,sendPhoto, registerFlag, registerCounter);
                    registerCounter++;
                }else if(registerFlag = true && firstMessage==false && update.getMessage().hasPhoto()){
                    LOGGER.info(("Contador [RegisterCounterValue]"+registerCounter));
                    messageBl.registerConact(update, user,sendMessage,sendPhoto, registerFlag, registerCounter);
                    registerCounter++;
                }
            }else if(deleteFlag == true && firstMessage==false){
                messageBl.deleteContact(update,user,sendMessage,sendPhoto);
            }else if(updateFlag == true && firstMessage==false){
                LOGGER.info("Estamos en Actualizar con botones");
                messageBl.updateContact(update,user,sendMessage,sendPhoto);
            }

            else if (messageInput.equals("Inicio") || firstMessage==false){
                firstMessage = false;
                try {
                    switch(messageInput) {
                        case "Inicio":
                            messageBl.startConversation(update, user,sendMessage,sendPhoto);
                            break;
                        case "Registrar Contacto":
                            LOGGER.info(("Contador [RegisterCounterValue]"+registerCounter));
                            registerFlag = true;
                            messageBl.startRegisterContact(update, user,sendMessage,sendPhoto, registerFlag, registerCounter);
                            break;
                        case "Menú Principal":
                            messageBl.principalMenu(update, user,sendMessage,sendPhoto);
                            break;
                        case "Enlistar":
                            deleteFlag= false;
                            updateFlag = false;
                            addNumberFlag = false;
                            messageBl.listContact(update, user,sendMessage,sendPhoto);
                            break;
                        case "Buscar":
                            messageBl.findContact(update, user,sendMessage,sendPhoto);
                            break;
                        case "Información":
                            messageBl.infoApp(update, user,sendMessage,sendPhoto);
                            break;

                        case "Eliminar Contacto":
                            deleteFlag = true;
                            messageBl.startDeleteContact(update, user,sendMessage,sendPhoto);
                            break;
                        case "Actualizar Contacto":
                            updateFlag = true;
                            messageBl.startUpdateContact(update,user,sendMessage,sendPhoto);


//                        case "Buscar":
//                            messageBl.findContact(update, user,sendMessage,sendPhoto);
//                            User user1 = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
//                            List<Contact> contactList = contactRepository.findAllByIdUserContact(user1);

//                            List<Contact> contactList = new ArrayList<>();
//                            contactList = messageBl.listaDeContactos(sendMessage,messageTextReceived);
//
//                            LOGGER.info(contactList.get(0).getFirstName());
//
//                            contactList.size();
//                            sendMessage.setChatId(chatId)
//                                    .setText(contactList.get(0).getFirstName()+"\n"+ contactList.get(0).getSecondName()+"\n"+contactList.get(0).getMail()+"\n"+
//                                            contactList.get(1).getFirstName()+"\n"+ contactList.get(1).getSecondName()+"\n"+contactList.get(1).getMail());
//                            break;

//                        default:
//                            sendMessage.setChatId(chatId)
//                                    .setText("No lo entiendo\n");
//                            row.add("Soy Docente");
//                            row.add("Soy Estudiante");
//                            keyboard.add(row);
//                            keyboardMarkup.setKeyboard(keyboard);
//                            sendMessage.setReplyMarkup(keyboardMarkup);
//                            break;
                    }
                } catch (NumberFormatException nfe){
                    sendMessage.setChatId(chatId)
                            .setText("DEFAULT");
                }
            }
            else {
                sendMessage.setChatId(chatId)
                        .setText("Hola, para empezar el bot por favor presiona el boton Inicio");
                row.add("Inicio");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);
            }
        }

        Chat chat = new Chat();
        chat.setIdUserChat(user);
        chat.setInMessage(update.getMessage().hasPhoto()?"User send photo":update.getMessage().getText());
        chat.setOutMessage("outMessage");
        chat.setDateMessage(new Date());
        chat.setTxDate(new Date());
        chat.setTxUser(user.getIdUser().toString());
        chat.setTxHost(update.getMessage().getChatId().toString());

        chatRepository.save(chat);
    }

    private User initUser(org.telegram.telegrambots.meta.api.objects.User user){
        boolean result = false;
        User userBotBl = userRepository.findByIdUserbot(user.getId().toString());

        if(userBotBl == null){
            userBotBl = new User();
            userBotBl.setIdUserbot(user.getId().toString());
            userBotBl.setName(user.getFirstName());
            userBotBl.setLastName(user.getLastName());
            userBotBl.setTxHost("localhost");
            userBotBl.setTxDate(new Date());
            userRepository.save(userBotBl);
        }
        return userBotBl;
    }

}
