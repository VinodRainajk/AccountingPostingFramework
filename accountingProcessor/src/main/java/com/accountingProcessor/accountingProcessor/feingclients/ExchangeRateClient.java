package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.model.CurrencyExchangeRateModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("EXCHANGE-RATE-SERVICE")
public interface ExchangeRateClient {

    @GetMapping("/getRate")
    public ResponseEntity<CurrencyExchangeRateModel> getExchangeRate(@RequestParam("currency1") String currency1, @RequestParam("currency2") String currency2);
}
