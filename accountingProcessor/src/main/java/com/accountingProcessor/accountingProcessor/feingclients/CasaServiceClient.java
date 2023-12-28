package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.dto.AccountBalanceResponse;
import com.accountingProcessor.accountingProcessor.dto.BalanceUpdateRequest;
import com.accountingProcessor.accountingProcessor.dto.CustomerAccount;
import com.accountingProcessor.accountingProcessor.exceptions.AccountingCustomException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("CASA-ACCOUNTS-SERVICE")
public interface CasaServiceClient {

    @GetMapping("/getAccountDetails/{accountID}")
    CustomerAccount getAccountDetails(@PathVariable("customerAccNo")Integer customerAccNo);

    @GetMapping("/getAccountBalance/{accountID}")
    AccountBalanceResponse getAccountBalance(@PathVariable("customerAccNo")  Integer customerAccNo);

    @PostMapping("/singlebalanceUpdate")
    void updateCustomerbalance( @RequestBody BalanceUpdateRequest balanceUpdateRequest);

    @PostMapping("/multibalanceUpdate")
    @CircuitBreaker(name= "accBalCircuitBreaker", fallbackMethod = "balanceUpdateCB")
    ResponseEntity<Map<String,Object>> updatemultiCustomerbalance(@RequestBody List<BalanceUpdateRequest> balanceUpdateRequest);

    default ResponseEntity<Map<String,Object>> balanceUpdateCB(List<BalanceUpdateRequest> balanceUpdateRequest, AccountingCustomException exp)
    {
        System.out.println(" Inside the custom exeption Circuit Breaker Method " +exp.getCustomAccountExceptionResponse().getMessage());
        throw exp;
    }

    default ResponseEntity<Map<String,Object>> balanceUpdateCB(List<BalanceUpdateRequest> balanceUpdateRequest, Exception exp)
    {
        System.out.println(" Inside the Circuit Breaker Method");
        throw new RuntimeException(exp.getMessage());
    }

}
