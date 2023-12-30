package com.accountingProcessor.accountingProcessor.exceptions;

import com.accountingProcessor.accountingProcessor.services.AccountBalances;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class AccountGlobalControllerAdvice extends ResponseEntityExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(AccountGlobalControllerAdvice.class);
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

       if(exception instanceof AccountingCustomException)
        {
            return ResponseEntity.status(httpStatus).body(((AccountingCustomException) exception).getCustomAccountExceptionResponse());
        }else
       {
           AccountingExceptionDetails accountingExceptionDetails = new AccountingExceptionDetails(String.valueOf(httpStatus.value()), message);
           return ResponseEntity.status(httpStatus).body(accountingExceptionDetails);
       }


    }

    @ExceptionHandler(AccountingCustomException.class)
    //@ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoSuchElementFoundException(AccountingCustomException accountingCustomException, WebRequest request) {
        LOGGER.info("Inside the AccountingCustomException");
        if(accountingCustomException.getStatusCode()==null)
        {
            accountingCustomException.setStatusCode(HttpStatus.NOT_FOUND);
        }
        return buildErrorResponse(accountingCustomException, accountingCustomException.getStatusCode(), request);
    }
}
