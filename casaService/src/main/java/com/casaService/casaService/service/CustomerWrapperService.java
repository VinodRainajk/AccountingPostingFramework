package com.casaService.casaService.service;

import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.CustomerAccountModel;
import com.casaService.casaService.model.DebitCreditEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerWrapperService {
    private final CustomerInfoService customerInfoService;
    private final AccountBalanceService accountBalanceService;


    @Autowired
    public CustomerWrapperService(CustomerInfoService customerInfoService,AccountBalanceService accountBalanceService) {
        this.customerInfoService = customerInfoService;
        this.accountBalanceService = accountBalanceService;
    }

    public CustomerAccountModel createNewAccount(CustomerAccountModel customerAccountModel)
    {
        System.out.println( "wrapper "+ customerAccountModel.getCustomerName());
        return customerInfoService.createNewAccount(customerAccountModel);
    }

    public CustomerAccountModel getAccountDetails(Integer accountNo)
    {
        return customerInfoService.getAccountDetails(accountNo);
    }

    public BalanceResponse getAccountBalance(Integer accountNo)
    {
        return customerInfoService.getAccountBalance(accountNo);
    }

    public BalanceUpdateRequest updateCustomerBalance(BalanceUpdateRequest balanceUpdateRequest)
    {
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
}
