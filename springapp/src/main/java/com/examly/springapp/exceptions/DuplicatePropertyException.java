package com.examly.springapp.exceptions;

/**
 * Exception thrown when a property already exists.
 * Used to prevent duplicate entries during property creation.
 */

public class DuplicatePropertyException extends RuntimeException {

    // Initializes exception for duplicate property entries
    public DuplicatePropertyException(String msg) {
        super(msg);
    }
}
