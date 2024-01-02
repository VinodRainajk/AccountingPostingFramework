package com.messagePublisher.kafkaPublisherService.model;

public class AccountingRequest {
    private Integer txnSrno;
    private String txnRefNo;
    private String extTxnRefNo;
    private DebitCreditEnum drcr;
    private Double lcyamount;
    private Double exchRate;
    private Double acyAmount;
    private Integer custAccno;
    private String accy;

    public Integer getTxnSrno() {
        return txnSrno;
    }

    public void setTxnSrno(Integer txnSrno) {
        this.txnSrno = txnSrno;
    }

    public String getTxnRefNo() {
        return txnRefNo;
    }

    public void setTxnRefNo(String txnRefNo) {
        this.txnRefNo = txnRefNo;
    }

    public String getExtTxnRefNo() {
        return extTxnRefNo;
    }

    public void setExtTxnRefNo(String extTxnRefNo) {
        this.extTxnRefNo = extTxnRefNo;
    }

    public DebitCreditEnum getDrcr() {
        return drcr;
    }

    public void setDrcr(DebitCreditEnum drcr) {
        this.drcr = drcr;
    }

    public Double getLcyamount() {
        return lcyamount;
    }

    public void setLcyamount(Double lcyamount) {
        this.lcyamount = lcyamount;
    }

    public Double getExchRate() {
        return exchRate;
    }

    public void setExchRate(Double exchRate) {
        this.exchRate = exchRate;
    }

    public Double getAcyAmount() {
        return acyAmount;
    }

    public void setAcyAmount(Double acyAmount) {
        this.acyAmount = acyAmount;
    }

    public Integer getCustAccno() {
        return custAccno;
    }

    public void setCustAccno(Integer custAccno) {
        this.custAccno = custAccno;
    }

    public String getAccy() {
        return accy;
    }

    public void setAccy(String accy) {
        this.accy = accy;
    }

    public String getLccy() {
        return lccy;
    }

    public void setLccy(String lccy) {
        this.lccy = lccy;
    }

    private String lccy;

    @Override
    public String toString() {
        return "AccountingRequest{" +
                "txnSrno=" + txnSrno +
                ", txnRefNo='" + txnRefNo + '\'' +
                ", extTxnRefNo='" + extTxnRefNo + '\'' +
                ", drcr=" + drcr +
                ", lcyamount=" + lcyamount +
                ", exchRate=" + exchRate +
                ", acyAmount=" + acyAmount +
                ", custAccno=" + custAccno +
                ", accy='" + accy + '\'' +
                ", lccy='" + lccy + '\'' +
                '}';
    }
}
