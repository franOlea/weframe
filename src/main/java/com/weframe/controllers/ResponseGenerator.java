package com.weframe.controllers;

import com.weframe.controllers.errors.Error;
import com.weframe.controllers.errors.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collection;
import java.util.Collections;

public class ResponseGenerator<T> {

	public ResponseEntity<ErrorResponse> generateInternalServerErrorResponse() {
		return new ResponseEntity<>(
				new ErrorResponse(Error.createInternalServerError()),
				new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR
		);
	}

	public ResponseEntity<ErrorResponse> generatePageRequestErrorResponse() {
		return new ResponseEntity<>(
				new ErrorResponse(
						Error.createPageRequestError()
				),
				new HttpHeaders(),
				HttpStatus.UNPROCESSABLE_ENTITY
		);
	}

    public ResponseEntity<ErrorResponse> generateErrorResponse(
            final Collection<Error> errors,
            final HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ErrorResponse(errors),
                new HttpHeaders(),
                httpStatus
        );
    }

    public ResponseEntity<ErrorResponse> generateErrorResponse(
            final Error error,
            final HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ErrorResponse(Collections.singleton(error)),
                new HttpHeaders(),
                httpStatus
        );
    }

    public ResponseEntity<ErrorResponse> generateErrorResponse(
            final String errorTitle,
            final String errorDescription,
            final HttpStatus httpStatus) {
        return new ResponseEntity<>(
                new ErrorResponse(
                        Collections.singleton(
                                new Error(errorTitle, errorDescription)
                        )
                ),
                new HttpHeaders(),
                httpStatus
        );
    }

    public ResponseEntity generateEmptyResponse() {
        return new ResponseEntity<>(
                new HttpHeaders(),
                HttpStatus.NO_CONTENT
        );
    }

    public ResponseEntity generateOkResponse() {
        return new ResponseEntity<>(
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<T> generateResponse(final T t) {
        return new ResponseEntity<>(
                t,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    public ResponseEntity<Collection<T>> generateResponse(
            final Collection<T> ts) {
        return new ResponseEntity<>(
                ts,
                new HttpHeaders(),
                HttpStatus.OK
        );
    }

    ResponseEntity generateResponse(final HttpStatus httpStatus) {
        return new ResponseEntity(httpStatus);
    }

}
