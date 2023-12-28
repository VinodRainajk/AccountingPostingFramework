package com.accountingProcessor.accountingProcessor.exceptions;

import org.springframework.http.HttpStatus;

public class AccountingCustomException extends RuntimeException{

    private HttpStatus statusCode;
    private AccountingExceptionDetails customAccountExceptionRespone;


    public AccountingExceptionDetails getCustomAccountExceptionResponse() {
        return customAccountExceptionRespone;
    }

    public void setCustomAccountExceptionResponse(AccountingExceptionDetails customAccountExceptionRespone) {
        this.customAccountExceptionRespone = customAccountExceptionRespone;
    }

    public AccountingCustomException() {
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }


    @Override
    public String toString() {
        return "AccountCustomException{" +
                "statusCode=" + statusCode +
                ", customAccountExceptionRespone=" + customAccountExceptionRespone +
                '}';
    }
}
