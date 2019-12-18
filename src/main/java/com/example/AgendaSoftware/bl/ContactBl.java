package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactBl {
    ContactRepository contactRepository;

    @Autowired
    public ContactBl(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }
}
