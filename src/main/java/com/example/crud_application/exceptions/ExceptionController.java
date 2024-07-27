package com.example.crud_application.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.ZonedDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    private void logError(HttpStatus status, String error, ZonedDateTime dateTime, Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTraceString = sw.toString().replace(", ", "\n");

        log.error("[!] Received the status {}. Error: {}. Time: {}.\n{}", status, error, dateTime, stackTraceString);
    }


    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handleThrowable(
            Throwable throwable
    ) {
        String message = throwable.getMessage().split(":")[0];
        ZonedDateTime now = ZonedDateTime.now();
        logError(INTERNAL_SERVER_ERROR, message, now, throwable);

        return new ErrorResponse(INTERNAL_SERVER_ERROR.value(), message, now);
    }

    @ExceptionHandler(CrudApplicationEmailExistsException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handleCrudApplicationEmailExistsException(
            CrudApplicationEmailExistsException exception
    ) {
        String message = exception.getMessage();
        ZonedDateTime now = ZonedDateTime.now();
        logError(BAD_REQUEST, message, now, exception);

        return new ErrorResponse(BAD_REQUEST.value(), message, now);
    }

    @ExceptionHandler(CrudApplicationNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ErrorResponse handleCrudApplicationNotFoundException(
            CrudApplicationNotFoundException exception
    ) {
        String message = exception.getMessage();
        ZonedDateTime now = ZonedDateTime.now();
        logError(NOT_FOUND, message, now, exception);

        return new ErrorResponse(NOT_FOUND.value(), message, now);
    }
}
