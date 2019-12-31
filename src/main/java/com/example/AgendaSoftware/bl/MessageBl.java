package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.dao.UserRepository;
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
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
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

    public static void principalMenu(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow rowOne = new KeyboardRow();
        sendMessage.setChatId(chatId)
                .setText("Menú");
        row.add("Registrar Contacto");
        row.add("Enlistar");
        rowOne.add("Buscar");
        rowOne.add("Información");

        keyboard.add(row);
        keyboard.add(rowOne);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public static void findContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Estamos en Buscar");
    }

    public static void listContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Estamos en Enlistar");
    }
    public static void infoApp(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Estamos en Información");
    }

    public static void startConversation(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        String imageFile = "https://mainvayne123.neocities.org/bienvenido.png";
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        KeyboardRow rowOne = new KeyboardRow();
        sendPhoto.setChatId(chatId)
                .setPhoto(imageFile);
        sendMessage.setChatId(chatId)
                .setText("Que gusto verte denuevo!\nSeleciona una opcion por favor");

        row.add("Registrar Contacto");
        row.add("Enlistar");
        rowOne.add("Buscar");
        rowOne.add("Información");

        keyboard.add(row);
        keyboard.add(rowOne);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    public static void startRegisterContact(Update update, User user, SendMessage sendMessage, SendPhoto sendPhoto, Boolean registerFlag, int registerCounter){
        long chatId = update.getMessage().getChatId();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        sendMessage.setChatId(chatId)
                .setText("Vamos a registrar un nuevo contacto. \n\nSi no tiene alguno de los datos solicitados puede presionar el boton de siguiente.");

        row.add("Empezar");
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
        keyboardMarkup.setResizeKeyboard(true);
        String photoData = null;
        if(registerCounter<=10){
            sendMessage.setChatId(chatId);
                responce = messageSaveContact(registerCounter);
                sendMessage.setChatId(chatId)
                        .setText(responce);
                keyboardMarkup.setKeyboard(keywordSaveContact(registerCounter));
                sendMessage.setReplyMarkup(keyboardMarkup);

            if(registerCounter != 0){
                if(update.getMessage().hasPhoto()){
                    List<PhotoSize> photos = update.getMessage().getPhoto();
                    photoData = photos.stream()
                            .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                            .findFirst()
                            .orElse(null).getFileId();
                    registUserList.add(photoData);
                }
                else {
                    registUserList.add(update.getMessage().getText());
                }
            }
            else {
                registUserList.add("Iniciando Registro");
            }
        }
        else{
            registUserList.add(update.getMessage().getText());


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
            LOGGER.info("11"+registUserList.get(11));


            Calendar calendarDateBorn = Calendar.getInstance();
            calendarDateBorn.set(Calendar.YEAR, Integer.parseInt(registUserList.get(9)));
            calendarDateBorn.set(Calendar.MONTH, Integer.parseInt(registUserList.get(8))-1);
            calendarDateBorn.set(Calendar.DAY_OF_MONTH, Integer.parseInt(registUserList.get(7)));
            Date dateBornContact = calendarDateBorn.getTime();

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
            LOGGER.info("RegistroCompleto A");
            registerFlag = false;

            sendMessage.setChatId(chatId);
            sendMessage.setText("Registro Completado");
            row.add("Menú Principal");
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);

            LOGGER.info("RegistroCompleto B");
        }
    }

    public List<Contact> listaDeContactos(SendMessage sendMessage,String messageTextReceived){
        User userTest = userRepository.findByIdUserbot(sendMessage.getChatId());
        List<Contact> contactList = contactRepository.findAll();
        return contactList;
    }

    public static String messageSaveContact(int qu) {
        String responces=new String();
        switch (qu){
            case 0:
                LOGGER.info("[Message] Pedir de Primer Nombre");
                responces="Ingrese Primer Nombre";
                break;
            case 1:
                LOGGER.info("[Message] Pedir de Segundo Nombre");
                responces="Ingrese Segundo Nombre";
                break;
            case 2:
                LOGGER.info("[Message] Pedir primer apellido");
                responces="Ingrese Primer Apellido";
                break;
            case 3:
                LOGGER.info("[Message] Pedir segundo Apellido");
                responces="Ingrese segundo Apellido";
                break;
            case 4:
                LOGGER.info("[Message] Pedir correo");
                responces="Ingrese email";
                break;
            case 5:
                LOGGER.info("[Message] Pedir numero");
                responces="Ingrese numero telefónico";
                break;
            case 6:
                LOGGER.info("[Message] Pedir dia de nacimiento");
                responces="Ingrese dia de nacimiento";
                break;
            case 7:
                LOGGER.info("[Message] Pedir mes de nacimiento");
                responces="Ingrese mes de nacimiento";
                break;
            case 8:
                LOGGER.info("[Message] Pedir año de nacimiento");
                responces="Ingrese año de nacimiento";
                break;
            case 9:
                LOGGER.info("[Message] Pedir imagen");
                responces="Ingrese imagen para el contacto";
                break;
        }
        return responces;
    }

    public static List<KeyboardRow> keywordSaveContact(int qu) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row= new KeyboardRow();
        KeyboardRow rowOne= new KeyboardRow();
        KeyboardRow rowTwo= new KeyboardRow();
        KeyboardRow rowThree= new KeyboardRow();
        KeyboardRow rowFour= new KeyboardRow();

        switch (qu){
            case 0:
                LOGGER.info("[Keyword]Pedir de Primer Nombre");
                row.add("Siguiente");
                row.add("Cancelar");
                keyboard.add(row);
                break;
            case 1:
                LOGGER.info("[Keyword]Pedir de Segundo Nombre");
                row.add("Siguiente");
                row.add("Cancelar");
                keyboard.add(row);
                break;
            case 2:
                LOGGER.info("[Keyword]Pedir primer apellido");
                row.add("Siguiente");
                row.add("Cancelar");
                keyboard.add(row);
                break;
            case 3:
                LOGGER.info("[Keyword]Pedir segundo Apellido");
                row.add("Siguiente");
                row.add("Cancelar");
                keyboard.add(row);
                break;
            case 4:
                LOGGER.info("[Keyword]Pedir correo");
                row.add("Siguiente");
                row.add("Cancelar");
                keyboard.add(row);
                break;
            case 5:
                LOGGER.info("[Keyword]Pedir numero");
                row.add("Siguiente");
                row.add("Cancelar");
                break;
            case 6:
                LOGGER.info("[Keyword]Pedir dia de nacimiento");
                row.add("Siguiente");
                row.add("Cancelar");
                rowOne.add("1");
                rowOne.add("2");
                rowOne.add("3");
                rowOne.add("4");
                rowOne.add("5");
                rowOne.add("6");
                rowOne.add("7");
                rowOne.add("8");
                rowTwo.add("9");
                rowTwo.add("10");
                rowTwo.add("11");
                rowTwo.add("12");
                rowTwo.add("13");
                rowTwo.add("14");
                rowTwo.add("15");
                rowTwo.add("16");
                rowThree.add("17");
                rowThree.add("18");
                rowThree.add("19");
                rowThree.add("20");
                rowThree.add("21");
                rowThree.add("22");
                rowThree.add("23");
                rowThree.add("24");
                rowFour.add("25");
                rowFour.add("26");
                rowFour.add("27");
                rowFour.add("28");
                rowFour.add("29");
                rowFour.add("30");
                rowFour.add("31");
                keyboard.add(row);
                keyboard.add(rowOne);
                keyboard.add(rowTwo);
                keyboard.add(rowThree);
                keyboard.add(rowFour);

                break;
            case 7:
                LOGGER.info("[Keyword]Pedir mes de nacimiento");
                row.add("Siguiente");
                row.add("Cancelar");
                rowOne.add("1");
                rowOne.add("2");
                rowOne.add("3");
                rowTwo.add("4");
                rowTwo.add("5");
                rowTwo.add("7");
                rowThree.add("8");
                rowThree.add("9");
                rowThree.add("10");
                rowFour.add("11");
                rowFour.add("12");
                keyboard.add(row);
                keyboard.add(rowOne);
                keyboard.add(rowTwo);
                keyboard.add(rowThree);
                keyboard.add(rowFour);
                break;
            case 8:
                LOGGER.info("[Keyword]Pedir año de nacimiento");
                row.add("Siguiente");
                row.add("Cancelar");
                keyboard.add(row);
                break;
            case 9:
                LOGGER.info("[Keyword]Pedir imagen");
                row.add("Guardar");
                row.add("Cancelar");
                keyboard.add(row);
                break;
        }
        return keyboard;
    }

}
