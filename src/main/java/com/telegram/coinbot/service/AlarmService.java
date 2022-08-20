package com.telegram.coinbot.service;

import com.telegram.coinbot.model.dto.AlarmInfo;
import com.telegram.coinbot.model.mapper.CoinBotMapper;
import com.telegram.coinbot.util.GetRequestApi;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RequiredArgsConstructor
@Service
public class AlarmService extends TelegramLongPollingBot {
    // 생성자로 빈 주입
    //private final Telegram telegram;
    private final CoinBotMapper coinBotMapper;
    private final GetRequestApi getRequestApi;

    @Value("${botUserName}")
    private String botUserName;

    @Value("${botToken}")
    private String botToken;

    @Value("${myChatId}")
    private String myChatId;

    public void alarm() {

        List<AlarmInfo> alarmInfoList = coinBotMapper.selectAllAlarmInfo();
        String param = "";
        for (AlarmInfo x : alarmInfoList) {
            param += x.getContract() + ",";
        }
        param = StringUtils.hasText(param) ? param.substring(0, param.length() - 1) : param;
        log.info("[alarm] param = {}", param);

        Mono<HashMap> test = getRequestApi.getByMap(param);
        for (AlarmInfo x : alarmInfoList) {
            x.setPrice(String.format("%.4f", test.block().get(x.getContract())));
        }

        Mono<List> test1 = getRequestApi.getByList(null);
        Map<String, String> map = new HashMap<>();
        map = (Map<String, String>) test1.block().get(0);
        String exchangeRate = String.valueOf(map.get("basePrice"));
        log.info("[alarm - exchangeRate] = {}", map.get("basePrice"));
        //telegram.alarm(alarmInfoList, exchangeRate);

        SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
        StringBuilder sb = new StringBuilder();
        sb.append("환율: " + exchangeRate + "원\n");
        for (AlarmInfo x : alarmInfoList) {
            sb.append(x.getName() + "[" + x.getSymbol() + "]" + ": " + x.getPrice() + "$|");
            sb.append(Double.parseDouble(x.getPrice()) * Double.parseDouble(exchangeRate) + "\n");
        }

        message.setText(sb.toString());
        message.setChatId(getMyChatId());
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        // We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
            message.setChatId(update.getMessage().getChatId().toString());
            if (update.getMessage().getText().charAt(0) == '/') {
                alarm();
            } else {
                message.setText(update.getMessage().getText());
                try {
                    execute(message); // Call method to send the message
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return botUserName;
    }

    @Override
    public String getBotToken() {
        // TODO
        return botToken;
    }

    public String getMyChatId() {
        return myChatId;
    }
}
