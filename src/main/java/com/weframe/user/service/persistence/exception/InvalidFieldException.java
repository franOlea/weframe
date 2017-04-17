package com.weframe.user.service.persistence.exception;

public class InvalidFieldException extends Exception {

    private final String field;
    private final String fieldContent;
    private final String details;

    public InvalidFieldException(final String field,
                                 final String fieldContent,
                                 final String details) {
        this.field = field;
        this.fieldContent = fieldContent;
        this.details = details;
    }

    public String getField() {
        return field;
    }

    public String getFieldContent() {
        return fieldContent;
    }

    public String getDetails() {
        return details;
    }
}
