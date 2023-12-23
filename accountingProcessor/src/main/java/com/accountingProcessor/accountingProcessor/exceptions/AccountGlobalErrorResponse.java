package com.accountingProcessor.accountingProcessor.exceptions;


import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AccountGlobalErrorResponse {
    private int statusCode;
    private String message;

    public AccountGlobalErrorResponse(int value, String message) {
        this.statusCode = value;
        this.message = message;
    }
}
