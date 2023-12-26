package com.accountingProcessor.accountingProcessor.exceptions;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import jakarta.ws.rs.BadRequestException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class FeingExceptionHandler implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorDecoder errorDecoder = new Default();
        System.out.println("Failed with exeption inside FeingExceptionHandler"+response.status());

        CustomAccountExceptionResponse exceptionMessage = null;
        Reader reader = null;
       try
        {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            exceptionMessage = mapper.readValue(result,  CustomAccountExceptionResponse.class);
            System.out.println("here inside "+exceptionMessage);
            System.out.println("result "+result);

        } catch (IOException e) {
           System.out.println("here inside "+e.toString());
            return new Exception(e.getMessage());
        }
        System.out.println("Error in request went through feign client {} "+ exceptionMessage.getMessage());
        System.out.println("Error in request went through feign client {} "+ exceptionMessage.getError_code());

        switch (response.status()) {
            case 417:
                System.out.println("Error in request went through feign client {} "+ exceptionMessage.getMessage());
                System.out.println("Error in request went through feign client {} "+ exceptionMessage.getError_code());
                AccountCustomException accountCustomException = new AccountCustomException();
                accountCustomException.setCustomAccountExceptionResponse(exceptionMessage);
                return accountCustomException;

            default:
                System.out.println("Error in request went through feign client");
                return new Exception("Common Feign Exception");
        }
    }
}
