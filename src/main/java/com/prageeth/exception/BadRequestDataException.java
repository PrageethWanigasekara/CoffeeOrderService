package com.prageeth.exception;

public class BadRequestDataException extends Exception {

    public BadRequestDataException(String message) {
        super("Resource not found - " + message);
    }


}
