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
import java.util.Optional;

@Service
public class BotBl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotBl.class);

    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private ChatRepository chatRepository;
    private ContactRepository contactRepository;
    public  Boolean firstMessage = true;
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
        } else {
            if (messageInput.equals("/start") || firstMessage==false){
                firstMessage = false;
                setModulesMessages(update,sendMessage,messageTextReceived);
                try {
                    switch(messageInput) {
                        case "/start":
                            imageFile = "https://mainvayne123.neocities.org/bienvenido.png";
                            sendPhoto.setChatId(chatId)
                                    .setPhoto(imageFile);
                            sendMessage.setChatId(chatId)
                                    .setText("Que gusto verte denuevo!\nSeleciona una opcion por favor");
                            row.add("Registrar Contacto");
                            row.add("Buscar");
                            keyboard.add(row);
                            keyboardMarkup.setKeyboard(keyboard);
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            break;

                        case "Registrar Contacto":
                            messageBl.setEntra_a_registro_docente(true);
                            imageFile = "https://i2.wp.com/mundialdecruceros.com/wp-content/uploads/2019/07/Contacto.png?fit=200%2C238&ssl=1";
                            sendMessage.setChatId(chatId)
                                    .setText("Vamos a registrar un nuevo contacto \nIngresa el nombre por favor");
                            sendPhoto.setChatId(chatId)
                                    .setPhoto(imageFile);
                            break;
                        case "Buscar":
                            List<Contact> contactList = new ArrayList<>();
                            contactList = messageBl.listaDeContactpos(sendMessage,messageTextReceived);

                            LOGGER.info(contactList.get(0).getFirstName());

                            contactList.size();
                            sendMessage.setChatId(chatId)
                                    .setText(contactList.get(0).getFirstName()+"\n"+ contactList.get(0).getSecondName()+"\n"+contactList.get(0).getMail());
                            break;

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
                        .setText("Hola, para empezar el bot por favor escribe /start");
            }
        }

        Chat chat = new Chat();
        chat.setIdUserChat(user);
        chat.setInMessage(update.getMessage().getText());
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


    private void setModulesMessages(Update update,SendMessage sendMessage,String messageTextReceived){
        if(messageBl.isEntra_a_registro_docente()){
//            try{
//                LOGGER.info("MODULESMESSAGE"+messageBl.entraRegistroDocente(sendMessage,messageTextReceived));

            sendMessage.setText(messageBl.entraRegistroDocente(sendMessage,messageTextReceived));
//            }
//            catch (NullPointerException e){
//                sendMessage.setText("Devuelve Nulo");
//            }
        }
    }




}
