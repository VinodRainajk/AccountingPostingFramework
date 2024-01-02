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

@Service
public class AccountService {
    @Autowired
    @Qualifier("accountkafkaTemplate")
    private KafkaTemplate<String, AccountBalanceDetails> kafkaTemplate;
    public ResponseEntity sendBalanceDetails(String topicName, AccountBalanceDetails accountBalanceDetails) {
                kafkaTemplate.send(topicName,accountBalanceDetails.getCustomerAccNo().toString(),accountBalanceDetails);

        return ResponseEntity.status(HttpStatus.OK).body("Message Sent Successfully");
    }
}
