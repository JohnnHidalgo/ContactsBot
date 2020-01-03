package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.domain.Contact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyboardBl {
    private static final Logger LOGGER= LoggerFactory.getLogger(MessageBl.class);

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

    public static List<KeyboardRow> keywordDeleteContact(List<Contact> userContactList) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow rowCancel = new KeyboardRow();
        KeyboardRow rowMenu = new KeyboardRow();
        KeyboardRow rowContact = new KeyboardRow();
        for(int j=0;j<userContactList.size();j++){
            rowContact.add(userContactList.get(j).getIdContact()
                    +" "+userContactList.get(j).getFirstName()+" "+userContactList.get(j).getFirstLastName()+" - "+userContactList.get(j).getMail());
        }
        rowCancel.add("Cancelar");
        rowMenu.add("Menú Principal");
        keyboard.add(rowMenu);
        keyboard.add(rowCancel);
        for (int i=0; i<rowContact.size();i++) {
            KeyboardRow row = new KeyboardRow();
            row.add(0,rowContact.get(i));
            keyboard.add(row);
        }

        return keyboard;
    }

    public static List<KeyboardRow> keywordUpdateContact(List<Contact> userContactList) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow rowCancel = new KeyboardRow();
        KeyboardRow rowMenu = new KeyboardRow();
        KeyboardRow rowContact = new KeyboardRow();
        for(int j=0;j<userContactList.size();j++){
            rowContact.add(userContactList.get(j).getIdContact()
                    +" "+userContactList.get(j).getFirstName()+" "+userContactList.get(j).getFirstLastName()+" - "+userContactList.get(j).getMail());
        }
        rowCancel.add("Cancelar");
        rowMenu.add("Menú Principal");
        keyboard.add(rowMenu);
        keyboard.add(rowCancel);
        for (int i=0; i<rowContact.size();i++) {
            KeyboardRow row = new KeyboardRow();
            row.add(0,rowContact.get(i));
            keyboard.add(row);
        }

        return keyboard;
    }

    public static List<KeyboardRow> keywordUpdateContactOptions(int contactId) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow rowCancel = new KeyboardRow();
        KeyboardRow rowMenu = new KeyboardRow();
        KeyboardRow rowName = new KeyboardRow();
        KeyboardRow rowSecondName = new KeyboardRow();
        KeyboardRow rowFirstLastName = new KeyboardRow();
        KeyboardRow rowSecondLastName = new KeyboardRow();
        KeyboardRow rowMail = new KeyboardRow();
        KeyboardRow rowDateBorn = new KeyboardRow();
        KeyboardRow rowPhone = new KeyboardRow();
        KeyboardRow rowImage = new KeyboardRow();

        rowCancel.add("Cancelar");
        rowMenu.add("Menú Principal");
        rowName.add(contactId+" Nombre");
        rowSecondName.add(contactId+" Segundo Nombre");
        rowFirstLastName.add(contactId+" Primer Apellido");
        rowSecondLastName.add(contactId+" Segundo Apellido");
        rowMail.add(contactId+" Email");
        rowDateBorn.add(contactId+" Fecha de Nacimiento");
        rowPhone.add(contactId+" Telefono");
        rowImage.add(contactId+" Imagen");

        keyboard.add(rowMenu);
        keyboard.add(rowCancel);
        keyboard.add(rowName);
        keyboard.add(rowSecondName);
        keyboard.add(rowFirstLastName);
        keyboard.add(rowSecondLastName);
        keyboard.add(rowMail);
        keyboard.add(rowDateBorn);
        keyboard.add(rowPhone);
        keyboard.add(rowImage);

        return keyboard;
    }

    public static List<KeyboardRow> keywordUpdateDateBornContact(int qu) {
        List<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow row= new KeyboardRow();
        KeyboardRow rowOne= new KeyboardRow();
        KeyboardRow rowTwo= new KeyboardRow();
        KeyboardRow rowThree= new KeyboardRow();
        KeyboardRow rowFour= new KeyboardRow();

        switch (qu){
            case 0:
                LOGGER.info("[Keyword]Pedir dia de nacimiento");
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
            case 1:
                LOGGER.info("[Keyword]Pedir mes de nacimiento");
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
            case 2:
                LOGGER.info("[Keyword]Pedir año de nacimiento");
                row.add("Cancelar");
                keyboard.add(row);
                break;
        }
        return keyboard;
    }

}