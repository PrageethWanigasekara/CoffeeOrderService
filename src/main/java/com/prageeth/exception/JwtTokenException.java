package com.prageeth.exception;

public class JwtTokenException extends Exception {

    public JwtTokenException(String message) {
        super("JWT error - " + message);
    }
}
