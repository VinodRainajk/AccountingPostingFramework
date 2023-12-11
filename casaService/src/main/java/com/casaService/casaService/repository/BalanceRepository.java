package com.casaService.casaService.repository;

import com.casaService.casaService.dto.CustomerAccountBalance;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class BalanceRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public void getLockOnAccount(Integer CustomerACNo)
    {
        CustomerAccountBalance customerAccountBalance = entityManager.find(CustomerAccountBalance.class, CustomerACNo, LockModeType.PESSIMISTIC_WRITE);
        customerAccountBalance.setAccountBalance(customerAccountBalance.getAccountBalance()+100);
        entityManager.persist(customerAccountBalance);
        entityManager.flush();
        entityManager.lock(customerAccountBalance, LockModeType.NONE);
    }

}
