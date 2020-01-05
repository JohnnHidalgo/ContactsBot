package com.example.AgendaSoftware;

import com.example.AgendaSoftware.bl.*;
import com.example.AgendaSoftware.dao.ChatRepository;
import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.dao.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.User;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BotBlTest {

    @Test
    void initUserTest() {
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        MessageBl messageBl = Mockito.mock(MessageBl.class);
        User user = Mockito.mock(User.class);
        Mockito.doReturn(1234).when(user).getId();
        Mockito.doReturn("Juan").when(user).getFirstName();
        Mockito.doReturn("Perez").when(user).getLastName();
        BotBl botBl = new BotBl(userRepository,phoneRepository,chatRepository,contactRepository, messageBl);
        com.example.AgendaSoftware.domain.User userResponce = botBl.initUser(user);
        assertNotNull(userResponce);
    }
}
