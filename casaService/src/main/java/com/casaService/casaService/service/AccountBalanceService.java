package com.casaService.casaService.service;

import com.casaService.casaService.controller.CustomerAccountController;
import com.casaService.casaService.dto.CustomerAccountOfflineBalance;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.CustomerAccountResponse;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustBalOfflineRepo;
import jakarta.transaction.Transactional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountBalanceService {
    private static final Logger lOGGER = LogManager.getLogger(AccountBalanceService.class);


    private final BalanceRepository balanceRepository;
    private final CustBalOfflineRepo custBalOfflineRepo;
    private final ModelMapper maper = new ModelMapper();

    @Autowired
    public AccountBalanceService(BalanceRepository balanceRepository, CustBalOfflineRepo custBalOfflineRepo) {
      //  this.customerBalanceRepository = customerBalanceRepository;
        this.balanceRepository = balanceRepository;
        this.custBalOfflineRepo = custBalOfflineRepo;
    }


    public List<BalanceUpdateRequest> updateOnlineBalance(List<BalanceUpdateRequest> balanceUpdateRequestlist)
    {
       lOGGER.info("Inside  updateOnlineBalance");
       return balanceRepository.updateOnlineBalance(balanceUpdateRequestlist);
    }


    public BalanceUpdateRequest updateOfflineBalance(BalanceUpdateRequest balanceUpdateRequest)
    {
        lOGGER.info("Inside  updateOfflineBalance");
        CustomerAccountOfflineBalance customerAccountOfflineBalance = maper.map(balanceUpdateRequest, CustomerAccountOfflineBalance.class);
        customerAccountOfflineBalance.setStatus("U");
        CustomerAccountOfflineBalance returnedval=  custBalOfflineRepo.save(customerAccountOfflineBalance);
        return  balanceUpdateRequest;
    }
}
