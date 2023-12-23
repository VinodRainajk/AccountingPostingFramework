package com.accountingProcessor.accountingProcessor.exceptions;

public class CustomAccountExceptionResponse {
    String error_code;
    String message;

    public CustomAccountExceptionResponse(String error_code, String message) {
        this.error_code = error_code;
        this.message = message;
    }
    public CustomAccountExceptionResponse()
    {

    }

    @Override
    public String toString() {
        return "CustomAccountExceptionResponse{" +
                "error_code='" + error_code + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
