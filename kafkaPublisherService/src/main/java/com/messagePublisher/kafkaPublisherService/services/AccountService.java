package com.messagePublisher.kafkaPublisherService.services;

import com.messagePublisher.kafkaPublisherService.configuration.KafkaBalanceProducer;
import com.messagePublisher.kafkaPublisherService.controller.MessagePublisherContoller;
import com.messagePublisher.kafkaPublisherService.model.AccountBalanceDetails;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    @Qualifier("accountkafkaTemplate")
    private KafkaTemplate<String, AccountBalanceDetails> kafkaTemplate;

    private static final Logger lOGGER = LogManager.getLogger(AccountService.class);
    public ResponseEntity sendBalanceDetails(String topicName, List<AccountBalanceDetails> accountBalanceDetailslist) {

        lOGGER.info("Inside the sendBalanceDetails");
        accountBalanceDetailslist.stream()
                        .forEach((a)-> kafkaTemplate.send(topicName,a.getCustomerAccNo().toString(),a));

        lOGGER.info("Message Sent Successfully");
        return ResponseEntity.status(HttpStatus.OK).body("Message Sent Successfully");
    }
}
