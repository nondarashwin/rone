package com.store.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class StickResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(StocksException.class)
    public ResponseEntity<ExceptionResponse> StockNotFound(StocksException exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(exception.getMessage(), LocalDateTime.now());
        return new ResponseEntity(exceptionResponse, HttpStatus.NOT_FOUND);
    }


}
