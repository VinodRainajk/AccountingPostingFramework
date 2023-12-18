package com.accountingProcessor.accountingProcessor.dto;

public class AccountBalanceUpdateRequest {
    String txnRefno;
    Integer customerAccNo;
    DebitCreditEnum drcr;
    Double amount;

    public String getTxnRefno() {
        return txnRefno;
    }

    public void setTxnRefno(String txnRefno) {
        this.txnRefno = txnRefno;
    }

    public AccountBalanceUpdateRequest() {

    }

    public AccountBalanceUpdateRequest(String txnRefno, Integer customerAccNo, DebitCreditEnum drcr, Double amount) {
        this.txnRefno = txnRefno;
        this.customerAccNo = customerAccNo;
        this.drcr = drcr;
        this.amount = amount;
    }

    public Integer getCustomerAccNo() {
        return customerAccNo;
    }

    public void setCustomerAccNo(Integer customerAccNo) {
        this.customerAccNo = customerAccNo;
    }

    public DebitCreditEnum getDrcr() {
        return drcr;
    }

    public void setDrcr(DebitCreditEnum drcr) {
        this.drcr = drcr;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}
