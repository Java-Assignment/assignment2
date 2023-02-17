package com.abhi.assignment2.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(value = {AccountFileUploadException.class})
    public ResponseEntity<String>handleException(AccountFileUploadException exception){
        log.error(exception.getMessage(),exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
