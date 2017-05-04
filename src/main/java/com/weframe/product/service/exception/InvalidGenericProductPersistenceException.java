package com.weframe.product.service.exception;

public class InvalidGenericProductPersistenceException extends Exception {
    public InvalidGenericProductPersistenceException(String message) {
        super(message);
    }

    public InvalidGenericProductPersistenceException(Throwable cause) {
        super(cause);
    }
}
