package com.casaService.casaService.service;

import com.casaService.casaService.exception.AccountCustomException;
import com.casaService.casaService.exception.CustomAccountExceptionResponse;
import com.casaService.casaService.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerWrapperService {
    private final CustomerAccountService customerAccountService;
    private final AccountBalanceService accountBalanceService;

    private final AccountCustomException accountCustomException= new AccountCustomException();
    @Autowired
    public CustomerWrapperService(CustomerAccountService customerAccountService, AccountBalanceService accountBalanceService) {
        this.customerAccountService = customerAccountService;
        this.accountBalanceService = accountBalanceService;
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
            accountCustomException.addAccountException("AF-Cust-04","The AccountNumber is Closed "+accountNo);
            throw accountCustomException;
        }

        return customerAccountService.getAccountBalance(accountNo);
    }

    public ResponseEntity updateCustomerBalance(BalanceUpdateRequest balanceUpdateRequest)
    {
        CustomerAccountModel customerAccountModel = customerAccountService.getAccountDetails(balanceUpdateRequest.getCustomerAccNo());

        if(customerAccountModel.getAccountStatus().equals("CLOSE"))
        {
            accountCustomException.setStatusCode(HttpStatus.EXPECTATION_FAILED);
            accountCustomException.addAccountException("AF-Cust-05","The AccountNumber is Closed "+customerAccountModel.getCustomerAccNo());
            throw accountCustomException;
        }

       if(balanceUpdateRequest.getDrcr().equals(DebitCreditEnum.valueOf("DEBIT"))
               || ( balanceUpdateRequest.getDrcr().equals(DebitCreditEnum.valueOf("CREDIT"))
                    && balanceUpdateRequest.getAmount()<0
                  )
         )
       {
         return   accountBalanceService.updateOnlineBalance(balanceUpdateRequest);
       }else
       {
         return accountBalanceService.updateOfflineBalance(balanceUpdateRequest);
       }
    }

    public ResponseEntity closeCustomerAccount(Integer CustomerAccount)
    {
        return customerAccountService.closeCustomerAccount(CustomerAccount);
    }
}
