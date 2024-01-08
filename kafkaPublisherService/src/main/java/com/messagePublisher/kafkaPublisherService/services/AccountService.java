package com.messagePublisher.kafkaPublisherService.services;

import com.messagePublisher.kafkaPublisherService.configuration.KafkaBalanceProducer;
import com.messagePublisher.kafkaPublisherService.model.AccountBalanceDetails;
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
    public ResponseEntity sendBalanceDetails(String topicName, List<AccountBalanceDetails> accountBalanceDetailslist) {

        accountBalanceDetailslist.stream()
                        .forEach((a)-> kafkaTemplate.send(topicName,a.getCustomerAccNo().toString(),a));


        return ResponseEntity.status(HttpStatus.OK).body("Message Sent Successfully");
    }
}
