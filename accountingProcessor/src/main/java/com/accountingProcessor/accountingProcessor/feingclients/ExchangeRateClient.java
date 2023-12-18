package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.dto.CurrencyExchangeRate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("EXCHANGE-RATE-SERVICE")
public interface ExchangeRateClient {

    @GetMapping("/getRate")
    public CurrencyExchangeRate getExchangeRate(@RequestParam("currency1") String currency1, @RequestParam("currency2") String currency2);
}
