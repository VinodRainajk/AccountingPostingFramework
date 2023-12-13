package com.casaService.casaService.controller;

import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.CustomerAccountModel;
import com.casaService.casaService.service.AccountBalanceService;
import com.casaService.casaService.service.CustomerWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CustomerAccountController {

    AccountBalanceService accountBalanceService;
    CustomerWrapperService customerWrapperService;

    @Autowired
    public CustomerAccountController(AccountBalanceService accountBalanceService, CustomerWrapperService customerWrapperService) {
        this.accountBalanceService = accountBalanceService;
        this.customerWrapperService = customerWrapperService;
    }

    @PostMapping("/balanceUpdate")
    public BalanceUpdateRequest updateCustomerBalance(@RequestBody BalanceUpdateRequest balanceUpdateRequest)
    {
       return customerWrapperService.updateCustomerBalance(balanceUpdateRequest);
       // accountBalanceOnlineService.getLockonAccount(123);
    }

    @PostMapping("/newAccount")
    public CustomerAccountModel createNewAccount(@RequestBody CustomerAccountModel customerAccountModel)
    {
        System.out.println( "wrapper "+ customerAccountModel.getCustomerName());
        return customerWrapperService.createNewAccount(customerAccountModel);
    }

    @GetMapping("/getAccountDetails/{accountID}")
    public CustomerAccountModel getAccountDetails(@PathVariable("accountID") Integer AccountNumber)
    {
        return customerWrapperService.getAccountDetails(AccountNumber);
    }

    @GetMapping("/getAccountBalance/{accountID}")
    public BalanceResponse getBalance(@PathVariable("accountID") Integer AccountNumber)
     {
         return customerWrapperService.getAccountBalance(AccountNumber);
     }
}
