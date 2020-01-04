package com.example.AgendaSoftware;

import com.example.AgendaSoftware.bl.ContactBl;
import com.example.AgendaSoftware.bl.KeyboardBl;
import com.example.AgendaSoftware.bl.MessageBl;
import com.example.AgendaSoftware.bl.PhoneBl;
import com.example.AgendaSoftware.dao.ChatRepository;
import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.dao.UserRepository;
import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.domain.Phone;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MessageBlTest {
    @Test
    void messageSaveContactTest(){
        PhoneBl phoneBl = Mockito.mock(PhoneBl.class);
        ContactBl contactBl = Mockito.mock(ContactBl.class);
        KeyboardBl keyboardBl = Mockito.mock(KeyboardBl.class);
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);

        MessageBl messageBl = new MessageBl(phoneBl,contactBl,keyboardBl,contactRepository,userRepository,phoneRepository,chatRepository);

        String value = messageBl.messageSaveContact(0);
        assertEquals(value, "Ingrese Primer Nombre");
    }

    @Test
    void messageUpdateDateBornContactTest(){
        PhoneBl phoneBl = Mockito.mock(PhoneBl.class);
        ContactBl contactBl = Mockito.mock(ContactBl.class);
        KeyboardBl keyboardBl = Mockito.mock(KeyboardBl.class);
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);

        MessageBl messageBl = new MessageBl(phoneBl,contactBl,keyboardBl,contactRepository,userRepository,phoneRepository,chatRepository);

        String value = messageBl.messageUpdateDateBornContact(0);
        assertEquals(value, "Ingrese dia de nacimiento");
    }

    @Test
    void listAllContactsTest(){
        PhoneBl phoneBl = Mockito.mock(PhoneBl.class);
        ContactBl contactBl = Mockito.mock(ContactBl.class);
        KeyboardBl keyboardBl = Mockito.mock(KeyboardBl.class);
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);

        List<Contact> contactList = new ArrayList<>();
        Contact contact = Mockito.mock(Contact.class);
        Mockito.doReturn(1).when(contact).getIdContact();
        Mockito.doReturn("Juan").when(contact).getFirstName();
        Mockito.doReturn("Antonio").when(contact).getSecondName();
        Mockito.doReturn("Perez").when(contact).getFirstLastName();
        Mockito.doReturn("Loayza").when(contact).getSecondLastName();
        Mockito.doReturn("Juan@gmail.com").when(contact).getMail();
        Mockito.doReturn(new Date()).when(contact).getDateBorn();
        Mockito.doReturn("No Image").when(contact).getImage();
        Mockito.doReturn(1).when(contact).getStatus();
        contactList.add(contact);
        Mockito.doReturn(contactList).when(contactRepository).findAll();
        MessageBl messageBl = new MessageBl(phoneBl,contactBl,keyboardBl,contactRepository,userRepository,phoneRepository,chatRepository);
        List<Contact> contactListResponce = messageBl.listAllContacts();
        assertEquals(contactListResponce.size(), 1);
    }

//    @Test
//    void listUserContactsTest(){
//        PhoneBl phoneBl = Mockito.mock(PhoneBl.class);
//        ContactBl contactBl = Mockito.mock(ContactBl.class);
//        KeyboardBl keyboardBl = Mockito.mock(KeyboardBl.class);
//        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
//        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);
//
//        List<Contact> contactList = new ArrayList<>();
//        Contact contactA = Mockito.mock(Contact.class);
//        Mockito.doReturn(1).when(contactA).getIdContact();
//        Mockito.doReturn("Juan").when(contactA).getFirstName();
//        Mockito.doReturn("Antonio").when(contactA).getSecondName();
//        Mockito.doReturn("Perez").when(contactA).getFirstLastName();
//        Mockito.doReturn("Loayza").when(contactA).getSecondLastName();
//        Mockito.doReturn("Juan@gmail.com").when(contactA).getMail();
//        Mockito.doReturn(new Date()).when(contactA).getDateBorn();
//        Mockito.doReturn("No Image").when(contactA).getImage();
//        Mockito.doReturn(1).when(contactA).getStatus();
//
//        Contact contactB = Mockito.mock(Contact.class);
//        Mockito.doReturn(1).when(contactB).getIdContact();
//        Mockito.doReturn("Juan").when(contactB).getFirstName();
//        Mockito.doReturn("Antonio").when(contactB).getSecondName();
//        Mockito.doReturn("Perez").when(contactB).getFirstLastName();
//        Mockito.doReturn("Loayza").when(contactB).getSecondLastName();
//        Mockito.doReturn("Juan@gmail.com").when(contactB).getMail();
//        Mockito.doReturn(new Date()).when(contactB).getDateBorn();
//        Mockito.doReturn("No Image").when(contactB).getImage();
//        Mockito.doReturn(1).when(contactB).getStatus();
//
//        contactList.add(contactA);
//        contactList.add(contactB);
//
//        User user = Mockito.mock(User.class);
//        Mockito.doReturn(1).when(user).getIdUser();
//        Mockito.doReturn("Arturo").when(user).getName();
//        Mockito.doReturn("Domingues").when(user).getLastName();
//        Mockito.doReturn("localhost").when(user).getTxHost();
//        Mockito.doReturn(new Date()).when(user).getTxDate();
//
//        Mockito.doReturn(contactList).when(contactRepository).findAll();
//
//        MessageBl messageBl = new MessageBl(phoneBl,contactBl,keyboardBl,contactRepository,userRepository,phoneRepository,chatRepository);
//
//        List<Contact> contactListMethod = new ArrayList<>();
//        List<Contact> contactListResponce = messageBl.listAllContacts();
//        String listUserContactResponce = messageBl.listUserContacts(user, contactListMethod);
//
//        assertNotNull(listUserContactResponce);
//    }
    @Test
    void listAllPhonesTest(){
        PhoneBl phoneBl = Mockito.mock(PhoneBl.class);
        ContactBl contactBl = Mockito.mock(ContactBl.class);
        KeyboardBl keyboardBl = Mockito.mock(KeyboardBl.class);
        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);

        List<Phone> phoneList = new ArrayList<>();

        Phone phone = Mockito.mock(Phone.class);
        Mockito.doReturn(1).when(phone).getIdPhone();
        Mockito.doReturn("70595607").when(phone).getNumberPhone();
        Mockito.doReturn(1).when(phone).getStatus();
        phoneList.add(phone);
        Mockito.doReturn(phoneList).when(phoneRepository).findAll();
        MessageBl messageBl = new MessageBl(phoneBl,contactBl,keyboardBl,contactRepository,userRepository,phoneRepository,chatRepository);
        List<Phone> phoneListResponce = messageBl.listAllPhones();
        assertEquals(phoneListResponce.size(), 1);
    }

//    @Test
//    void startConversationTest(){
//        PhoneBl phoneBl = Mockito.mock(PhoneBl.class);
//        ContactBl contactBl = Mockito.mock(ContactBl.class);
//        KeyboardBl keyboardBl = Mockito.mock(KeyboardBl.class);
//        ContactRepository contactRepository = Mockito.mock(ContactRepository.class);
//        UserRepository userRepository = Mockito.mock(UserRepository.class);
//        PhoneRepository phoneRepository = Mockito.mock(PhoneRepository.class);
//        ChatRepository chatRepository = Mockito.mock(ChatRepository.class);
//
//        org.telegram.telegrambots.meta.api.objects.User user = Mockito.mock(org.telegram.telegrambots.meta.api.objects.User.class);
//        Mockito.doReturn(1).when(user).getId();
//        Mockito.doReturn("Juan").when(user).getFirstName();
//        Mockito.doReturn("Perez").when(user).getLastName();
//        Message message = Mockito.mock(Message.class);
//        Mockito.doReturn(user).when(message).getFrom();
//        Mockito.doReturn("Hola").when(message).getText();
//        Mockito.doReturn(1L).when(message).getChatId();
//
//        Update update = Mockito.mock(Update.class);
//        Mockito.doReturn(message).when(update).getMessage();
//        Mockito.doReturn(1).when(update).getMessage().getChatId();
//
//
//        User userBl = Mockito.mock(User.class);
//        Mockito.doReturn(1).when(userBl).getIdUser();
//        Mockito.doReturn("Arturo").when(userBl).getName();
//        Mockito.doReturn("Domingues").when(userBl).getLastName();
//        Mockito.doReturn("localhost").when(userBl).getTxHost();
//        Mockito.doReturn(new Date()).when(userBl).getTxDate();
//
//        long chatId = update.getMessage().getChatId();
//        SendMessage sendMessage = Mockito.mock(SendMessage.class);
//        Mockito.doReturn(chatId).when(sendMessage).getChatId();
//        SendPhoto sendPhoto = Mockito.mock(SendPhoto.class);
//        Mockito.doReturn(chatId).when(sendPhoto).getChatId();
//
//        MessageBl messageBl = new MessageBl(phoneBl,contactBl,keyboardBl,contactRepository,userRepository,phoneRepository,chatRepository);
//        messageBl.startConversation(update,userBl,sendMessage,sendPhoto);
//
//
//        assertEquals(sendMessage.getText(), "Que gusto verte denuevo!\nSeleciona una opcion por favor");
//    }
}
