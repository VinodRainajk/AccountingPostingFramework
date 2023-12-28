package com.accountingProcessor.accountingProcessor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private Double acyAmount;
    @Column
    private String accy;
    @Column
    private String lccy;

    @Column
    private Integer custAccno;


}