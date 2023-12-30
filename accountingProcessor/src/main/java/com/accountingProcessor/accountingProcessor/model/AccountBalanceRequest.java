package com.accountingProcessor.accountingProcessor.model;

public class AccountBalanceRequest {
    String txnRefno;
    Integer customerAccNo;
    DebitCreditEnum drcr;
    Double amount;

    public AccountBalanceRequest(String txnRefno,
                                 Integer customerAccNo,
                                 DebitCreditEnum drcr,
                                 Double amount)
    {
        this.txnRefno = txnRefno;
        this.customerAccNo = customerAccNo;
        this.drcr = drcr;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "AccountBalanceRequest{" +
                "txnRefno='" + txnRefno + '\'' +
                ", customerAccNo=" + customerAccNo +
                ", drcr=" + drcr +
                ", amount=" + amount +
                '}';
    }

    public String getTxnRefno() {
        return txnRefno;
    }

    public void setTxnRefno(String txnRefno) {
        this.txnRefno = txnRefno;
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
