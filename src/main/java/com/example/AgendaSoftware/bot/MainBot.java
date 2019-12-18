package com.example.AgendaSoftware.bot;

import com.example.AgendaSoftware.bl.PhoneBl;
import com.example.AgendaSoftware.domain.Phone;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MainBot extends TelegramLongPollingBot {

    PhoneBl phoneBl;

    public MainBot(PhoneBl phoneBl){
        this.phoneBl = phoneBl;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        if (update.hasMessage() && update.getMessage().hasText()) {
            Phone phone = phoneBl.findPhoneById(1);
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText("Phone from: " + phone.getNumberPhone());
            try {
                this.execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "SoftwareUcbbot";
    }

    @Override
    public String getBotToken() {
        return "1010842157:AAHyoS76gC-BDJikCbPicLiHELObXJVoays";
    }
}
