package com.example.second_project_registrationSystem.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpResponseException extends RuntimeException{
    private final HttpStatus httpStatus;
    private final String message;

    public HttpResponseException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.message = message;
    }
}