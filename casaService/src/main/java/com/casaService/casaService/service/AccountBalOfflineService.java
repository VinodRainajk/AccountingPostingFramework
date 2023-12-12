package com.casaService.casaService.service;

import com.casaService.casaService.repository.CustBalOfflineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountBalOfflineService {

    CustBalOfflineRepo custBalOfflineRepo;

    @Autowired
    public AccountBalOfflineService(CustBalOfflineRepo custBalOfflineRepo)
    {
        this.custBalOfflineRepo = custBalOfflineRepo;
    }



}
