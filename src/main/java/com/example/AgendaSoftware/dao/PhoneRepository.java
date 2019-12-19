package com.example.AgendaSoftware.dao;
import com.example.AgendaSoftware.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    @Query(value = "SELECT * FROM phone where  id_contact_phone = ?1 ORDER BY id_phone DESC LIMIT 1", nativeQuery = true)
    public Phone findphoneByContactId(Integer userId);

    List<Phone> findAllByStatus(int status);



}
