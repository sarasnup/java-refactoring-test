package com.sap.refactoring.exception;

public class UserException extends Exception {

    String message;

    public UserException(String message) {
        super(message);
        this.message = message;
    }
}
