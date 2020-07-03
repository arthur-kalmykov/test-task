package com.itembase.testtask.model.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum Currency {
    EUR, AED, ARS, AUD, BGN, BRL, BSD, CAD, CHF, CLP, CNY, COP, CZK, DKK, DOP, EGP, FJD, GBP, GTQ, HKD, HRK, HUF, IDR,
    ILS, INR, ISK, JPY, KRW, KZT, MXN, MYR, NOK, NZD, PAB, PEN, PHP, PKR, PLN, PYG, RON, RUB, SAR, SEK, SGD, THB, TRY,
    TWD, UAH, USD, UYU, ZAR;

    @JsonCreator
    public static Currency fromString(String key) {
        for(Currency currency : Currency.values()) {
            if(currency.name().equalsIgnoreCase(key)) {
                return currency;
            }
        }
        return null;
    }
}
