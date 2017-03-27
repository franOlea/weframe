package com.weframe.controllers.errors;

import java.util.Collection;

public class ErrorResponse {

    private Collection<Error> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(Collection<Error> errors) {
        this.errors = errors;
    }

}
