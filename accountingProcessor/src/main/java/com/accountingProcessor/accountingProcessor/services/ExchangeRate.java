package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.feingclients.ExchangeRateClient;
import com.accountingProcessor.accountingProcessor.model.CurrencyExchangeRateModel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;

@Component
public class ExchangeRate {

    private ExchangeRateClient exchangeRateClient;
    private final Environment environment;

    private static final Logger LOGGER = LogManager.getLogger(ExchangeRate.class);


    @Autowired
    public ExchangeRate(ExchangeRateClient exchangeRateClient, Environment environment) {
        this.exchangeRateClient = exchangeRateClient;
        this.environment = environment;
    }

    public double getDerviveExchangeRate(String currency1, String currency2)
    {
        if(currency1.equals(currency2))
            return 1D;

        ResponseEntity responseEntity = exchangeRateClient.getExchangeRate(currency1, currency2);
        CurrencyExchangeRateModel currencyExchangeRateModel =  (CurrencyExchangeRateModel)responseEntity.getBody();
        LOGGER.info("Returned value from service is "+ currencyExchangeRateModel);
        return currencyExchangeRateModel.getExchangeRate();
    }

    public double getDerviveAmount(Double exchangeRate, Double amount)
    {
        DecimalFormat decimalFormat = new DecimalFormat();
        decimalFormat.setMinimumFractionDigits(Integer.valueOf(environment.getProperty("decimalPlaces")));
        String result = decimalFormat.format(exchangeRate*amount);
        LOGGER.info("Returned value is "+ result);
      return Double.parseDouble(result);
    }

}
