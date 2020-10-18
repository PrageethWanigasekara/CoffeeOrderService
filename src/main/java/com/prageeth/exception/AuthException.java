package com.prageeth.exception;

public class AuthException extends Exception {

    public AuthException(String message) {
        super("Refused to authorise : " + message);
    }

}
