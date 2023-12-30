package com.accountingProcessor.accountingProcessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountingModel {
    private Integer txnSrno;
    private String txnRefNo;
    private String extTxnRefNo;
    private DebitCreditEnum drcr;
    private Double lcyamount;
    private Double exchRate;
    private Double acyAmount;
    private Integer custAccno;
    private String accy;
    private String lccy;


}
