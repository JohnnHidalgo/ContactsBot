package com.example.AgendaSoftware.api;

import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.dto.ContactDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/contact")
public class ContactController {
    private ContactRepository contactRepository;
    @Autowired
    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<ContactDto> all() {
        List<ContactDto> contactDtoList = new ArrayList<>();
        for (Contact contact:contactRepository.findAll()) {
            contactDtoList.add(new ContactDto(contact));
        }
        return contactDtoList;
    }

}
