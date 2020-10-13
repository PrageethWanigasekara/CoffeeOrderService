package com.prageeth.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse>handleResourceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse response = new ErrorResponse();
        response.setError(exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        logger.error("Error : {}",exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse>handleAnyException(Exception exception){
        ErrorResponse response = new ErrorResponse();
        response.setError("Server Error Occurred");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("Error : {}",exception.getMessage());
        return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
