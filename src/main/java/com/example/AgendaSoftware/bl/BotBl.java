package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.ChatRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.dao.UserRepository;
import com.example.AgendaSoftware.domain.Chat;
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
    public  Boolean firstMessage = true;


    @Autowired
    public BotBl(UserRepository userRepository, PhoneRepository phoneRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.chatRepository = chatRepository;
    }


    public List<String> processUpdateMesage(Update update, SendMessage message, SendPhoto photo) {
        LOGGER.info("RECIBIENDO UPDATE en SEND MESSAGE",update);
        List<String> chatResponse = new ArrayList<>();
        User user = initUser(update.getMessage().getFrom());
        message.setChatId(update.getMessage().getChatId());
        photo.setChatId(update.getMessage().getChatId());
        coninueChatWithUser(update, user, message,photo);
        return chatResponse;

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
                try {
                    switch(messageInput) {
                        case "Men":
                            LOGGER.info("ENTRO A PRUEBA DE MULTIMENSAJE");
                            sendMessage.setChatId(chatId)
                                    .setText("MULTIMENSAJE");
                            row.add("Comenzar");
                            row.add("Información");
                            keyboard.add(row);
                            keyboardMarkup.setKeyboard(keyboard);
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            imageFile = "https://image.shutterstock.com/z/stock-vector-bienvenido-welcome-spanish-text-lettering-vector-illustration-1050015260.jpg";
                            sendPhoto.setChatId(chatId)
                                    .setPhoto(imageFile);
                            break;
                        case "/start":
                            imageFile = "https://image.shutterstock.com/z/stock-vector-bienvenido-welcome-spanish-text-lettering-vector-illustration-1050015260.jpg";
                            sendPhoto.setChatId(chatId)
                                    .setPhoto(imageFile);
                            sendMessage.setChatId(chatId)
                                    .setText("Seleccione una opción por favor\nComenzar\nInformacion");
                            row.add("Comenzar");
                            row.add("Información");
                            keyboard.add(row);
                            keyboardMarkup.setKeyboard(keyboard);
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            break;
                        case "Información":
                            imageFile = "https://pngimage.net/wp-content/uploads/2018/06/informaci%C3%B3n-png-1.png";
                            sendPhoto.setChatId(chatId)
                                    .setPhoto(imageFile);
                            sendMessage.setChatId(chatId)
                                    .setText("Somos una plataforma para crear test interactivos! \nLos docentes pueden crear test para enviarlos a sus alumnos y ver la puntuación de cada alumno \n ");
                            break;
                        case "Comenzar":
                            sendMessage.setChatId(chatId)
                                    .setText("Bienvenido!!\nEres Docente o Estudiante");
                            row.add("Soy Docente");
                            row.add("Soy Estudiante");
                            keyboard.add(row);
                            keyboardMarkup.setKeyboard(keyboard);
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            break;
                        default:
                            sendMessage.setChatId(chatId)
                                    .setText("No lo entiendo\n");
                            row.add("Soy Docente");
                            row.add("Soy Estudiante");
                            keyboard.add(row);
                            keyboardMarkup.setKeyboard(keyboard);
                            sendMessage.setReplyMarkup(keyboardMarkup);
                            break;
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
        chat.setOutMessage(sendMessage.getText());
        chat.setDateMessage(new Date());
        chat.setTxDate(new Date());
        chat.setTxUser(user.getIdUser().toString());
        chat.setTxHost(update.getMessage().getChatId().toString());

        // Guardamos en base dedatos
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
