package com.casaService.casaService.repository;

import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.exception.AccountCustomException;
import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.CustomerAccountResponse;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Repository
public class BalanceRepository {
    @PersistenceContext
    private EntityManager entityManager;
    private final AccountCustomException accountCustomException = new AccountCustomException();


    public List<BalanceUpdateRequest> updateOnlineBalance(List<BalanceUpdateRequest> balanceUpdateRequest)
    {

      for(int idx= 0; idx< balanceUpdateRequest.size(); idx++)
       {
         CustomerAccountBalance customerAccountBalance = entityManager.find(CustomerAccountBalance.class, balanceUpdateRequest.get(idx).getCustomerAccNo(), LockModeType.PESSIMISTIC_WRITE);


         System.out.println("Account Balance is "+getAccountBalance(balanceUpdateRequest.get(idx).getCustomerAccNo()).getAccountBalance());
         if(getAccountBalance(balanceUpdateRequest.get(idx).getCustomerAccNo()).getAccountBalance() - balanceUpdateRequest.get(idx).getAmount() >= 0)
         {
             customerAccountBalance.setAccountBalance(customerAccountBalance.getAccountBalance()-balanceUpdateRequest.get(idx).getAmount());
             entityManager.persist(customerAccountBalance);
         }else
         {
             accountCustomException.setStatusCode(HttpStatus.EXPECTATION_FAILED);
             accountCustomException.addAccountException("AF-ACC-03","Insufficient Account Balance in Account"+balanceUpdateRequest.get(idx).getCustomerAccNo());
             throw accountCustomException;
         }
       }
        entityManager.flush();
       return balanceUpdateRequest;
    }

    public void saveNewAccount(CustomerAccountBalance customerAccountBalance)
    {
        entityManager.persist(customerAccountBalance);
        entityManager.flush();
    }

    public BalanceResponse getAccountBalance(Integer CustomerACNo)
    {
        String balanceQuery = "Select Account_no, accountBalance " +
                               "from Current_Account_Balance " +
                               "where Account_no = :accno ";
        Query returnobject =  entityManager.createNativeQuery(balanceQuery)
                                                        .setParameter("accno",CustomerACNo)
                                                        ;
        Object[] retunedvalues = (Object[]) returnobject.getSingleResult();
        BalanceResponse balanceResponse = new BalanceResponse();
        balanceResponse.setCustomerAccNo(CustomerACNo);
        balanceResponse.setAccountBalance(((BigDecimal)retunedvalues[1]).doubleValue());
        return balanceResponse;
    }

}
