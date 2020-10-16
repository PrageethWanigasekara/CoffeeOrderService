package com.prageeth.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

import java.util.List;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.add(error.getDefaultMessage());
        });
        logger.error("Validation error : {}",errors);
        ErrorResponse response = new ErrorResponse();
        response.setError(errors.toString());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setError(exception.getMessage());
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        logger.error("Not found error : {}", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthException.class)
    public ResponseEntity<ErrorResponse> handleAuthException(AuthException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setError(exception.getMessage());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        logger.error("Auth error : {}", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(JwtTokenException.class)
    public ResponseEntity<ErrorResponse> handleJwtTokenException(JwtTokenException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setError(exception.getMessage());
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        logger.error("JWT error : {}", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ErrorResponse> handleExpiredJwtException(ExpiredJwtException exception) {
        ErrorResponse response = new ErrorResponse();
        response.setError("JWT token expired");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        logger.error("JWT error : {}", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleAnyException(Exception exception) {
        ErrorResponse response = new ErrorResponse();
        response.setError("Server error Occurred");
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        logger.error("Runtime exception : {}", exception.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
