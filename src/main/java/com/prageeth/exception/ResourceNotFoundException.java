package com.prageeth.exception;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String message) {
        super("Resource not found - " + message);
    }


}
