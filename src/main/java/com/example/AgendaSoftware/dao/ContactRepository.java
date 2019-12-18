package com.example.AgendaSoftware.dao;

import com.example.AgendaSoftware.domain.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Integer> {

}