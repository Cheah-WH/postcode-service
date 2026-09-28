package com.wcc.postcode_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice // Handles exceptions coming from controllers and returns appropriate HTTP responses.
public class GlobalExceptionHandler {

    @ExceptionHandler(PostcodeNotFoundException.class) // Handle PostcodeNotFoundException thrown in any controller.
    @ResponseStatus(HttpStatus.NOT_FOUND) // Set the HTTP status code to 404 Not Found for this exception.
    public Map<String, String> handlePostcodeNotFound(
            PostcodeNotFoundException exception) {

        return Map.of(
                "error", exception.getMessage()
        );
    }
}