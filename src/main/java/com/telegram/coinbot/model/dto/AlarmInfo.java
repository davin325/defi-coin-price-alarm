package com.telegram.coinbot.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlarmInfo {
    private String name;
    private String symbol;
    private String contract;
    private String type;
    private Double price;

}
