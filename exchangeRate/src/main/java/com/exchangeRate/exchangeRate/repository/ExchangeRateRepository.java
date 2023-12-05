package com.exchangeRate.exchangeRate.repository;

import com.exchangeRate.exchangeRate.dto.CurrencyExchangeRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExchangeRateRepository extends JpaRepository<CurrencyExchangeRate,Integer> {

    @Query(
            nativeQuery = true,
            value = "SELECT * FROM currency_exchange_rate WHERE currency1 = ?1 and currency2 = ?2"
    )
    Optional<CurrencyExchangeRate> getExchnageRate(String Currency1, String Currency2);
}
