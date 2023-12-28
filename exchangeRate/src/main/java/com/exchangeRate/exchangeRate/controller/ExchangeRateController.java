package com.exchangeRate.exchangeRate.controller;

import com.exchangeRate.exchangeRate.model.CurrencyExchangeRateModel;
import com.exchangeRate.exchangeRate.services.ExchangeRateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity getExchnageRatePair(@RequestParam String currency1, @RequestParam String currency2) {
         System.out.println("Inside  getExchnageRatePair");
         System.out.println("Currency1 "+currency1);
         System.out.println("currency2 "+currency2);
         CurrencyExchangeRateModel exchnageRate = exchangeRateService.getExchnageRate(currency1, currency2);
          System.out.println("exchnageRate "+exchnageRate);
         return ResponseEntity.status(HttpStatus.OK).body(exchnageRate);
    }
}
