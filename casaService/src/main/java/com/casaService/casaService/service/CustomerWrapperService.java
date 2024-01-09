package com.casaService.casaService.service;

import com.casaService.casaService.exception.AccountCustomException;
import com.casaService.casaService.exception.CustomAccountExceptionResponse;
import com.casaService.casaService.feingClients.MessagePublisherClient;
import com.casaService.casaService.model.*;
import com.casaService.casaService.repository.CustomerAccountRepository;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerWrapperService {
    private static final Logger lOGGER = LogManager.getLogger(CustomerWrapperService.class);
    private final CustomerAccountService customerAccountService;
    private final AccountCustomException accountCustomException= new AccountCustomException();
    private OnlineBalanceImpl onlineBalanceimpl;
    private OfflineBalanceImpl offlineBalanceimpl;
    private MessagePublisherClient messagePublisherClient;

    @Autowired
    public CustomerWrapperService(CustomerAccountService customerAccountService,
                                    OnlineBalanceImpl onlineBalance
                                  , OfflineBalanceImpl offlineBalance
                                  , MessagePublisherClient messagePublisherClient
    ) {
        this.customerAccountService = customerAccountService;
        this.onlineBalanceimpl = onlineBalance;
        this.offlineBalanceimpl = offlineBalance;
        this.messagePublisherClient = messagePublisherClient;
    }

    public CustomerAccountModel createNewAccount(CustomerAccountModel customerAccountModel)
    {
        System.out.println( "wrapper "+ customerAccountModel.getCustomerName());
        return customerAccountService.createNewAccount(customerAccountModel);
    }

    public CustomerAccountModel getAccountDetails(Integer accountNo)
    {
        return customerAccountService.getAccountDetails(accountNo);
    }

    public BalanceResponse getAccountBalance(Integer accountNo)
    {
        CustomerAccountModel customerAccountModel = getAccountDetails(accountNo);
        if(customerAccountModel.getAccountStatus().equals("CLOSE"))
        {
            accountCustomException.setStatusCode(HttpStatus.EXPECTATION_FAILED);
            accountCustomException.setCustomAccountExceptionResponse( new CustomAccountExceptionResponse("AF-Cust-04","The AccountNumber is Closed "+accountNo));
            throw accountCustomException;
        }

        return customerAccountService.getAccountBalance(accountNo);
    }

    public ResponseEntity closeCustomerAccount(Integer CustomerAccount)
    {
        return customerAccountService.closeCustomerAccount(CustomerAccount);
    }

    @Transactional
    public ResponseEntity multiBalanceUpdate(List<BalanceUpdateRequest> balanceUpdList)
    {
       List<MessagePublisherRequest> messagePublisherRequestList = new ArrayList<>();
       for(int idx = 0 ; idx < balanceUpdList.size(); idx++)
       {
           Balance balance;

            if(verifyTxnType(balanceUpdList.get(idx)).equals(DebitCreditEnum.valueOf("DEBIT")))
            {
                balance = onlineBalanceimpl;
            }else
            {
                balance = offlineBalanceimpl;
            }
          balance.updateBalance(balanceUpdList.get(idx));
          MessagePublisherRequest messagePublisherRequest =  messageGenerator(balanceUpdList.get(idx));
          messagePublisherRequestList.add(messagePublisherRequest);
       }

        lOGGER.info("The details updated and ready to send to MessagePublisher "+messagePublisherRequestList);

        ResponseEntity response = messagePublisherClient.sendBalanceUpdate("BalanceUpdate",messagePublisherRequestList);

        lOGGER.info("The details sent to MessagePublisher successfully "+response.getStatusCode());

         CustomerAccountResponse customerAccountResponse = new CustomerAccountResponse();
        return customerAccountResponse.generateResponse("Successfully Processed Transactions"
                ,HttpStatus.OK
                ,balanceUpdList);
    }


    public DebitCreditEnum verifyTxnType(BalanceUpdateRequest balanceUpdateRequest)
    {
        if
        ( (balanceUpdateRequest.getDrcr().equals(DebitCreditEnum.valueOf("DEBIT"))
           && balanceUpdateRequest.getAmount() >0)
           ||(balanceUpdateRequest.getDrcr().equals(DebitCreditEnum.valueOf("CREDIT"))
              && balanceUpdateRequest.getAmount() < 0)
        )
        {
          return DebitCreditEnum.DEBIT;
        }else
        {
          return  DebitCreditEnum.CREDIT;
        }

    }


    public MessagePublisherRequest messageGenerator(BalanceUpdateRequest balanceUpdateRequest)
    {
        MessagePublisherRequest messagePublisherRequest = new MessagePublisherRequest();
        messagePublisherRequest.setBalanceUpdateRequest(balanceUpdateRequest);
        messagePublisherRequest.setCustomerAccNo(balanceUpdateRequest.getCustomerAccNo());

        messagePublisherRequest.setBalance(customerAccountService.getAccountBalance(balanceUpdateRequest.getCustomerAccNo()).getAccountBalance());
        messagePublisherRequest.setCustomerName(customerAccountService.getAccountDetails(balanceUpdateRequest.getCustomerAccNo()).getCustomerName());
        messagePublisherRequest.setAccountStatus(customerAccountService.getAccountDetails(balanceUpdateRequest.getCustomerAccNo()).getAccountStatus());
        return messagePublisherRequest;
    }
}



