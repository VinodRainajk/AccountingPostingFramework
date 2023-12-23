package com.accountingProcessor.accountingProcessor.exceptions;

import java.util.List;

public class TransactionGlobalErrorResponse {

    private int statusCode;
    private String message;

    private List<TransactionExceptionResponse> customAccountExceptionResponseList;

    public TransactionGlobalErrorResponse(int value, String message) {
        this.statusCode = value;
        this.message = message;
    }

    @Override
    public String toString() {
        return "TransactionGlobalErrorResponse{" +
                "statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", customAccountExceptionResponseList=" + customAccountExceptionResponseList +
                '}';
    }
}
