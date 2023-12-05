package com.accountingProcessor.accountingProcessor.controller;

import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.services.AccountingPostingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountingPostingController {
    AccountingPostingService accountingPostingService;

    @Autowired
    public AccountingPostingController(AccountingPostingService accountingPostingService) {
        this.accountingPostingService = accountingPostingService;
    }

    @GetMapping("/getTxnDetails{txnRefno}")
    public List<AccountingModel> getTxnByRefNo(@PathVariable("txnRefno") String txnRefno)
    {
        return accountingPostingService.getTransactions(txnRefno);
    }

    @PostMapping("/postAccounting")
    public List<AccountingModel> getTxnByRefNo(@RequestBody List<AccountingModel> txnList)
    {
        return accountingPostingService.saveTransaction(txnList);
    }
}
