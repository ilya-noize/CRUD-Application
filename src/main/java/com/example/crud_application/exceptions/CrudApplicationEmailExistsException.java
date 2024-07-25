package com.example.crud_application.exceptions;

public class CrudApplicationEmailExistsException extends RuntimeException {
    public CrudApplicationEmailExistsException(String message) {
        super(message);
    }
}
