package com.prageeth.exception;

public class AuthException extends Exception {

    public AuthException(String message) {
        super("Refuse to authorise : " + message);
    }

}
