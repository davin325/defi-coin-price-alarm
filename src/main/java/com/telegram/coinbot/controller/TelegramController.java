package com.telegram.coinbot.controller;

import com.telegram.coinbot.service.AlarmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@Slf4j
@AllArgsConstructor
@RestController
public class TelegramController {

    private final AlarmService telegramService;

    @PostConstruct
    public void start() {
        {
            try {
                log.info("start@@@@@@@@@@@@@@@@@@@@@@@@");
                TelegramBotsApi botsApi;
                botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(telegramService);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @GetMapping("/alarm")
    @Scheduled(cron = "0 0 08-23 * * *")
    public void alarm() {
       telegramService.alarm();
    }

}
