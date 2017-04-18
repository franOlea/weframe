package com.weframe.controllers.errors;

import org.apache.commons.lang3.Validate;

import java.util.Collection;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ErrorResponse {

    private Collection<Error> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(final Collection<Error> errors) {
        Validate.notEmpty(errors, "The errors cannot be empty.");

        this.errors = errors;
    }

    public Collection<Error> getErrors() {
        return errors;
    }
}
