package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.feingclients.MessagePublisherClient;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

    private final MessagePublisherClient messagePublisherClient;

    @Autowired
    public MessagePublisher(MessagePublisherClient messagePublisherClient) {
        this.messagePublisherClient = messagePublisherClient;
    }


    public void  SendMessage(AccountingModel messagePublisherModel)
    {
        ResponseEntity responseEntity =  messagePublisherClient.sendAccountingDetails("Accounting",messagePublisherModel);

    }
}
