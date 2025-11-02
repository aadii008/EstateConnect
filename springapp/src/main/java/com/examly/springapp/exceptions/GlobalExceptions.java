package com.examly.springapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


/**
 * Handles custom exceptions globally and maps them to HTTP 409 Conflict responses.
 * Ensures consistent error handling across property-related operations.
 */

@ControllerAdvice
public class GlobalExceptions {

    // Thrown when a property already exists — returns 409 Conflict.   
    @ExceptionHandler(DuplicatePropertyException.class)
    public ResponseEntity<String> duplicatePropertyException(DuplicatePropertyException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    // Handles property-related conflicts or rule violations — returns 409 Conflict.
    @ExceptionHandler(PropertyException.class)
    public ResponseEntity<String> propertyException(PropertyException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }

    // Raised during inquiry conflicts (e.g., duplicate inquiry) — returns 409 Conflict.
    @ExceptionHandler(PropertyInquiryException.class)
    public ResponseEntity<String> propertyInquiryException(PropertyInquiryException e){
        return ResponseEntity.status(409).body(e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
 
        ex.getBindingResult().getFieldErrors().forEach(error -> 
            errors.put(error.getField(), error.getDefaultMessage())
        );
 
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleExceptions(Exception ex) { 
        return ResponseEntity.status(400).body(ex.getMessage());
    }
 

}
