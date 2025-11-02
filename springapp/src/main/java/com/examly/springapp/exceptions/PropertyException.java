package com.examly.springapp.exceptions;

/**
 * Exception thrown for general property-related issues.
 * Used to indicate conflicts or validation errors during property operations.
 */

public class PropertyException extends RuntimeException {

    // Initializes exception for general property-related issues.
    public PropertyException(String msg) {
        super(msg);
    }
}
