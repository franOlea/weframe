package com.weframe.user.service.persistence.exception;

public class InvalidFieldException extends Exception {

    private final String field;
    private final String message;

    public InvalidFieldException(final String field, final String message) {
        this.field = field;
        this.message = message;
    }

}
