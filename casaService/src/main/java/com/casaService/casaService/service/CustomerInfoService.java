package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccount;
import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.CustomerAccountModel;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustomerAccountRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerInfoService {
    private CustomerAccountRepository customerAccountRepository;
    private BalanceRepository balanceRepository;
    private ModelMapper maper = new ModelMapper();

    @Autowired
    public CustomerInfoService(CustomerAccountRepository customerAccountRepository , BalanceRepository balanceRepository) {
        this.customerAccountRepository = customerAccountRepository;
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public CustomerAccountModel createNewAccount(CustomerAccountModel customerAccountModel)
    {
        System.out.println( "info Service "+ customerAccountModel.getCustomerName());
        CustomerAccount customerAccount =  maper.map(customerAccountModel, CustomerAccount.class);
        CustomerAccount accountentity = customerAccountRepository.save(customerAccount);
        CustomerAccountModel accountmodel = maper.map(accountentity,CustomerAccountModel.class);

        if(accountmodel.getCustomerAccNo()!=null)
        {
            CustomerAccountBalance accountBalance = new CustomerAccountBalance();
            accountBalance.setCustomerAccNo(accountmodel.getCustomerAccNo());
            accountBalance.setAccountBalance(0D);
            balanceRepository.saveNewAccount(accountBalance);
        }

        return accountmodel;
    }


    public CustomerAccountModel getAccountDetails(Integer accountNo)
    {
        CustomerAccount accountentity = customerAccountRepository.getReferenceById(accountNo);
        return maper.map(accountentity,CustomerAccountModel.class);
    }

    public BalanceResponse getAccountBalance(Integer accountNo)
    {
        return balanceRepository.getAccountBalance(accountNo);
    }

}
