package com.prageeth.exception;

public class ExistingResourceException extends Exception {

    public ExistingResourceException(String message) {
        super("Resource already exist - " + message);
    }

}
