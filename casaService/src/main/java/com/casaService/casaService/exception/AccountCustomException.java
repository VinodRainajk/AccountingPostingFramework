package com.casaService.casaService.exception;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

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




}
