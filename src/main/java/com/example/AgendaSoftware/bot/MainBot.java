package com.example.AgendaSoftware.bot;

import com.example.AgendaSoftware.bl.BotBl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class MainBot extends TelegramLongPollingBot {

    BotBl botBl;
    public MainBot(BotBl botBl){
        this.botBl = botBl;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(update);
        update.getMessage().getFrom().getId();
        if (update.hasMessage() && update.getMessage().hasText() || update.getMessage().hasPhoto()) {
            SendMessage message=new SendMessage();
            SendPhoto photo = new SendPhoto();

            botBl.processUpdateMesage(update,message,photo);
             try {
                 if(message == null){
                     message.setText("No entiendo lo que me quieres decir");
                     this.execute(message);
                 }else if(photo.getPhoto() == null && message!=null){
                     this.execute(message);
                 }else if (message != null && photo.getPhoto() != null){
                     this.execute(message);
                     this.execute(photo);
                 }
             } catch (TelegramApiException e) {
                 e.printStackTrace();
             }
             catch(NullPointerException e )
             {
                 System.out.print("NullPointerException caught");
             }
        }

        if (update.hasMessage() && update.getMessage().hasPhoto()) {
            SendMessage message=new SendMessage();
            SendPhoto photo = new SendPhoto();

            botBl.processUpdateMesage(update,message,photo);
            try {
                if(message == null){
                    message.setText("No entiendo lo que me quieres decir");
                    this.execute(message);
                }
                else if (message != null && photo.getPhoto() != null){
                    this.execute(photo);
                }
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            catch(NullPointerException e )
            {
                System.out.print("NullPointerException caught");
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
