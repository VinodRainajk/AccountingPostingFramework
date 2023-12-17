package com.casaService.casaService.exception;

public class CustomAccountExceptionResponse {
    String error_code;
    String message;

    public CustomAccountExceptionResponse(String errors_code, String message) {
        this.error_code = errors_code;
        this.message = message;
    }

    public String getErrors_code() {
        return error_code;
    }

    public void setErrors_code(String errors_code) {
        this.error_code = errors_code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
