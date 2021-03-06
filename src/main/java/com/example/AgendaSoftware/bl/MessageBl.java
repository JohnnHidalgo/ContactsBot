package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.ChatRepository;
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
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.*;

@Service
public class MessageBl {
    private static final Logger LOGGER= LoggerFactory.getLogger(MessageBl.class);
    private static List<String> registUserList= new ArrayList<>();
    private static List<String> updateDateBornList= new ArrayList<>();
    public boolean startflag = true;
    public boolean updateflag = false;
    public boolean updateNameFlag = false;
    public boolean updateSecondNameFlag = false;
    public boolean updateLastNameFlag = false;
    public boolean updateSecondLastNameFlag = false;
    public boolean updateEmailFlag = false;
    public boolean updateDateBornFlag = false;
    public boolean updatePhoneFlag = false;
    public boolean updateImageFlag = false;
    public boolean addNumberFlag = false;
    public boolean findByPhone = false;
    public boolean findByName = false;

    int indexUpdate = 0;
    private ContactRepository contactRepository;
    private UserRepository userRepository;
    private PhoneRepository phoneRepository;
    private ChatRepository chatRepository;
    private PhoneBl phoneBl;
    private ContactBl contactBl;
    private KeyboardBl keyboardBl;

    @Autowired
    public MessageBl(PhoneBl phoneBl, ContactBl contactBl, KeyboardBl keyboardBl, ContactRepository contactRepository, UserRepository userRepository, PhoneRepository phoneRepository, ChatRepository chatRepository){
        this.phoneBl = phoneBl;
        this.contactBl = contactBl;
        this.keyboardBl = keyboardBl;
        this.contactRepository = contactRepository;
        this.userRepository = userRepository;
        this.phoneRepository = phoneRepository;
        this.chatRepository = chatRepository;
    }
    /***Texts***/
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
    public static String messageUpdateDateBornContact(int qu) {
        String responces=new String();
        switch (qu){
            case 0:
                LOGGER.info("[Message] Pedir dia de nacimiento");
                responces="Ingrese dia de nacimiento";
                break;
            case 1:
                LOGGER.info("[Message] Pedir mes de nacimiento");
                responces="Ingrese mes de nacimiento";
                break;
            case 2:
                LOGGER.info("[Message] Pedir año de nacimiento");
                responces="Ingrese año de nacimiento";
                break;
        }
        return responces;
    }

    /***List All Contact***/
    public List<Contact> listAllContacts(){
        List<Contact> contactList = contactRepository.findAll();
        return contactList;
    }

    /***List User Contact***/
    public String listUserContacts(User user,List<Contact> userContactList){
        int userIdMessage= user.getIdUser();
        String responceContacts = "";
        List<Contact> contactList = new ArrayList<>();
        contactList = listAllContacts();

        for (int i=0; i<contactList.size();i++){
            if(userIdMessage == contactList.get(i).getIdUserContact().getIdUser() && contactList.get(i).getStatus()==1){
                userContactList.add(contactList.get(i));
                responceContacts = responceContacts +contactList.get(i).getFirstName()+" "+contactList.get(i).getFirstLastName()+" "+contactList.get(i).getMail()+"\n";
            }
        }
        return responceContacts;
    }

    /***List All Phones***/
    public List<Phone> listAllPhones(){
        List<Phone> contactList = phoneRepository.findAll();
        return contactList;
    }

    /***List Contact Phone***/
    public String listPhoneContacts(Contact contact,List<Phone> contactPhoneList){

        int contactId = contact.getIdContact();

        String responcePhones = "";
        List<Phone> phoneList = new ArrayList<>();

        phoneList = listAllPhones();

        for (int j=0; j<phoneList.size();j++){
            if(contactId == phoneList.get(j).getIdContactPhone().getIdContact() && phoneList.get(j).getStatus()==1 ){
                contactPhoneList.add(phoneList.get(j));
                responcePhones = responcePhones +phoneList.get(j).getIdPhone()+" - "+phoneList.get(j).getNumberPhone()+"\n";
            }
        }
        return responcePhones.equals("")?"No phone":responcePhones;
    }

    /***Choose contact for update***/
    public Contact chooseContactForUpdate(User user){
        Chat lastMessage = chatRepository.findLastChatByUserId(user.getIdUser());
        String contactDataMessageUpdate = lastMessage.getInMessage();
        String messageData[] = contactDataMessageUpdate.split(" ");
        List<Contact> userContactList = new ArrayList<>();
        String responceContacts = listUserContacts(user, userContactList);
        Contact contactUpdate = new Contact();
        for (int i=0 ; i<userContactList.size();i++ ){
            if(userContactList.get(i).getIdContact()== Integer.parseInt(messageData[0])){
                contactUpdate = userContactList.get(i);
            }
        }
        return contactUpdate;
    }


    public Contact findContactForResponce(List<Contact> contactList, int idContact){
        Contact contactoParaResponder = new Contact();
        for (int i =0 ;i<contactList.size();i++){
            if(contactList.get(i).getIdContact() == idContact){
                contactoParaResponder = contactList.get(i);
            }
        }
        return contactoParaResponder;
    }

    /***Start Conversation***/
    public void startConversation(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
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

    /***Principal Menu***/
    public void principalMenu(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
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

    /***Menu option Registrar Contacto***/
    public void startRegisterContact(Update update, User user, SendMessage sendMessage, SendPhoto sendPhoto, Boolean registerFlag, int registerCounter){
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
            keyboardMarkup.setKeyboard(KeyboardBl.keywordSaveContact(registerCounter));
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
            contact.setFirstName(registUserList.get(1).equals("Siguiente")?"empty":registUserList.get(1));
            contact.setSecondName(registUserList.get(2).equals("Siguiente")?"empty":registUserList.get(2));
            contact.setFirstLastName(registUserList.get(3).equals("Siguiente")?"empty":registUserList.get(3));
            contact.setSecondLastName(registUserList.get(4).equals("Siguiente")?"empty":registUserList.get(4));
            contact.setMail(registUserList.get(5).equals("Siguiente")?"empty":registUserList.get(5));
            contact.setDateBorn(dateBornContact);
            contact.setImage(registUserList.get(10).equals("Siguiente")?"No Image":registUserList.get(10));
            contact.setStatus(Status.ACTIVE.getStatus());

            contactRepository.save(contact);
            Phone phone = new Phone();
            phone.setNumberPhone(registUserList.get(6).equals("Siguiente")?"empty":registUserList.get(6));
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

    /***Menu option Listar***/
    public void listContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto) {
        user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        List<KeyboardButton> keyboardButtons = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        KeyboardRow rowOne = new KeyboardRow();

        List<Contact> userContactList = new ArrayList<>();
        String responceContacts = listUserContacts(user, userContactList);
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Estamos en Enlistar\n\n"+responceContacts);

        row.add("Actualizar Contacto");
        row.add("Eliminar Contacto");
        rowOne.add("Agregar Nuevo Numero");
        rowOne.add("Menú Principal");

        keyboard.add(row);
        keyboard.add(rowOne);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }

    /***Menu option Buscar***/
    public void findContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto, List<Phone> phoneList, List<Contact> contactList){

        long chatId = update.getMessage().getChatId();
        String buildResponce = " ";
        List<Phone> phoneListresponce = new ArrayList<>();
        if (update.getMessage().getText().equals("Por nombre") || findByName){
            if(!findByName){
                findByName = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el Nombre");
            }else {
                findByName = false;
                List<Contact> userContactList = new ArrayList<>();
                String responceContacts = listUserContacts(user, userContactList);
                LOGGER.info("tamaño de contacto"+userContactList.size());

                for(int i=0;i<userContactList.size();i++){
                    if(userContactList.get(i).getFirstName().contains(update.getMessage().getText())){
                        contactList.add(userContactList.get(i));
                        buildResponce =buildResponce + " "+userContactList.get(i).getIdContact()+ " "+userContactList.get(i).getFirstName()+ " "+userContactList.get(i).getSecondName()+ " "+userContactList.get(i).getFirstLastName()+ " "+userContactList.get(i).getSecondLastName()+ "\n";
                    }
                    else if(userContactList.get(i).getSecondName().contains(update.getMessage().getText())){
                        contactList.add(userContactList.get(i));
                        buildResponce =buildResponce + " "+userContactList.get(i).getIdContact()+ " "+userContactList.get(i).getFirstName()+ " "+userContactList.get(i).getSecondName()+ " "+userContactList.get(i).getFirstLastName()+ " "+userContactList.get(i).getSecondLastName()+ "\n";
                    }
                    else if(userContactList.get(i).getFirstLastName().contains(update.getMessage().getText())){
                        contactList.add(userContactList.get(i));
                        buildResponce =buildResponce + " "+userContactList.get(i).getIdContact()+ " "+userContactList.get(i).getFirstName()+ " "+userContactList.get(i).getSecondName()+ " "+userContactList.get(i).getFirstLastName()+ " "+userContactList.get(i).getSecondLastName()+ "\n";
                    }
                    else if(userContactList.get(i).getSecondLastName().contains(update.getMessage().getText())){
                        contactList.add(userContactList.get(i));
                        buildResponce =buildResponce + " "+userContactList.get(i).getIdContact()+ " "+userContactList.get(i).getFirstName()+ " "+userContactList.get(i).getSecondName()+ " "+userContactList.get(i).getFirstLastName()+ " "+userContactList.get(i).getSecondLastName()+ "\n";
                    }
                }
                LOGGER.info("Tamaño final"+contactList.size());
                sendMessage.setChatId(chatId)
                        .setText(buildResponce);
            }
        }else if(update.getMessage().getText().equals("Por telefono") || findByPhone){
            if(!findByPhone){
                findByPhone = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el Numero");
            }else {
                List<Contact> userContactList = new ArrayList<>();
                String responceContacts = listUserContacts(user, userContactList);

                Contact contactForResponce = new Contact();
                findByPhone = false;
                Contact contactUpdate = contactRepository.findById(user);
                List <Contact> contactResponce = new ArrayList<>();
                List<Phone> phoneListRecived = new ArrayList<>();
                phoneListRecived = listAllPhones();
                String responcePhone = listPhoneContacts(contactUpdate,phoneList);
                LOGGER.info("tamaño de phone"+phoneListRecived.size());
                for(int i=0;i<phoneListRecived.size();i++){
                    if(phoneListRecived.get(i).getNumberPhone().contains(update.getMessage().getText())) {
                        LOGGER.info("tamaño de User Contact List " + userContactList.size());
                        LOGGER.info("*********id  " + phoneListRecived.get(i).getIdContactPhone().getIdContact());
                        contactForResponce = findContactForResponce(userContactList,phoneListRecived.get(i).getIdContactPhone().getIdContact());
                        LOGGER.info("**Contacto creado  " + contactForResponce.getFirstName());
                        if(contactForResponce.getFirstName() !=null){
                            phoneListresponce.add(phoneListRecived.get(i));
                            buildResponce =buildResponce + " "+ contactForResponce.getFirstName()+" "+ contactForResponce.getFirstLastName()+" "+phoneListRecived.get(i).getNumberPhone()+ "\n";
                        }
                    }
                }
                sendMessage.setChatId(chatId)
                        .setText(buildResponce);
            }
        }else{
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            List<KeyboardRow> keyboard = new ArrayList<>();
            KeyboardRow row = new KeyboardRow();
            sendMessage.setChatId(chatId)
                    .setText("Estamos en Buscar");
            row.add("Por nombre");
            row.add("Por telefono");
            row.add("Cancelar");
            keyboard.add(row);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);
        }
    }

    /***Menu option Informacion***/
    public void infoApp(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Estamos en Información");
    }

    /***Listar option *Delete**/
    public void startDeleteContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        List<Contact> userContactList = new ArrayList<>();
        String responceContacts = listUserContacts(user, userContactList);
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Para eliminar un contacto, Debe seleccionar el boton del contacto que desea eliminar");
        row.add("Empezar");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
    public void deleteContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        long chatId = update.getMessage().getChatId();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        LOGGER.info("Verificacion"+update.getMessage().getText());

        if( update.getMessage().getText().equals("Empezar") ||startflag == true ){
            startflag= false;
            user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
            sendMessage.setChatId(chatId)
                    .setText("Seleccioina el contacto que desea eliminar");
            List<Contact> userContactList = new ArrayList<>();
            String responceContacts = listUserContacts(user, userContactList);
            keyboard = KeyboardBl.keywordDeleteContact(userContactList);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);
        }
        else{
            KeyboardRow rowMenu = new KeyboardRow();
            String contactDataMessage = update.getMessage().getText();
            String contactDataDelete[] = contactDataMessage.split(" ");

            List<Contact> userContactList = new ArrayList<>();
            String responceContacts = listUserContacts(user, userContactList);


            Contact contact = new Contact();
            for (int i=0 ; i<userContactList.size();i++ ){
                if(userContactList.get(i).getIdContact()== Integer.parseInt(contactDataDelete[0])){
                    contact = userContactList.get(i);

                }
            }
            contact.setStatus(Status.INACTIVE.getStatus());
            contactRepository.save(contact);

            sendMessage.setChatId(chatId)
                    .setText("Contacto Eliminado Correctamente");
            rowMenu.add("Menú Principal");
            keyboard.add(rowMenu);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);

            startflag = false;
        }
    }

    /***Listar option *Update**/
    public void startUpdateContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto){
        user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        List<Contact> userContactList = new ArrayList<>();
        String responceContacts = listUserContacts(user, userContactList);
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Para actualizar un contacto, Debe seleccionar el boton del contacto que desea actualizar");
        row.add("Empezar");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
    public void updateContact(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto, Contact contact, List<Phone> phoneContactList, Boolean updateValues){
        long chatId = update.getMessage().getChatId();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        if(!updateValues && !update.getMessage().hasPhoto() && ( startflag || update.getMessage().getText().equals("Empezar"))){
            startflag= false;
            updateNameFlag = false;
            updateSecondNameFlag = false;
            updateLastNameFlag = false;
            updateSecondLastNameFlag = false;
            updateEmailFlag = false;
            updateDateBornFlag = false;
            updatePhoneFlag = false;
            updateImageFlag = false;
            user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
            sendMessage.setChatId(chatId)
                    .setText("Seleccioina el contacto que desea actualizar");
            List<Contact> userContactList = new ArrayList<>();
            String responceContacts;
            responceContacts = listUserContacts(user, userContactList);
            keyboard = KeyboardBl.keywordUpdateContact(userContactList);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);
        }
        else if (updateflag == false){
            String contactDataMessage = update.getMessage().getText();
            String contactDataDelete[] = contactDataMessage.split(" ");
            List<Contact> userContactList = new ArrayList<>();
            String responceContacts = listUserContacts(user, userContactList);
            List<Phone> phoneList = new ArrayList<>();
            for (int i=0 ; i<userContactList.size();i++ ){
                if(userContactList.get(i).getIdContact()== Integer.parseInt(contactDataDelete[0])){
                    contact = userContactList.get(i);
                }
            }

            phoneList = listAllPhones();

            String responcePhone = listPhoneContacts(contact,phoneContactList);

            LOGGER.info("Aqui mostraremos el contacto");
            LOGGER.info("Phone responce "+responcePhone);

            sendMessage.setChatId(chatId)
                    .setText("Nombre : "+contact.getFirstName()+"\n"+
                            "Segundo Nombre : "+contact.getSecondName()+"\n"+
                            "Primer Apellido : "+contact.getFirstLastName()+"\n"+
                            "Segundo Apellido : "+contact.getSecondLastName()+"\n"+
                            "Email : "+contact.getMail()+"\n"+
                            "Fecha de Nacimiento : "+contact.getDateBorn()+"\n"+
                            "Telefono[s] : |"+responcePhone+"\n"+
                            "Imagen : "+contact.getImage()+"\n"
                    );

            sendPhoto.setChatId(chatId)
                    .setPhoto(contact.getImage().equals("No Image")?"https://www.gvsu.edu/cms4/asset/25867353-94CC-EA07-36E3D4DCA04D10A3/laker_effect_buttons_vacant(4).jpg":contact.getImage());

            keyboard = KeyboardBl.keywordUpdateContactOptions(contact.getIdContact());
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);

            startflag = false;
            updateflag = true;
            updateNameFlag = false;
            updateSecondNameFlag = false;
            updateLastNameFlag = false;
            updateSecondLastNameFlag = false;
            updateEmailFlag = false;
            updateDateBornFlag = false;
            updatePhoneFlag = false;
            updateImageFlag = false;
        }
        else if(updateflag &&  !updateNameFlag && !updateSecondNameFlag && !updateLastNameFlag && !updateSecondLastNameFlag && !updateEmailFlag && !updateDateBornFlag && !updatePhoneFlag && !updateImageFlag ){
            String contactDataMessage = update.getMessage().getText();
            String messageData[] = contactDataMessage.split(" ");//[0]id del contacto
            if( messageData[1].equals("Nombre") ){
                updateNameFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo nombre");
            }
            else if(messageData[1].equals("Segundo") && messageData[2].equals("Nombre")){
                updateSecondNameFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo Segundo Nombre");
            }
            else if(messageData[1].equals("Primer") && messageData[2].equals("Apellido")){
                updateLastNameFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo Primer Apellido");
            }
            else if(messageData[1].equals("Segundo") && messageData[2].equals("Apellido")){
                updateSecondLastNameFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo Segundo Apellido");
            }
            else if(messageData[1].equals("Email")){
                updateEmailFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo Email");
            }
            else if(messageData[1].equals("Fecha") && messageData[2].equals("de")){
                updateDateBornFlag = true;
                updateDateBornList.add(update.getMessage().getText());
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo día de Nacimiento");
                keyboardMarkup.setKeyboard(KeyboardBl.keywordUpdateDateBornContact(indexUpdate));
                sendMessage.setReplyMarkup(keyboardMarkup);
                indexUpdate++;
            }
            else if(messageData[1].equals("Telefono")){
                updatePhoneFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese el nuevo Telefono");
            }
            else if(messageData[1].equals("Imagen")){
                updateImageFlag = true;
                sendMessage.setChatId(chatId)
                        .setText("Ingrese la nuevo Imagen");
            }
        }
        else if (updateNameFlag== true) {
            Contact contactUpdate = chooseContactForUpdate(user);
            contactUpdate.setFirstName(update.getMessage().getText());
            contactRepository.save(contactUpdate);
            sendMessage.setChatId(chatId)
                    .setText("Nombre actualizado");
            updateNameFlag = false;
            updateflag = false;
        }
        else if (updateSecondNameFlag== true) {
            Contact contactUpdate = chooseContactForUpdate(user);
            contactUpdate.setSecondName(update.getMessage().getText());
            contactRepository.save(contactUpdate);
            sendMessage.setChatId(chatId)
                    .setText("Segundo Nombre actualizado");
            updateSecondNameFlag = false;
            updateflag = false;
        }
        else if (updateLastNameFlag== true) {
            Contact contactUpdate = chooseContactForUpdate(user);
            contactUpdate.setFirstLastName(update.getMessage().getText());
            contactRepository.save(contactUpdate);
            sendMessage.setChatId(chatId)
                    .setText("Primer Apellido actualizado");
            updateLastNameFlag = false;
            updateflag = false;
        }
        else if (updateSecondLastNameFlag== true) {
            Contact contactUpdate = chooseContactForUpdate(user);
            contactUpdate.setSecondLastName(update.getMessage().getText());
            contactRepository.save(contactUpdate);
            sendMessage.setChatId(chatId)
                    .setText("Segundo Apellido actualizado");
            updateSecondLastNameFlag = false;
            updateflag = false;
        }
        else if (updateEmailFlag== true) {

            Contact contactUpdate = chooseContactForUpdate(user);
            contactUpdate.setMail(update.getMessage().getText());
            contactRepository.save(contactUpdate);
            sendMessage.setChatId(chatId)
                    .setText("Mail actualizado");
            updateEmailFlag = false;
            updateflag = false;
        }
        else if (updateDateBornFlag== true) {

            String responce ;

            if(indexUpdate<3){
                updateDateBornList.add(update.getMessage().getText());
                sendMessage.setChatId(chatId);
                responce = messageUpdateDateBornContact(indexUpdate);
                sendMessage.setChatId(chatId)
                        .setText(responce);
                keyboardMarkup.setKeyboard(KeyboardBl.keywordUpdateDateBornContact(indexUpdate));
                sendMessage.setReplyMarkup(keyboardMarkup);
                indexUpdate++;
            }
            else{
                updateDateBornList.add(update.getMessage().getText());
                LOGGER.info("tamaña de nueva lista"+updateDateBornList.size());
                LOGGER.info("tamaña de nueva lista"+updateDateBornList.get(0));
                LOGGER.info("tamaña de nueva lista"+updateDateBornList.get(1));
                LOGGER.info("tamaña de nueva lista"+updateDateBornList.get(2));
                Chat lastMessage = chatRepository.findLastChatByUserId(user.getIdUser());
                List<Contact> userContactList = new ArrayList<>();
                String responceContacts = listUserContacts(user, userContactList);
                String messageData[] = updateDateBornList.get(0).split(" ");
                Contact contactUpdate = new Contact();
                for (int i=0 ; i<userContactList.size();i++ ){
                    if(userContactList.get(i).getIdContact()== Integer.parseInt(messageData[0])){
                        contactUpdate = userContactList.get(i);
                    }
                }
                Calendar calendarDateBorn = Calendar.getInstance();
                calendarDateBorn.set(Calendar.YEAR, Integer.parseInt(updateDateBornList.get(3)));
                calendarDateBorn.set(Calendar.MONTH, Integer.parseInt(updateDateBornList.get(2))-1);
                calendarDateBorn.set(Calendar.DAY_OF_MONTH, Integer.parseInt(updateDateBornList.get(1)));
                Date dateBornContact = calendarDateBorn.getTime();
                contactUpdate.setDateBorn(dateBornContact);
                contactRepository.save(contactUpdate);
                sendMessage.setChatId(chatId)
                        .setText("Nacimiento actualizado");

                KeyboardRow row = new KeyboardRow();
                keyboardMarkup.setResizeKeyboard(true);
                row.add("Menú Principal");
                keyboard.add(row);
                keyboardMarkup.setKeyboard(keyboard);
                sendMessage.setReplyMarkup(keyboardMarkup);

                updateEmailFlag = false;
                updateflag = false;
            }

        }
        else if (updatePhoneFlag== true) {

            Contact contactUpdate = chooseContactForUpdate(user);
            List<Phone> phoneList = new ArrayList<>();
            phoneList = listAllPhones();
            String responcePhone = listPhoneContacts(contactUpdate,phoneContactList);

            if(phoneContactList.size()==0){
                Phone phoneUpdate = new Phone();
                phoneUpdate.setIdContactPhone(contactUpdate);
                phoneUpdate.setStatus(Status.ACTIVE.getStatus());
                phoneUpdate.setNumberPhone(update.getMessage().getText());
                phoneRepository.save(phoneUpdate);
            }else{
                Phone phoneUpdate = phoneContactList.get(0);
                phoneUpdate.setNumberPhone(update.getMessage().getText());
                phoneRepository.save(phoneUpdate);
            }
            sendMessage.setChatId(chatId)
                    .setText("Telefono actualizado");
            updatePhoneFlag = false;
            updateflag = false;
        }
        else if (updateImageFlag== true) {

            Contact contactUpdate = chooseContactForUpdate(user);
            String photoData = null;
                List<PhotoSize> photos = update.getMessage().getPhoto();
                photoData = photos.stream()
                        .sorted(Comparator.comparing(PhotoSize::getFileSize).reversed())
                        .findFirst()
                        .orElse(null).getFileId();
                contactUpdate.setImage(photoData);
                contactRepository.save(contactUpdate);

            LOGGER.info("Imagen actualizada");
            sendMessage.setChatId(chatId)
                    .setText("Imagen actualizado");
            updateImageFlag = false;
            updateflag = false;
        }
    }

    /***Agregar Nuevo Numero***/
    public void startAddNumber(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto) {
        user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row = new KeyboardRow();
        List<Contact> userContactList = new ArrayList<>();
        String responceContacts = listUserContacts(user, userContactList);
        long chatId = update.getMessage().getChatId();
        sendMessage.setChatId(chatId)
                .setText("Para agregar un nuevo numero, Debe seleccionar el boton del contacto al que desea agregar");
        row.add("Empezar");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        sendMessage.setReplyMarkup(keyboardMarkup);
    }
    public void addNumber(Update update, User user,SendMessage sendMessage,SendPhoto sendPhoto, List<Phone> phoneContactList) {
        user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
        long chatId = update.getMessage().getChatId();
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<>();

        if(update.getMessage().getText().equals("Empezar")){
            startflag= false;
            addNumberFlag = false;
            user = userRepository.findByIdUserbot(update.getMessage().getChatId().toString());
            sendMessage.setChatId(chatId)
                    .setText("Seleccioina el contacto que desea actualizar");
            List<Contact> userContactList = new ArrayList<>();
            String responceContacts;
            responceContacts = listUserContacts(user, userContactList);
            keyboard = KeyboardBl.keywordUpdateContact(userContactList);
            keyboardMarkup.setKeyboard(keyboard);
            sendMessage.setReplyMarkup(keyboardMarkup);
        }else if(!addNumberFlag) {
            sendMessage.setChatId(chatId)
                    .setText("Ingrese el nuevo numero");
            addNumberFlag = true;
        }else if(addNumberFlag) {
            Contact contactUpdate = chooseContactForUpdate(user);
            List<Phone> phoneList = new ArrayList<>();
            phoneList = listAllPhones();
            String responcePhone = listPhoneContacts(contactUpdate,phoneContactList);

            if(phoneContactList.size()==0){
                Phone phoneUpdate = new Phone();
                phoneUpdate.setIdContactPhone(contactUpdate);
                phoneUpdate.setStatus(Status.ACTIVE.getStatus());
                phoneUpdate.setNumberPhone(update.getMessage().getText());
                phoneRepository.save(phoneUpdate);
            }else{
                Phone phoneUpdate = new Phone();
                phoneUpdate.setIdContactPhone(contactUpdate);
                phoneUpdate.setStatus(Status.ACTIVE.getStatus());
                phoneUpdate.setNumberPhone(update.getMessage().getText());
                phoneRepository.save(phoneUpdate);
            }
            sendMessage.setChatId(chatId)
                    .setText("Telefono actualizado");
        }
        updatePhoneFlag = false;
        updateflag = false;
    }


}