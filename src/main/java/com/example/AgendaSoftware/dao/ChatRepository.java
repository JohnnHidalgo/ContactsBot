package com.example.AgendaSoftware.dao;

import com.example.AgendaSoftware.domain.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatRepository extends JpaRepository<Chat,Integer> {
    @Query(value = "SELECT * FROM chat where  id_user_chat = ?1 ORDER BY id_chat DESC LIMIT 1", nativeQuery = true)
    public Chat findLastChatByUserId(Integer userId);
}
