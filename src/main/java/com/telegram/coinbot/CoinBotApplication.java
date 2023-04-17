package com.telegram.coinbot;

import com.telegram.coinbot.service.AlarmService;
import javax.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@AllArgsConstructor
@EnableScheduling
@SpringBootApplication
public class CoinBotApplication {

    private final AlarmService telegramService;

    @PostConstruct
    public void start() {
        {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(telegramService);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        SpringApplication.run(CoinBotApplication.class, args);

//        SpringApplication application = new SpringApplication(CoinBotApplication.class);
//        application.setWebApplicationType(WebApplicationType.NONE);
//        application.run(args);

    }

}
