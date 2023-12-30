package com.exchangeRate.exchangeRate.exceptions;

public class RateNotMaintained extends RuntimeException{

    String error_code;

    public String getErrorCode() {
        return error_code;
    }

    public void setErrorCode(String errorCode) {
        this.error_code = errorCode;
    }

    public RateNotMaintained(String message, String error_code) {
        super(message);
        this.error_code = error_code;
    }
}
