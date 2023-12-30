package com.accountingProcessor.accountingProcessor.controller;

import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import com.accountingProcessor.accountingProcessor.services.AccountingPostingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AccountingPostingController {
    AccountingPostingService accountingPostingService;
    private static final Logger logger = LogManager.getLogger(AccountingPostingController.class);


    @Autowired
    public AccountingPostingController(AccountingPostingService accountingPostingService) {
        this.accountingPostingService = accountingPostingService;
    }

    @GetMapping("/getTxnDetails/{txnRefno}")
    public List<AccountingModel> getTxnByRefNo(@PathVariable("txnRefno") String txnRefno)
    {
        logger.info("Inside the getTxnByRefNo");
      //  System.out.println("Inside the getTxnByRefNo");
        return accountingPostingService.getTransactions(txnRefno);
    }

    @PostMapping("/postAccounting")
    public ResponseEntity getTxnByRefNo(@RequestBody List<AccountingModel> txnList)
    {
        logger.info("Inside the postAccounting");
        return accountingPostingService.saveTransaction(txnList);
    }
}
