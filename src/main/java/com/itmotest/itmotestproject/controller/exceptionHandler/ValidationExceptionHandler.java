package com.itmotest.itmotestproject.controller.exceptionHandler;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Hidden
@RestControllerAdvice
public class ValidationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Response.builder()
                        .time(LocalDateTime.now())
                        .status(String.valueOf(HttpStatus.BAD_REQUEST.value()))
                        .errors(errors)
                        .build()
        );
    }

    @Getter
    @Builder
    private static class Response {
        private LocalDateTime time;
        private String status;
        private Map<String, String> errors;
    }
}
