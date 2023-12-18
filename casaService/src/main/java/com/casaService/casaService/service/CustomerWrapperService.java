package com.casaService.casaService.service;

import com.casaService.casaService.exception.AccountCustomException;
import com.casaService.casaService.exception.CustomAccountExceptionResponse;
import com.casaService.casaService.model.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerWrapperService {
    private final CustomerAccountService customerAccountService;
    private final AccountBalanceService accountBalanceService;

    private CustomerAccountResponse customerAccountResponse = new CustomerAccountResponse();

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
/*
    public ResponseEntity updateCustomerBalance(List<BalanceUpdateRequest> balanceUpdateRequest)
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
*/
    public ResponseEntity closeCustomerAccount(Integer CustomerAccount)
    {
        return customerAccountService.closeCustomerAccount(CustomerAccount);
    }

    @Transactional
    public ResponseEntity multiBalanceUpdate(List<BalanceUpdateRequest> balanceUpdList)
    {
        // Validate the Transaction
        // Split Send Credit First
        // then Debit
        // Perform Final Check if Success full
        List<BalanceUpdateRequest> debitBalanceUpdate = new ArrayList<>();

        for(int idx =0 ; idx<balanceUpdList.size(); idx++ )
        {
            CustomerAccountModel customerAccountModel = customerAccountService.getAccountDetails(balanceUpdList.get(idx).getCustomerAccNo());
            if(!customerAccountModel.isValidForTransaction())
            {
                accountCustomException.setStatusCode(HttpStatus.EXPECTATION_FAILED);
                accountCustomException.addAccountException("AF-Cust-05","The AccountNumber is Closed "+customerAccountModel.getCustomerAccNo());
                throw accountCustomException;
            } else
            {
                if(balanceUpdList.get(idx).getDrcr().equals(DebitCreditEnum.valueOf("CREDIT")))
                {
                     accountBalanceService.updateOfflineBalance(balanceUpdList.get(idx));
                } else
                {
                    debitBalanceUpdate.add(balanceUpdList.get(idx));
                }
            }
        }

        if(debitBalanceUpdate.size()>0)
        {
            accountBalanceService.updateOnlineBalance(debitBalanceUpdate);
        }

        return CustomerAccountResponse
                .generateResponse("Successfully Processed Transactions"
                                 ,HttpStatus.OK
                                ,balanceUpdList);
    }



}
