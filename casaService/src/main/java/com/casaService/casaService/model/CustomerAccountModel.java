package com.casaService.casaService.model;


public class CustomerAccountModel {

    Integer customerAccNo;

    String customerName;
    String photo;
    String accountStatus;
    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }



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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    public boolean isValidForTransaction()
    {
        if(this.getAccountStatus().equals("CLOSE"))
        {
            return false;
        }
        return true;
    }
}
