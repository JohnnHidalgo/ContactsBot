package com.example.AgendaSoftware.dao;

import com.example.AgendaSoftware.domain.Contact;
import com.example.AgendaSoftware.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    @Query(value = "SELECT * FROM contact where  id_user_contact = ?1 ORDER BY id_contact DESC LIMIT 1", nativeQuery = true)
    Contact findById(User idUserContact);

}