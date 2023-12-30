package com.exchangeRate.exchangeRate.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RateExceptionControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<RatesExceptionResponse> restOfExceptions(Exception ratesException, WebRequest request)
    {
        RatesExceptionResponse exceptionResponse = new RatesExceptionResponse();
        exceptionResponse.setMessage(ratesException.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
    }

    @ExceptionHandler(RateNotMaintained.class)
    public ResponseEntity<RatesExceptionResponse> rateNotMaintainedExeption(RateNotMaintained ratesException, WebRequest request)
    {
        RatesExceptionResponse exceptionResponse = new RatesExceptionResponse();
        exceptionResponse.setMessage(ratesException.getMessage());
        exceptionResponse.setError_code(ratesException.getErrorCode());
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(exceptionResponse);
    }

}
