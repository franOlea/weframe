package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.controllers.errors.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public class ResponseGenerator<T> {

    ResponseEntity<ErrorResponse> createErrorResponse(
            final Collection<Error> errors,
            final HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ErrorResponse(errors),
                new HttpHeaders(),
                httpStatus
        );
    }

    ResponseEntity generateEmptyResponse() {
        return new ResponseEntity<>(
                null,
                new HttpHeaders(),
                HttpStatus.NO_CONTENT
        );
    }

    ResponseEntity<T> generateResponse(final T t) {
        return new ResponseEntity<>(
                t,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    ResponseEntity<Collection<T>> generateResponse(
            final Collection<T> ts) {
        return new ResponseEntity<>(
                ts,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

}
