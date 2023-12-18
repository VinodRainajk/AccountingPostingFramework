package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.dto.AccountBalanceResponse;
import com.accountingProcessor.accountingProcessor.dto.AccountBalanceUpdateRequest;
import com.accountingProcessor.accountingProcessor.dto.CurrencyExchangeRate;
import com.accountingProcessor.accountingProcessor.dto.CustomerAccount;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient("CASA-ACCOUNTS-SERVICE")
public interface CasaServiceClient {

    @GetMapping("/getAccountDetails/{accountID}")
    CustomerAccount getAccountDetails(@PathVariable("customerAccNo")Integer customerAccNo);

    @GetMapping("/getAccountBalance/{accountID}")
    AccountBalanceResponse getAccountBalance(@PathVariable("customerAccNo")  Integer customerAccNo);

    @PostMapping("/multibalanceUpdate")
    ResponseEntity updateCustomerbalance(@RequestParam List<AccountBalanceUpdateRequest> accountBalanceUpdateRequest);
}
