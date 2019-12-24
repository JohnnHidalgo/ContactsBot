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
public class MessageBl {

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageBl.class);
    private static int numero_de_pregunta=0;
    private static boolean registrosllenos=false;
    private static boolean entra_a_registro_docente=false;
    private static List<String> registrollenadosList= new ArrayList<>();

    /*******/
    private ContactRepository contactRepository;
    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private PhoneBl phoneBl;
    private ContactBl contactBl;

//    Update update;
//    User user;
//    SendMessage sendMessage;
//    SendPhoto sendPhoto;
//    String messageInput = update.getMessage().getText();
//    long chatId = update.getMessage().getChatId();
//    String messageTextReceived = update.getMessage().getText();
//        LOGGER.info("Ultimo mensaje "+update.getMessage().getText());
//    String imageFile = null;
//    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
//    List<KeyboardRow> keyboard = new ArrayList<>();
//    KeyboardRow row = new KeyboardRow();
//        sendMessage.setChatId(chatId);


    @Autowired
    public MessageBl(PhoneBl phoneBl, ContactBl contactBl, ContactRepository contactRepository, UserRepository userRepository){
        this.phoneBl = phoneBl;
        this.contactBl = contactBl;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
    }

    //sendMessage,sendPhoto,update
    public static String inicioMensaje(SendMessage sendMessage, SendPhoto sendPhoto, Update update){
        String response ="Bienvenido";
        return response;
    }

    public static int getNumero_de_pregunta() {
        return numero_de_pregunta;
    }

    public static void setNumero_de_pregunta(int numero_de_pregunta) {
        MessageBl.numero_de_pregunta = numero_de_pregunta;
    }

    public String entraRegistroDocente(SendMessage sendMessage,String messageTextReceived){
        System.out.println("Entra a el registro docente oficial");
        LOGGER.info("Entra a el registro docente oficial");
        String mensaje="";
        if(registrollenadosList.size()<8)
        {
            LOGGER.info("Entra al registros no llenos");
            if(getNumero_de_pregunta()<7){
                mensaje = messageSaveContact();
            }
            setNumero_de_pregunta(getNumero_de_pregunta()+1) ;//
            registrollenadosList.add(messageTextReceived);
        }
        if (registrollenadosList.size()==7) {
            LOGGER.info("Ingresa a registros llenos");
            mensaje = guardarListaRegistros(registrollenadosList, sendMessage);
            registrosllenos = false;
            registrollenadosList.clear();
            entra_a_registro_docente = false;
            setNumero_de_pregunta(0) ;//
        }
        System.out.println(mensaje);
        sendMessage.setText(mensaje);
        return mensaje;
    }
    public List<Contact> listaDeContactpos(SendMessage sendMessage,String messageTextReceived){
        User userTest = userRepository.findByIdUserbot(sendMessage.getChatId());


        List<Contact> contactList = contactRepository.findAll();

        return contactList;
    }


    public String messageSaveContact() {
        String responces=new String();
        switch (numero_de_pregunta){
            case 0:
                LOGGER.info("Pedir de Segundo Nombre");
                responces="Ingrese Segundo Nombre";
                break;
            case 1:
                LOGGER.info("Pedir primer apellido");
                responces="Ingrese Primer Apellido";
                break;
            case 2:
                LOGGER.info("Pedir segundo Apellido");
                responces="Ingese segundo Apellido";
                break;
            case 3:
                LOGGER.info("Pedir correo");
                responces="Ingese email";
                break;
            case 4:
                LOGGER.info("Pedir numero");
                responces="Ingese numero telefónico";
                break;
            case 5:
                LOGGER.info("Pedir nacimiento");
                responces="Ingese fecha de nacimiento";
                break;
            case 6:
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
