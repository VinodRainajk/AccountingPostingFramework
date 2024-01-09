package com.casaService.casaService.model;

public class BalanceUpdateRequest {

    String txnRefno;
    Integer customerAccNo;
    DebitCreditEnum drcr;
    Double amount;

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    @Override
    public String toString() {
        return "BalanceUpdateRequest{" +
                "txnRefno='" + txnRefno + '\'' +
                ", customerAccNo=" + customerAccNo +
                ", drcr=" + drcr +
                ", amount=" + amount +
                '}';
    }
}
