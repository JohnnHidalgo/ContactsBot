package com.example.AgendaSoftware.api;

import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.domain.Phone;
import com.example.AgendaSoftware.dto.ContactDto;
import com.example.AgendaSoftware.dto.PhoneDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/v1/phone")
public class PhoneController {
    private PhoneRepository phoneRepository;
    @Autowired
    public PhoneController(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }


    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    List<PhoneDto> all() {
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        for (Phone phone:phoneRepository.findAll()) {
            phoneDtoList.add(new PhoneDto(phone));
        }
        return phoneDtoList;
    }

}
