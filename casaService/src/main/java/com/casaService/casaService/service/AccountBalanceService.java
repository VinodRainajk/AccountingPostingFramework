package com.casaService.casaService.service;

import com.casaService.casaService.repository.BalanceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBalanceService {


    BalanceRepository balanceRepository;
    @Autowired
    public AccountBalanceService(BalanceRepository balanceRepository) {
      //  this.customerBalanceRepository = customerBalanceRepository;
        this.balanceRepository = balanceRepository;
    }

    @Transactional
    public void getLockonAccount(Integer CustomerAccountNO)
    {
        System.out.println("Locks Achieved");
        balanceRepository.getLockOnAccount(CustomerAccountNO);
    }
}
