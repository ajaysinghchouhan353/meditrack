package com.airtribe.meditrack.util;

import com.airtribe.meditrack.exception.InvalidDataException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Pattern;

/**
 * Utility class for data validation.
 */
public class Validator {
    private static final Logger logger = LoggerFactory.getLogger(Validator.class);
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");

    private Validator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validate email format.
     * @param email the email to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Validate phone number format (10 digits).
     * @param phoneNumber the phone number to validate
     * @return true if valid, false otherwise
     */
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    /**
     * Validate if a string is not null or empty.
     * @param value the value to validate
     * @return true if valid, false otherwise
     */
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    /**
     * Validate if a number is positive.
     * @param value the value to validate
     * @return true if positive, false otherwise
     */
    public static boolean isPositive(double value) {
        return value > 0;
    }

    /**
     * Validate if a number is non-negative.
     * @param value the value to validate
     * @return true if non-negative, false otherwise
     */
    public static boolean isNonNegative(double value) {
        return value >= 0;
    }

    /**
     * Validate and throw exception if data is invalid.
     * @param condition the condition to check
     * @param errorMessage the error message
     * @throws InvalidDataException if condition is false
     */
    public static void validateOrThrow(boolean condition, String errorMessage) 
            throws InvalidDataException {
        if (!condition) {
            logger.warn("Validation failed: {}", errorMessage);
            throw new InvalidDataException(errorMessage);
        }
    }
}
