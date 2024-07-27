package com.example.crud_application.exceptions;

import java.time.ZonedDateTime;

public record ErrorResponse(int status, String error, ZonedDateTime dateTime) {}
