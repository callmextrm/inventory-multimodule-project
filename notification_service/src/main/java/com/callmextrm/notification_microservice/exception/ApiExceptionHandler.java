package com.callmextrm.notification_microservice.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ResourceAlreadyFound.class)
    public ResponseEntity<String> handleResourceFound(ResourceAlreadyFound found) {
        return ResponseEntity.status(HttpStatus.FOUND).body(found.getMessage());
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<String> handleResourceNotFound(ResourceNotFound notFound) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(notFound.getMessage());
    }
}
