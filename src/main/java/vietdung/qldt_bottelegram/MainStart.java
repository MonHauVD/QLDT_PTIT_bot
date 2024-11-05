package vietdung.qldt_bottelegram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import vietdung.qldt_bottelegram.Ver1.Bot.BotTelegram;
import vietdung.qldt_bottelegram.Ver2.Bot2.BotTelegram2;

@SpringBootApplication
public class MainStart {

    public static void main(String[] args) throws TelegramApiException {
        SpringApplication.run(MainStart.class, args);
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        // BotTelegram bot = new BotTelegram();   //Ban cu, phai lay du lieu moi lan hoi
        BotTelegram2 bot = new BotTelegram2();      //Ban moi, lay du lieu mot lan cho nhieu lan
        botsApi.registerBot(bot);
        System.out.println("Bot started!");
    }

}
