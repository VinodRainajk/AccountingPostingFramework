package com.casaService.casaService.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static jakarta.persistence.GenerationType.SEQUENCE;


@Entity
public class CustomerAccount {

    @Id
    @Column
    @GeneratedValue(strategy = SEQUENCE)
    Integer customerAccNo;

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

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Column
    String customerName;

    @Column
    String photo;

    @Column
    String accountStatus;

}
