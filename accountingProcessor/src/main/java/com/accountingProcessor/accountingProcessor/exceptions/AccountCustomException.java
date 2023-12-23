package com.accountingProcessor.accountingProcessor.exceptions;

import org.springframework.http.HttpStatus;

public class AccountCustomException extends RuntimeException{

    private HttpStatus statusCode;
    private CustomAccountExceptionResponse customAccountExceptionRespone;


    public CustomAccountExceptionResponse getCustomAccountExceptionResponse() {
        return customAccountExceptionRespone;
    }

    public void setCustomAccountExceptionResponse(CustomAccountExceptionResponse customAccountExceptionRespone) {
        this.customAccountExceptionRespone = customAccountExceptionRespone;
    }

    public AccountCustomException() {
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
