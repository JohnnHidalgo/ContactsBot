package com.example.AgendaSoftware.bl;

import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.domain.Phone;
import com.example.AgendaSoftware.dto.PhoneDto;
import com.example.AgendaSoftware.dto.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PhoneBl {
    PhoneRepository phoneRepository;
    @Autowired
    public PhoneBl(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public Phone findPhoneById(Integer pk){
        Optional<Phone> optional = this.phoneRepository.findById(pk);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            throw new RuntimeException("Record cannot found for Phone with ID: " + pk);
        }
    }

    public List<PhoneDto> findAllPhone() {
        List<PhoneDto> phoneDtoList = new ArrayList<>();
        for (Phone phone:phoneRepository.findAllByStatus(Status.ACTIVE.getStatus())) {
            phoneDtoList.add(new PhoneDto(phone));
        }
        return phoneDtoList;
    }

}
