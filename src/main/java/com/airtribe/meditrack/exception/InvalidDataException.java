package com.airtribe.meditrack.exception;

/**
 * Exception thrown when invalid data is provided.
 */
public class InvalidDataException extends Exception {
    public InvalidDataException(String message) {
        super(message);
    }

    public InvalidDataException(String message, Throwable cause) {
        super(message, cause);
    }
}
