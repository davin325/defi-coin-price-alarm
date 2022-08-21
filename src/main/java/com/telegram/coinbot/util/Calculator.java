package com.telegram.coinbot.util;

public class Calculator {

    public static double roundString(String input, Integer digit) {
        return Math.round(Double.parseDouble(input)*digit)/(double)digit;
    }

    public static double roundDouble(Double input, Integer digit) {
        return Math.round(input*digit)/(double)digit;
    }

    public static void main(String[] args) {
        System.out.println(roundDouble(23.454545,100));
    }
}
