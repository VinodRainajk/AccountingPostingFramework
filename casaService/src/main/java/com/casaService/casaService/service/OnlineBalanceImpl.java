package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.exception.AccountCustomException;
import com.casaService.casaService.exception.CustomAccountExceptionResponse;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.MessagePublisherRequest;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustomerAccountRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class OnlineBalanceImpl implements Balance{

    private BalanceRepository balanceRepository;
    private final AccountCustomException accountCustomException = new AccountCustomException();
    private final CustomerAccountRepository customerAccountRepository;

    private static final Logger lOGGER = LogManager.getLogger(OnlineBalanceImpl.class);

   @Autowired
    public OnlineBalanceImpl(BalanceRepository balanceRepository,
                             CustomerAccountRepository customerAccountRepository) {
        this.balanceRepository = balanceRepository;
        this.customerAccountRepository =  customerAccountRepository;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void updateBalance(BalanceUpdateRequest balanceUpdateRequest) {

        CustomerAccountBalance customerAccountBalance = entityManager.find(CustomerAccountBalance.class, balanceUpdateRequest.getCustomerAccNo(), LockModeType.PESSIMISTIC_WRITE);
        lOGGER.info("Account Balance is "+balanceRepository.getAccountBalance(balanceUpdateRequest.getCustomerAccNo()).getAccountBalance());
        if(balanceRepository.getAccountBalance(balanceUpdateRequest.getCustomerAccNo()).getAccountBalance() - balanceUpdateRequest.getAmount() >= 0)
        {
            customerAccountBalance.setAccountBalance(customerAccountBalance.getAccountBalance()-balanceUpdateRequest.getAmount());
            entityManager.persist(customerAccountBalance);
        }else
        {
            accountCustomException.setStatusCode(HttpStatus.EXPECTATION_FAILED);
            accountCustomException.setCustomAccountExceptionResponse(new CustomAccountExceptionResponse("AF-ACC-03","Insufficient Account Balance in Account"+balanceUpdateRequest.getCustomerAccNo()));
            throw accountCustomException;
        }

    }


}
