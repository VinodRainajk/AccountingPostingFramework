package com.casaService.casaService.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class AccountCustomException extends RuntimeException{

    private HttpStatus statusCode;
    private final List<CustomAccountExceptionResponse> customAccountExceptionResponselist = new ArrayList<>();


    public AccountCustomException(HttpStatus statusCode)
    {
        this.statusCode = statusCode;
    }

    public AccountCustomException() {
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    public List<CustomAccountExceptionResponse> getcustomAccountExceptionResponselist() {
        return this.customAccountExceptionResponselist;
    }

    public void addAccountException(String errorCode, String message) {
        CustomAccountExceptionResponse customAccountExceptionResponse = new CustomAccountExceptionResponse(errorCode,message);
        this.customAccountExceptionResponselist.add(customAccountExceptionResponse);
    }
}
