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


    public static class BalanceUpdateRequest {
        String txnRefno;
        Integer customerAccNo;
        DebitCreditEnum drcr;
        Double amount;

        @Override
        public String toString() {
            return "BalanceUpdateRequest{" +
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

        public BalanceUpdateRequest() {

        }

        public BalanceUpdateRequest(String txnRefno, Integer customerAccNo, DebitCreditEnum drcr, Double amount) {
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
}
