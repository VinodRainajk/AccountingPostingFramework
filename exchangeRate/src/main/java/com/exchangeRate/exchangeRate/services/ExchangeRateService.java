package com.exchangeRate.exchangeRate.services;

import com.exchangeRate.exchangeRate.dto.CurrencyExchangeRate;
import com.exchangeRate.exchangeRate.model.CurrencyExchangeRateModel;
import com.exchangeRate.exchangeRate.repository.ExchangeRateRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExchangeRateService {

    ExchangeRateRepository exchangeRateRepository;

    @Autowired
    public ExchangeRateService(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    public CurrencyExchangeRateModel getExchnageRate(String currency1, String currency2)
    {
        Optional<CurrencyExchangeRate> exchangeRates = exchangeRateRepository.getExchnageRate(currency1,currency2);
        ModelMapper mapper = new ModelMapper();
        if(!exchangeRates.isPresent())
        {
            exchangeRates = exchangeRateRepository.getExchnageRate(currency2,currency1);
            CurrencyExchangeRateModel model=  mapper.map(exchangeRates.get(), CurrencyExchangeRateModel.class);
            model.setExchangeRate(1/model.getExchangeRate());
            return model;
        }else
        {
            return mapper.map(exchangeRates.get(), CurrencyExchangeRateModel.class);
        }

    }



}
