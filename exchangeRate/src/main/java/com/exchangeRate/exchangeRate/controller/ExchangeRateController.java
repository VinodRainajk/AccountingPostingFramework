package com.exchangeRate.exchangeRate.controller;

import com.exchangeRate.exchangeRate.model.CurrencyExchangeRateModel;
import com.exchangeRate.exchangeRate.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {
     ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
          this.exchangeRateService = exchangeRateService;
        }

    @GetMapping({"/getRate"})
    public CurrencyExchangeRateModel getExchnageRatePair(@RequestParam String currency1, @RequestParam String currency2) {
         System.out.println("Currency1 "+currency1);
        System.out.println("currency2 "+currency2);
        return exchangeRateService.getExchnageRate(currency1,currency2);
    }
}
