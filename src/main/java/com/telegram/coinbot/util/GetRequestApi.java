package com.telegram.coinbot.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class GetRequestApi {

    @Value("${tokenUrl}")
    private String tokenUrl;

    @Value("${exchangeRateUrl}")
    private String exchangeRateUrl;

    WebClient webClient2 = WebClient.builder()
            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .build();

    public Mono<HashMap> getByMap(String param) {
        param = StringUtils.hasText(param) ? param : "";
        return webClient2.get()
                .uri(tokenUrl + param)
                .retrieve()
                .bodyToMono(HashMap.class);
    }

    public Mono<List> getByList(String param) {
        param = StringUtils.hasText(param) ? param : "";
        return webClient2.get()
                .uri(exchangeRateUrl + param)
                .retrieve()
                .bodyToMono(List.class);
    }
}
