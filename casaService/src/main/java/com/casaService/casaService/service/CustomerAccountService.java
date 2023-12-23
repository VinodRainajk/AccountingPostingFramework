package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccount;
import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.exception.AccountCustomException;
import com.casaService.casaService.exception.CustomAccountExceptionResponse;
import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.CustomerAccountModel;
import com.casaService.casaService.model.CustomerAccountResponse;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustomerAccountRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerAccountService {
    private CustomerAccountRepository customerAccountRepository;
    private BalanceRepository balanceRepository;
    private ModelMapper maper = new ModelMapper();

    private final AccountCustomException accountCustomException = new AccountCustomException();


    private ResponseEntity balanceUpdateResponse;
    @Autowired
    public CustomerAccountService(CustomerAccountRepository customerAccountRepository , BalanceRepository balanceRepository) {
        this.customerAccountRepository = customerAccountRepository;
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public CustomerAccountModel createNewAccount(CustomerAccountModel customerAccountModel)
    {
        System.out.println( "info Service "+ customerAccountModel.getCustomerName());
        customerAccountModel.setAccountStatus("OPEN");
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
        if(accountentity == null || accountentity.getCustomerAccNo() ==null)
        {
             accountCustomException.setStatusCode(HttpStatus.NOT_FOUND);
             accountCustomException.setCustomAccountExceptionResponse(new CustomAccountExceptionResponse("AF-ACC-001","Account number does not exist"));
            throw accountCustomException;
        }
        return maper.map(accountentity,CustomerAccountModel.class);
    }

    public BalanceResponse getAccountBalance(Integer accountNo)
    {
        return balanceRepository.getAccountBalance(accountNo);
    }

    public ResponseEntity closeCustomerAccount(Integer accountNo)
    {
         if(!balanceRepository.getAccountBalance(accountNo).getAccountBalance().equals(0D))
         {
             accountCustomException.setStatusCode(HttpStatus.EXPECTATION_FAILED);
             accountCustomException.setCustomAccountExceptionResponse(new CustomAccountExceptionResponse("AF-AC-003","Cannot Close Account Balance > 0"));
             throw accountCustomException;
         };
        CustomerAccount customerAccount = customerAccountRepository.getReferenceById(accountNo);
        customerAccount.setAccountStatus("CLOSE");
        customerAccountRepository.flush();
        return CustomerAccountResponse.generateResponse("Successfully CLosed Account", HttpStatus.OK,accountNo);

    }

}
