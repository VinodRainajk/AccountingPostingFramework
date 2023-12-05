package com.exchangeRate.exchangeRate.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CurrencyExchangeRate {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    @Column
    Integer id;
    @Column
    String currency1;
    @Column
    String currency2;
    @Column
    double exchnageRate;


}
