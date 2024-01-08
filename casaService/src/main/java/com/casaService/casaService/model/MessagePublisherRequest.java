package com.casaService.casaService.model;

import java.util.List;

public class MessagePublisherRequest {

    Integer customerAccNo;
    String customerName;
    String accountStatus;
    double balance;
    BalanceUpdateRequest balanceUpdateRequest;

    public Integer getCustomerAccNo() {
        return customerAccNo;
    }

    public void setCustomerAccNo(Integer customerAccNo) {
        this.customerAccNo = customerAccNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public BalanceUpdateRequest getBalanceUpdateRequest() {
        return balanceUpdateRequest;
    }

    public void setBalanceUpdateRequest(BalanceUpdateRequest balanceUpdateRequest) {
        this.balanceUpdateRequest = balanceUpdateRequest;
    }
}




