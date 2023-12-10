package com.accountingProcessor.accountingProcessor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyExchangeRate {


    Integer id;
    String currency1;
    String currency2;
    double exchangeRate;
}
