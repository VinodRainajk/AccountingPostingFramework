package com.casaService.casaService.repository;

import com.casaService.casaService.dto.CustomerAccountOfflineBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustBalOfflineRepo extends JpaRepository<CustomerAccountOfflineBalance,Integer> {
}
