package com.messagePublisher.kafkaPublisherService.model;

import java.util.List;

public class AccountBalanceDetails {

    Integer customerAccNo;
    String customerName;
    String accountStatus;
    double balance;
    List<BalanceUpdateRequest> balanceUpdateRequestList;

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

    public List<BalanceUpdateRequest> getBalanceUpdateRequestList() {
        return balanceUpdateRequestList;
    }

    public void setBalanceUpdateRequestList(List<BalanceUpdateRequest> balanceUpdateRequestList) {
        this.balanceUpdateRequestList = balanceUpdateRequestList;
    }
}
