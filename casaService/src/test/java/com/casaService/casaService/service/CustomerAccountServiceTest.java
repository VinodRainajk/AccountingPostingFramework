package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccount;
import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.model.BalanceResponse;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

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

    CustomerAccount accountentity = new CustomerAccount();
    CustomerAccountModel customerAccountModel = new CustomerAccountModel();
    BalanceResponse balanceResponse = new BalanceResponse();




    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        accountentity.setAccountStatus("OPEN");
        accountentity.setCustomerName("Vinod");
        accountentity.setCustomerAccNo(1);


        customerAccountModel.setCustomerName("Vinod");
        customerAccountModel.setCustomerAccNo(1);

        balanceResponse.setAccountBalance(0D);
        balanceResponse.setCustomerAccNo(1);

    }

    @Test
    public void ValidatecreateNewAccount()
    {



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

    @Test
    public void ValidategetAccountDetails()
    {
        Integer inputAccountNumber = 1;
        Mockito.when(customerAccountRepository.getReferenceById(inputAccountNumber)).thenReturn(accountentity);
        Mockito.when(maper.map(accountentity,CustomerAccountModel.class)).thenReturn(customerAccountModel);
        CustomerAccountModel response = customerAccountService.getAccountDetails(inputAccountNumber);

        Assertions.assertEquals(customerAccountModel.getCustomerAccNo(),1);
    }


    @Test
    public void TesttheAccountClosure()
    {
        Integer accountNo =1;
        Mockito.when(balanceRepository.getAccountBalance(accountNo)).thenReturn(balanceResponse);
        Mockito.when(customerAccountRepository.getReferenceById(accountNo)).thenReturn(accountentity);
        ResponseEntity response =  customerAccountService.closeCustomerAccount(accountNo);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.OK);


    }


}