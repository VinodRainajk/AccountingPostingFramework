package com.casaService.casaService.feingClients;

import com.casaService.casaService.model.MessagePublisherRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("MESSAGE-PUBLISHER")
public interface MessagePublisherClient {

    @PostMapping("/balanceUpdate/{topicName}")
    public RequestEntity sendBalanceUpdate(@PathVariable String topicName, @RequestBody List<MessagePublisherRequest> messagePublisherRequest);


}
