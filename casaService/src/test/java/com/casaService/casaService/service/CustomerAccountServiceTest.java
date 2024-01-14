package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccount;
import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.model.CustomerAccountModel;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustomerAccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CustomerAccountServiceTest {

    @Mock
    ModelMapper maper;

    @Mock
    private CustomerAccountRepository customerAccountRepository;
    @Mock
    private BalanceRepository balanceRepository;

    @InjectMocks
    private CustomerAccountService customerAccountService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void ValidatecreateNewAccount()
    {
        CustomerAccountModel customerAccountModel = new CustomerAccountModel();
        customerAccountModel.setCustomerName("Vinod");
        customerAccountModel.setCustomerAccNo(1);


        CustomerAccount accountentity = new CustomerAccount();
        accountentity.setAccountStatus("OPEN");
        accountentity.setCustomerName("Vinod");
        accountentity.setCustomerAccNo(1);


        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomerName("Vinod");
        customerAccount.setAccountStatus("OPEN");

        CustomerAccountBalance accountBalance = new CustomerAccountBalance();
        accountBalance.setCustomerAccNo(customerAccountModel.getCustomerAccNo());
        accountBalance.setAccountBalance(0D);

        Mockito.when(maper.map(customerAccountModel, CustomerAccount.class)).thenReturn(customerAccount);
        Mockito.when(customerAccountRepository.save(any())).thenReturn(accountentity);
        Mockito.when(maper.map(accountentity,CustomerAccountModel.class)).thenReturn(customerAccountModel);


        Mockito.doNothing().when(balanceRepository).saveNewAccount(accountBalance);
        CustomerAccountModel response = customerAccountService.createNewAccount(customerAccountModel);

        Assertions.assertEquals(response.getCustomerName() , customerAccountModel.getCustomerName());


    }



}