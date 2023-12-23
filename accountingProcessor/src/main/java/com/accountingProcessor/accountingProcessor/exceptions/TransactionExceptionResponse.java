package com.accountingProcessor.accountingProcessor.exceptions;

public class TransactionExceptionResponse {

    String error_code;

    public String getError_code() {
        return error_code;
    }

    public TransactionExceptionResponse(String error_code, String message) {
        this.error_code = error_code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "TransactionExceptionResponse{" +
                "error_code='" + error_code + '\'' +
                ", message='" + message + '\'' +
                '}';
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

    String message;
}
