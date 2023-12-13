package com.casaService.casaService.dto;

import com.casaService.casaService.model.DebitCreditEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerAccountOfflineBalance {

    @Id
    @Column
    @GeneratedValue(strategy = SEQUENCE)
    Integer id;

    @Column
    Integer customerAccNo;

    @Column
    Double amount;

    @Column
    @Enumerated(EnumType.STRING)
    DebitCreditEnum drCr;

    @Column
    String status;

    @Column
    String TxnRefNo;
}
