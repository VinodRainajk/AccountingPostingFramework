package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccount;
import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.CustomerAccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerWrapperService {
    private CustomerInfoService customerInfoService;

    @Autowired
    public CustomerWrapperService(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
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
}
