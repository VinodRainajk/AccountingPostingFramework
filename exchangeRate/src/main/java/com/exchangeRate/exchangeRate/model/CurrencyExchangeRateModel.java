package com.exchangeRate.exchangeRate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyExchangeRateModel {

    String currency1;
    String currency2;
    double exchangeRate;

    @Override
    public String toString() {
        return "CurrencyExchangeRateModel{" +
                "currency1='" + currency1 + '\'' +
                ", currency2='" + currency2 + '\'' +
                ", exchangeRate=" + exchangeRate +
                '}';
    }
}