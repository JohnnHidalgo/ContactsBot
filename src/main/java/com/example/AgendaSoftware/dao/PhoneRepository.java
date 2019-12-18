package com.example.AgendaSoftware.dao;

import com.example.AgendaSoftware.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PhoneRepository extends JpaRepository<Phone, Integer> {
    List<Phone> findAllByStatus(int status);
}
