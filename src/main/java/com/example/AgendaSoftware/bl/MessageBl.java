package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.dao.UserRepository;
import com.example.AgendaSoftware.domain.Chat;
import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.domain.Phone;
import com.example.AgendaSoftware.domain.User;
import com.example.AgendaSoftware.dto.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.*;

@Service
public class MessageBl {

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageBl.class);
    private static int numero_de_pregunta=0;
    private static boolean registrosllenos=false;
    private static boolean entra_a_registro_docente=false;
    private static List<String> registUserList= new ArrayList<>();

    /*******/
    private ContactRepository contactRepository;
    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private PhoneBl phoneBl;
    private ContactBl contactBl;
    private Contact contact;

    @Autowired
    public MessageBl(PhoneBl phoneBl, ContactBl contactBl, ContactRepository contactRepository, UserRepository userRepository, PhoneRepository phoneRepository){
        this.phoneBl = phoneBl;
        this.contactBl = contactBl;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
    }

    //sendMessage,sendPhoto,update
    public static void startConversation(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        String imageFile = "https://mainvayne123.neocities.org/bienvenido.png";
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        sendMessage.setChatId(chatId);
        sendPhoto.setChatId(chatId)
                .setPhoto(imageFile);
        sendMessage.setChatId(chatId)
                .setText("Que gusto verte denuevo!\nSeleciona una opcion por favor");
        row.add("Registrar Contacto");
        row.add("Buscar");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public void registerConact(Update update, User user, SendMessage sendMessage, SendPhoto sendPhoto, Boolean registerFlag, int registerCounter){

        String responce= null;
        long chatId = update.getMessage().getChatId();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        if(registerCounter==10){
            registUserList.add(update.getMessage().getText());
            Date dateBornContact = new Date(Integer.parseInt(registUserList.get(7)), Integer.parseInt(registUserList.get(7)), Integer.parseInt(registUserList.get(7)));
            LOGGER.info("0"+registUserList.get(0));
            LOGGER.info("1"+registUserList.get(1));
            LOGGER.info("2"+registUserList.get(2));
            LOGGER.info("3"+registUserList.get(3));
            LOGGER.info("4"+registUserList.get(4));
            LOGGER.info("5"+registUserList.get(5));
            LOGGER.info("6"+registUserList.get(6));
            LOGGER.info("7"+registUserList.get(7));
            LOGGER.info("8"+registUserList.get(8));
            LOGGER.info("9"+registUserList.get(9));
            LOGGER.info("10"+registUserList.get(10));
            LOGGER.info("Fecha Nacimiento"+dateBornContact);
            Contact contact = new Contact();
            contact.setIdUserContact(user);
            contact.setFirstName(registUserList.get(1));
            contact.setSecondName(registUserList.get(2));
            contact.setFirstLastName(registUserList.get(3));
            contact.setSecondLastName(registUserList.get(4));
            contact.setMail(registUserList.get(5));
            contact.setDateBorn(dateBornContact);
            contact.setImage(registUserList.get(10));
            contact.setStatus(Status.ACTIVE.getStatus());
            contactRepository.save(contact);

            Phone phone = new Phone();
            phone.setNumberPhone(registUserList.get(6));
            phone.setIdContactPhone(contact);
            phone.setStatus(Status.ACTIVE.getStatus());

            phoneRepository.save(phone);
            registerFlag = false;

            sendMessage.setText("Registro Completado");
            row.add("Menú Principal");
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);
        }
        else{
        sendMessage.setChatId(chatId);
            responce = messageSaveContact(registerCounter);
            sendMessage.setChatId(chatId)
                    .setText(responce);
            row.add("Siguiente");
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);
            if(registerCounter != 0){
                registUserList.add(update.getMessage().getText());
            }
            else {
                registUserList.add("Iniciando Registro");
            }
        }
    }

    public static int getNumero_de_pregunta() {
        return numero_de_pregunta;
    }

    public static void setNumero_de_pregunta(int numero_de_pregunta) {
        MessageBl.numero_de_pregunta = numero_de_pregunta;
    }

//    public String entraRegistroDocente(SendMessage sendMessage,String messageTextReceived){
//        System.out.println("Entra a el registro docente oficial");
//        LOGGER.info("Entra a el registro docente oficial");
//        String mensaje="";
//        if(registrollenadosList.size()<8)
//        {
//            LOGGER.info("Entra al registros no llenos");
//            if(getNumero_de_pregunta()<7){
//                mensaje = messageSaveContact(4);
//            }
//            setNumero_de_pregunta(getNumero_de_pregunta()+1) ;//
//            registrollenadosList.add(messageTextReceived);
//        }
//        if (registrollenadosList.size()==7) {
//            LOGGER.info("Ingresa a registros llenos");
//            mensaje = guardarListaRegistros(registrollenadosList, sendMessage);
//            registrosllenos = false;
//            registrollenadosList.clear();
//            entra_a_registro_docente = false;
//            setNumero_de_pregunta(0) ;//
//        }
//        System.out.println(mensaje);
//        sendMessage.setText(mensaje);
//        return mensaje;
//    }
    public List<Contact> listaDeContactpos(SendMessage sendMessage,String messageTextReceived){
        User userTest = userRepository.findByIdUserbot(sendMessage.getChatId());


        List<Contact> contactList = contactRepository.findAll();

        return contactList;
    }

    public static String messageSaveContact(int qu) {
        String responces=new String();
        switch (qu){
            case 0:
                LOGGER.info("Pedir de Primer Nombre");
                responces="Vamos a registrar un nuevo contacto. \nSi no tiene alguno de los datos solicitados puede presionar el boton de siguiente. \nIngrese Primer Nombre";
                break;
            case 1:
                LOGGER.info("Pedir de Segundo Nombre");
                responces="Ingrese Segundo Nombre";
                break;
            case 2:
                LOGGER.info("Pedir primer apellido");
                responces="Ingrese Primer Apellido";
                break;
            case 3:
                LOGGER.info("Pedir segundo Apellido");
                responces="Ingese segundo Apellido";
                break;
            case 4:
                LOGGER.info("Pedir correo");
                responces="Ingese email";
                break;
            case 5:
                LOGGER.info("Pedir numero");
                responces="Ingese numero telefónico";
                break;
            case 6:
                LOGGER.info("Pedir dia de nacimiento");
                responces="Ingese dia de nacimiento";
                break;
            case 7:
                LOGGER.info("Pedir mes de nacimiento");
                responces="Ingese mes de nacimiento";
                break;
            case 8:
                LOGGER.info("Pedir año de nacimiento");
                responces="Ingese año de nacimiento";
                break;
            case 9:
                LOGGER.info("Pedir imagen");
                responces="Ingese imagen para el contacto";
                break;
        }
        return responces;
    }

    public  String guardarListaRegistros(List<String> listaderegistros, SendMessage sendMessage){

        Contact contact =new Contact();


        User userTest = userRepository.findByIdUserbot(sendMessage.getChatId());
        System.out.println(userTest);
        contact.setIdUserContact(userTest);
        contact.setFirstName(listaderegistros.get(0));
        contact.setSecondName(listaderegistros.get(1));
        contact.setFirstLastName(listaderegistros.get(2));
        contact.setSecondLastName(listaderegistros.get(3));
        contact.setMail(listaderegistros.get(4));
        contact.setDateBorn(new Date());
        contact.setImage("No Image");
        contact.setStatus(Status.ACTIVE.getStatus());
        contactRepository.save(contact);


//        Contact contactPhone = contactRepository.findById(contact.getIdUserContact());
//        Phone phone = new Phone();
//        phone.setIdContactPhone(contactPhone);
//        phone.setNumberPhone(listaderegistros.get(5));
//        phone.setStatus(Status.ACTIVE.getStatus());
//        phoneRepository.save(phone);


        return "¡Registro completado exitosamente¡";
    }


    public static boolean isEntra_a_registro_docente() {
        return entra_a_registro_docente;
    }

    public static void setEntra_a_registro_docente(boolean entra_a_registro_docente) {
        MessageBl.entra_a_registro_docente = entra_a_registro_docente;
    }

}
