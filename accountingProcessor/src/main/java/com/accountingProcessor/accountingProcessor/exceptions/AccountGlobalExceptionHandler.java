package com.accountingProcessor.accountingProcessor.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {

        return buildErrorResponse(exception, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
    }


    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      HttpStatus httpStatus,
                                                      WebRequest request) {
        return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
    }

    private ResponseEntity<Object> buildErrorResponse(Exception exception,
                                                      String message,
                                                      HttpStatus httpStatus,
                                                      WebRequest request)
    {

       if(exception instanceof AccountCustomException)
        {
            return ResponseEntity.status(httpStatus).body(((AccountCustomException) exception).getCustomAccountExceptionResponse());
        }else
       {
           AccountGlobalErrorResponse accountGlobalErrorResponse = new AccountGlobalErrorResponse(httpStatus.value(), message);
           return ResponseEntity.status(httpStatus).body(accountGlobalErrorResponse);
       }


    }

    @ExceptionHandler(AccountCustomException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(AccountCustomException accountCustomException, WebRequest request) {

        if(accountCustomException.getStatusCode()==null)
        {
            accountCustomException.setStatusCode(HttpStatus.NOT_FOUND);
        }
        return buildErrorResponse(accountCustomException, accountCustomException.getStatusCode(), request);
    }
}
