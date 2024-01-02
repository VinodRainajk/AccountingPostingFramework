package com.messagePublisher.kafkaPublisherService.controller;


import com.messagePublisher.kafkaPublisherService.model.AccountBalanceDetails;
import com.messagePublisher.kafkaPublisherService.model.AccountingRequest;
import com.messagePublisher.kafkaPublisherService.services.AccountService;
import com.messagePublisher.kafkaPublisherService.services.AccountingService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessagePublisherContoller {

    private static final Logger lOGGER = LogManager.getLogger(MessagePublisherContoller.class);
    AccountingService accountingService;
    AccountService accountService;

    @Autowired
    public MessagePublisherContoller(AccountingService accountingService, AccountService accountService) {
        this.accountingService = accountingService;
        this.accountService = accountService;
    }

    @PostMapping("/accounting/{topicName}")
    public ResponseEntity sendAccountingDetails(@PathVariable String topicName , @RequestBody AccountingRequest accountingRequest )
    {
        lOGGER.info(" inside "+accountingRequest);
        return accountingService.sendAccountingDetails(topicName,accountingRequest);
    }

    @PostMapping("/balanceUpdate/{topicName}")
    public ResponseEntity sendbalanceUpdateDetails(@PathVariable String topicName,
                                                   @RequestBody AccountBalanceDetails accountBalanceDetails)
    {
        lOGGER.info(" inside Balance Update"+accountBalanceDetails);
        return accountService.sendBalanceDetails(topicName,accountBalanceDetails);
    }


}
