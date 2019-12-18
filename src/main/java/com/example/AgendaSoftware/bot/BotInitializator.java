package com.example.AgendaSoftware.bot;

import com.example.AgendaSoftware.bl.BotBl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import javax.annotation.PostConstruct;

@Component
public class BotInitializator {

    BotBl botBl;

    @Autowired
    public BotInitializator(BotBl botBl) {
        this.botBl = botBl;
    }

    public BotInitializator() {
    }

    @PostConstruct
    public void init() {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new MainBot(botBl));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
