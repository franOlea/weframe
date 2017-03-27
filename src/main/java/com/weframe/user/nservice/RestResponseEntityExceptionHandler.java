package com.weframe.user.nservice;

import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

//@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ RepositoryConstraintViolationException.class })
    public ResponseEntity<Object> handleAccessDeniedException(Exception exception, WebRequest request) {

        RepositoryConstraintViolationException nevEx = (RepositoryConstraintViolationException) exception;

        String errors = nevEx.getErrors().getAllErrors()
                .stream()
                .map(ObjectError::toString)
                .collect(Collectors.joining("\n"));

        return new ResponseEntity<>(errors, new HttpHeaders(),
                HttpStatus.PARTIAL_CONTENT);
    }
}