package com.example.AgendaSoftware.bot;

import com.example.AgendaSoftware.bl.PhoneBl;
import com.example.AgendaSoftware.dao.ContactRepository;
import com.example.AgendaSoftware.dao.PhoneRepository;
import com.example.AgendaSoftware.domain.Phone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class BotInitializator {

    PhoneBl phoneBl;

    @Autowired
    public BotInitializator(PhoneBl phoneBl){
        this.phoneBl = phoneBl;
    }
    public BotInitializator(){

    }

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MainBot(phoneBl));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
