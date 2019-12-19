package com.example.AgendaSoftware.bl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageBl {

    private static final Logger LOGGER= LoggerFactory.getLogger(MessageBl.class);
    private PhoneBl phoneBl;
    private ContactBl contactBl;

    @Autowired
    public MessageBl(PhoneBl phoneBl, ContactBl contactBl){
        this.phoneBl = phoneBl;
        this.contactBl = contactBl;
    }

    public String inicio()
    {
        return "Bienvenido";
    }




}
