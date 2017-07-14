package com.weframe.controllers.errors;

import org.apache.commons.lang3.Validate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;

@SuppressWarnings({"unused", "WeakerAccess"})
public class ErrorResponse {

    private Collection<Error> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(final Collection<Error> errors) {
        Validate.notEmpty(errors, "The errors cannot be empty.");

        this.errors = errors;
    }

    public ErrorResponse(final Error error) {
        Validate.notNull(error, "The error cannot be null.");

        this.errors = Collections.singleton(error);
    }

    public Collection<Error> getErrors() {
        return errors;
    }

}
