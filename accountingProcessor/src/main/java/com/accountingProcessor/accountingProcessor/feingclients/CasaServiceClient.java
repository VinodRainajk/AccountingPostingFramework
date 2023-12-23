package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.dto.AccountBalanceResponse;
import com.accountingProcessor.accountingProcessor.dto.BalanceUpdateRequest;
import com.accountingProcessor.accountingProcessor.dto.CustomerAccount;
import com.accountingProcessor.accountingProcessor.model.TransactionResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED_VALUE;

@FeignClient("CASA-ACCOUNTS-SERVICE")
public interface CasaServiceClient {

    @GetMapping("/getAccountDetails/{accountID}")
    CustomerAccount getAccountDetails(@PathVariable("customerAccNo")Integer customerAccNo);

    @GetMapping("/getAccountBalance/{accountID}")
    AccountBalanceResponse getAccountBalance(@PathVariable("customerAccNo")  Integer customerAccNo);

    @PostMapping("/singlebalanceUpdate")
    void updateCustomerbalance( @RequestBody BalanceUpdateRequest balanceUpdateRequest);

    @PostMapping("/multibalanceUpdate")
    ResponseEntity<Map<String,Object>> updatemultiCustomerbalance(@RequestBody List<BalanceUpdateRequest> balanceUpdateRequest);
}
