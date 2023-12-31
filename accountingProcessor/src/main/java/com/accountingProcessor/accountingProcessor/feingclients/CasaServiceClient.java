package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.exceptions.AccountingCustomException;
import com.accountingProcessor.accountingProcessor.model.AccountBalanceRequest;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient("CASA-ACCOUNTS-SERVICE")
public interface CasaServiceClient {

    @PostMapping("/singlebalanceUpdate")
    void updateCustomerbalance( @RequestBody AccountBalanceRequest balanceUpdateRequest);

    @PostMapping("/multibalanceUpdate")
    @CircuitBreaker(name= "accBalCircuitBreaker", fallbackMethod = "balanceUpdateCB")
    ResponseEntity<Map<String,Object>> updatemultiCustomerbalance(@RequestBody List<AccountBalanceRequest> balanceUpdateRequest);

    default ResponseEntity<Map<String,Object>> balanceUpdateCB(List<AccountBalanceRequest> balanceUpdateRequest, AccountingCustomException exp)
    {
        System.out.println(" Inside the custom exeption Circuit Breaker Method " +exp.getCustomAccountExceptionResponse().getMessage());
        throw exp;
    }

    default ResponseEntity<Map<String,Object>> balanceUpdateCB(List<AccountBalanceRequest> balanceUpdateRequest, Exception exp)
    {
        System.out.println(" Inside the Circuit Breaker Method");
        throw new RuntimeException(exp.getMessage());
    }

}
