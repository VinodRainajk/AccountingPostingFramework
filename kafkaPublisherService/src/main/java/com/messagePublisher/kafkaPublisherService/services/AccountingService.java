package com.messagePublisher.kafkaPublisherService.services;

import com.messagePublisher.kafkaPublisherService.model.AccountingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountingService {

    @Autowired
    @Qualifier("accountingKafkaTemplate")
    private KafkaTemplate<String, AccountingRequest> kafkaTemplate;

    public ResponseEntity sendAccountingDetails(String topicName, AccountingRequest accountingRequest) {

         kafkaTemplate.send(topicName,accountingRequest.getTxnSrno().toString(),accountingRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Message Sent Successfully");
    }
}
