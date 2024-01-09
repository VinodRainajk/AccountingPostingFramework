package com.accountingProcessor.accountingProcessor.feingclients;

import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("MESSAGE-PUBLISHER")
public interface MessagePublisherClient {

    @PostMapping("/accounting/{topicName}")
    public ResponseEntity sendAccountingDetails(@PathVariable String topicName, @RequestBody AccountingModel messagePublisherModel);


}
