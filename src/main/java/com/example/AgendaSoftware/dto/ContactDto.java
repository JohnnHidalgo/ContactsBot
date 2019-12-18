package com.example.AgendaSoftware.dto;

import com.example.AgendaSoftware.domain.Contact;

import java.util.Date;

public class ContactDto {
    private Integer idContact;
    private String firstName;
    private String secondName;
    private String firstLastName;
    private String secondLastName;
    private String mail;
    private Date dateBorn;
    private String image;


    public ContactDto() {
    }

    public ContactDto(Contact contact) {
        this.idContact = contact.getIdContact();
        this.firstName = contact.getFirstName();
        this.secondName = contact.getSecondName();
        this.firstLastName = contact.getFirstLastName();
        this.secondLastName = contact.getSecondLastName();
        this.mail = contact.getMail();
        this.dateBorn = contact.getDateBorn();
        this.image = contact.getImage();
    }

    public Integer getIdContact() {
        return idContact;
    }

    public void setIdContact(Integer idContact) {
        this.idContact = idContact;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Date getDateBorn() {
        return dateBorn;
    }

    public void setDateBorn(Date dateBorn) {
        this.dateBorn = dateBorn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
