package com.examly.springapp.exceptions;

/**
 * Exception thrown when a conflict occurs during a property inquiry.
 * Used to handle cases like duplicate or invalid inquiry attempts.
 */

public class PropertyInquiryException extends RuntimeException {
    
    // Initializes exception for conflicts during property inquiry.
    public PropertyInquiryException(String msg) {
        super(msg);
    }
}
