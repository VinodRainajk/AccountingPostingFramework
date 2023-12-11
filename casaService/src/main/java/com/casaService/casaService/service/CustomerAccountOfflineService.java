package com.casaService.casaService.service;

import com.casaService.casaService.repository.CustBalOfflineRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerAccountOfflineService {

    CustBalOfflineRepo custBalOfflineRepo;

    @Autowired
    public CustomerAccountOfflineService(CustBalOfflineRepo custBalOfflineRepo)
    {
        this.custBalOfflineRepo = custBalOfflineRepo;
    }

    public saveOffLineBalance()



}
