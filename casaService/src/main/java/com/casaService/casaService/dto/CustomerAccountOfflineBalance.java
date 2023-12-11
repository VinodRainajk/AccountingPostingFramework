package com.casaService.casaService.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerAccountOfflineBalance {

    @Id
    @Column
    Integer customerAccNo;

    @Column
    Double amount;

    @Column
    String drCr;

    @Column
    String status;

    @Column
    String TxnRefNo;
}
