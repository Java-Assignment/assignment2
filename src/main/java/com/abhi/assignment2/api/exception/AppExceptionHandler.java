package com.abhi.assignment2.api.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {
    @ExceptionHandler(value = {AccountFileUploadException.class})
    public ResponseEntity<String> handleException(AccountFileUploadException exception) {
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @ExceptionHandler(value = AppAccountNotFoundException.class)
    public ResponseEntity<String> handleException(AppAccountNotFoundException exception) {
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value=AccountFileGenException.class)
    public ResponseEntity<String> hadleException(AccountFileGenException exception){
        log.error(exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
