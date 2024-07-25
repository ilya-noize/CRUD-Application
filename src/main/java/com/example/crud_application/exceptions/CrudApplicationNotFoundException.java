package com.example.crud_application.exceptions;


public class CrudApplicationNotFoundException extends RuntimeException {
    public CrudApplicationNotFoundException(String message) {
        super(message);
    }
}
