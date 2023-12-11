package com.casaService.casaService.controller;

import com.casaService.casaService.service.AccountBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerAccountController {

    AccountBalanceService accountBalanceService;

    @Autowired
    public CustomerAccountController(AccountBalanceService accountBalanceService) {
        this.accountBalanceService = accountBalanceService;
    }

    @PostMapping
    public void getLock()
    {
        accountBalanceService.getLockonAccount(123);
    }


}
