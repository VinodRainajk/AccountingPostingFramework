package com.accountingProcessor.accountingProcessor.services;

import com.accountingProcessor.accountingProcessor.feingclients.CasaServiceClient;
import com.accountingProcessor.accountingProcessor.model.AccountBalanceRequest;
import com.accountingProcessor.accountingProcessor.model.AccountingModel;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Component
public class AccountBalances {

    private CasaServiceClient casaServiceClient;

    private static final Logger LOGGER = LogManager.getLogger(AccountBalances.class);

    @Autowired
    public AccountBalances(CasaServiceClient casaServiceClient) {
        this.casaServiceClient = casaServiceClient;
    }

    public boolean updateCustomerBalance(List<AccountBalanceRequest> accountBalanceRequestList)
    {
        ResponseEntity<Map<String,Object>> balanceUpdateResponse = casaServiceClient.updatemultiCustomerbalance(accountBalanceRequestList);
        List<AccountBalanceRequest> responseforRequest = (List<AccountBalanceRequest>)balanceUpdateResponse.getBody().get("data");
        LOGGER.info("responseforRequest "+ responseforRequest);
        if(balanceUpdateResponse.getStatusCode().is2xxSuccessful())
            {
                return true;
            }


        return false;
    }
}
