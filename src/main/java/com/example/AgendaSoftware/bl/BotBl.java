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
import org.telegram.telegrambots.meta.api.objects.Update;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class BotBl {

    private static final Logger LOGGER = LoggerFactory.getLogger(BotBl.class);

    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private ChatRepository chatRepository;


    @Autowired
    public BotBl(UserRepository userRepository, PhoneRepository phoneRepository, ChatRepository chatRepository) {
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.chatRepository = chatRepository;
    }


    public List<String> processUpdate(Update update) {
        LOGGER.info("Recibiendo update {} ", update);
        List<String> chatResponse = new ArrayList<>();
        User user = initUser(update.getMessage().getFrom());
        coninueChatWithUser(update, user, chatResponse);
        return chatResponse;
    }

    private void coninueChatWithUser( Update update, User user, List<String> chatResponce) {

        Chat lastMessage = chatRepository.findLastChatByUserId(user.getIdUser());
        String response = null;

        if (lastMessage == null) {
            response = "1";
        } else {
            int lastMessageInt = 0;
            try {
                lastMessageInt = Integer.parseInt(lastMessage.getOutMessage());
                response = "" + (lastMessageInt + 1);
            } catch (NumberFormatException nfe) {
                response = "1";
            }
        }
        LOGGER.info("PROCESSING IN MESSAGE: {} from user {}" ,update.getMessage().getText(), user.getIdUser());

        Chat chat = new Chat();
        chat.setIdUserChat(user);
        chat.setInMessage(update.getMessage().getText());
        chat.setOutMessage(response);
        chat.setDateMessage(new Date());
        chat.setTxDate(new Date());
        chat.setTxUser(user.getIdUser().toString());
        chat.setTxHost(update.getMessage().getChatId().toString());

        // Guardamos en base dedatos
        chatRepository.save(chat);
        // Agregamos la respuesta al chatResponse.
        chatResponce.add(response);
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
