package com.accountingProcessor.accountingProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public class CurrencyExchangeRateModel{

    String currency1;

    public CurrencyExchangeRateModel(String currency1, String currency2, double exchangeRate) {
        this.currency1 = currency1;
        this.currency2 = currency2;
        this.exchangeRate = exchangeRate;
    }

    public CurrencyExchangeRateModel() {
    }

    String currency2;

    public String getCurrency1() {
        return currency1;
    }

    public void setCurrency1(String currency1) {
        this.currency1 = currency1;
    }

    public String getCurrency2() {
        return currency2;
    }

    public void setCurrency2(String currency2) {
        this.currency2 = currency2;
    }

    public double getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(double exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

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