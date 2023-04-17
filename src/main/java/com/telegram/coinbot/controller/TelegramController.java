package com.telegram.coinbot.controller;

import com.telegram.coinbot.service.AlarmService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@AllArgsConstructor
@RestController
public class TelegramController {

    private final AlarmService telegramService;

    @GetMapping("/alarm")
    @Scheduled(cron = "0 0 08-23 * * *")
    public void alarm() {
       telegramService.alarm();
    }

}
