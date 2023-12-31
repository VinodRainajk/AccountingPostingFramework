package com.casaService.casaService.exception;


import lombok.*;

import java.util.List;


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
