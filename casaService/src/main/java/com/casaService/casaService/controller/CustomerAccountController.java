package com.casaService.casaService.controller;

import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.CustomerAccountModel;
import com.casaService.casaService.service.AccountBalanceService;
import com.casaService.casaService.service.CustomerWrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CustomerAccountController {

    AccountBalanceService accountBalanceService;
    CustomerWrapperService customerWrapperService;

    @Autowired
    public CustomerAccountController(AccountBalanceService accountBalanceService, CustomerWrapperService customerWrapperService) {
        this.accountBalanceService = accountBalanceService;
        this.customerWrapperService = customerWrapperService;
    }

    @PostMapping("/singlebalanceUpdate")
    public ResponseEntity updateCustomerBalance(@RequestBody BalanceUpdateRequest balanceUpdateRequest)
    {
        List<BalanceUpdateRequest> multiBalanceUpdate = new ArrayList<>();
        multiBalanceUpdate.add(balanceUpdateRequest);
       return customerWrapperService.multiBalanceUpdate(multiBalanceUpdate);

    }

    @PostMapping("/multibalanceUpdate")
    public ResponseEntity updateCustomerBalance(@RequestBody List<BalanceUpdateRequest> balanceUpdateReqList)
    {
        return customerWrapperService.multiBalanceUpdate(balanceUpdateReqList);

    }


    @PostMapping("/multiBalanceUpdate")
    public ResponseEntity multiBalanceUpdate(@RequestBody List<BalanceUpdateRequest> balanceUpdaList)
    {
        return customerWrapperService.multiBalanceUpdate(balanceUpdaList);

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

    @PostMapping("/closeAccount/{accountID}")
    public ResponseEntity closeAccount(@PathVariable("accountID") Integer AccountNumber)
    {
        return customerWrapperService.closeCustomerAccount(AccountNumber);
    }
}
