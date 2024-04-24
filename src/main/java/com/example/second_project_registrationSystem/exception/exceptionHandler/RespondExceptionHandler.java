package com.example.second_project_registrationSystem.exception.exceptionHandler;

import com.example.second_project_registrationSystem.exception.HttpResponseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RespondExceptionHandler {
    @ExceptionHandler(HttpResponseException.class)
    public ResponseEntity<String> handleHttpResponseException(HttpResponseException e) {
        return ResponseEntity.status
                (e.getHttpStatus()).body(e.getMessage());
    }


}
