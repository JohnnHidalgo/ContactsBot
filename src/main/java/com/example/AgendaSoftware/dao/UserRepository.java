package com.example.AgendaSoftware.dao;

import com.example.AgendaSoftware.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByIdUserbot(String idUserbot);
}