package com.exchangeRate.exchangeRate.controller;

import com.exchangeRate.exchangeRate.model.CurrencyExchangeRateModel;
import com.exchangeRate.exchangeRate.services.ExchangeRateService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeRateController {

    private static final Logger lOGGER = LogManager.getLogger(ExchangeRateController.class);
     ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
          this.exchangeRateService = exchangeRateService;
        }

    @GetMapping({"/getRate"})
    public ResponseEntity getExchnageRatePair(@RequestParam String currency1, @RequestParam String currency2) {
        lOGGER.info("Inside  getExchnageRatePair");
        lOGGER.info("Currency1 "+currency1);
        lOGGER.info("currency2 "+currency2);
         CurrencyExchangeRateModel exchnageRate = exchangeRateService.getExchnageRate(currency1, currency2);
        lOGGER.info("exchnageRate "+exchnageRate);
         return ResponseEntity.status(HttpStatus.OK).body(exchnageRate);
    }
}
