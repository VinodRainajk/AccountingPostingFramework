package com.ExchangeRateService.ExchangeRateService.Model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyExchnageRateModel {

    String currency1;
    String currency2;
    String exchnageRate;

}
