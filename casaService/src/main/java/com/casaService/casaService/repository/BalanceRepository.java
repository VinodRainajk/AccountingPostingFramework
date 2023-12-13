package com.casaService.casaService.repository;

import com.casaService.casaService.dto.CustomerAccountBalance;
import com.casaService.casaService.model.BalanceResponse;
import com.casaService.casaService.model.BalanceUpdateRequest;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public class BalanceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public BalanceUpdateRequest updateOnlineBalance(BalanceUpdateRequest balanceUpdateRequest)
    {
        CustomerAccountBalance customerAccountBalance = entityManager.find(CustomerAccountBalance.class, balanceUpdateRequest.getCustomerAccNo(), LockModeType.PESSIMISTIC_WRITE);
        if(getAccountBalance(balanceUpdateRequest.getCustomerAccNo()).getAccountBalance() - balanceUpdateRequest.getAmount() >0)
        {
            customerAccountBalance.setAccountBalance(customerAccountBalance.getAccountBalance()-balanceUpdateRequest.getAmount());
            entityManager.persist(customerAccountBalance);

        }
        entityManager.flush();
        entityManager.lock(customerAccountBalance, LockModeType.NONE);
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
