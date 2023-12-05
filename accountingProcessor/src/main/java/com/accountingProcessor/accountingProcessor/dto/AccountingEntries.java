package com.accountingProcessor.accountingProcessor.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.boot.model.relational.Sequence;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountingEntries {
    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    @Column
    private Integer txnSrno;
    @Column
    private String txnRefNo;
    @Column
    private String extTxnRefNo;
    @Column
    private String drcr;
    @Column
    private Double lcyamount;
    @Column
    private Double exchRate;
    @Column
    private Double fcyAmount;
    @Column
    private String fccy;
    @Column
    private String lccy;


}