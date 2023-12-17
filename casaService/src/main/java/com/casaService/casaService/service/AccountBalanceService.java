package com.casaService.casaService.service;

import com.casaService.casaService.dto.CustomerAccountOfflineBalance;
import com.casaService.casaService.model.BalanceUpdateRequest;
import com.casaService.casaService.model.CustomerAccountResponse;
import com.casaService.casaService.repository.BalanceRepository;
import com.casaService.casaService.repository.CustBalOfflineRepo;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountBalanceService {


    private final BalanceRepository balanceRepository;
    private final CustBalOfflineRepo custBalOfflineRepo;
    private final ModelMapper maper = new ModelMapper();

    @Autowired
    public AccountBalanceService(BalanceRepository balanceRepository, CustBalOfflineRepo custBalOfflineRepo) {
      //  this.customerBalanceRepository = customerBalanceRepository;
        this.balanceRepository = balanceRepository;
        this.custBalOfflineRepo = custBalOfflineRepo;
    }

    @Transactional
    public ResponseEntity updateOnlineBalance(BalanceUpdateRequest balanceUpdateRequest)
    {
        System.out.println("Inside  updateOnlineBalance");
       return balanceRepository.updateOnlineBalance(balanceUpdateRequest);
    }

    @Transactional
    public ResponseEntity updateOfflineBalance(BalanceUpdateRequest balanceUpdateRequest)
    {
        System.out.println("Inside  updateOfflineBalance");
        CustomerAccountOfflineBalance customerAccountOfflineBalance = maper.map(balanceUpdateRequest, CustomerAccountOfflineBalance.class);
        customerAccountOfflineBalance.setStatus("U");
        CustomerAccountOfflineBalance returnedval=  custBalOfflineRepo.save(customerAccountOfflineBalance);
        return   CustomerAccountResponse.generateResponse("Successfully Debited the account", HttpStatus.OK,maper.map(returnedval,BalanceUpdateRequest.class) );


    }
}
