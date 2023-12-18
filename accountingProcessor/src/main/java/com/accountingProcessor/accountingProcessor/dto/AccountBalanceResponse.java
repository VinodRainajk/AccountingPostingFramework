package com.accountingProcessor.accountingProcessor.dto;

public class AccountBalanceResponse {
    Integer customerAccNo;
    Double  accountBalance;

    public Integer getCustomerAccNo() {
        return customerAccNo;
    }

    public void setCustomerAccNo(Integer customerAccNo) {
        this.customerAccNo = customerAccNo;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }
}
