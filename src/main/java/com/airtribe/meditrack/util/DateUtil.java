package com.airtribe.meditrack.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Utility class for date and time operations.
 */
public class DateUtil {
    private static final DateTimeFormatter DATE_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter DATE_TIME_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private DateUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Parse a date string in yyyy-MM-dd format.
     * @param dateString the date string
     * @return LocalDate object
     */
    public static LocalDate parseDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    /**
     * Parse a date-time string in yyyy-MM-dd HH:mm:ss format.
     * @param dateTimeString the date-time string
     * @return LocalDateTime object
     */
    public static LocalDateTime parseDateTime(String dateTimeString) {
        return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
    }

    /**
     * Format a LocalDate to string.
     * @param date the date to format
     * @return formatted date string
     */
    public static String formatDate(LocalDate date) {
        return date.format(DATE_FORMATTER);
    }

    /**
     * Format a LocalDateTime to string.
     * @param dateTime the date-time to format
     * @return formatted date-time string
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        return dateTime.format(DATE_TIME_FORMATTER);
    }

    /**
     * Check if a date is in the future.
     * @param date the date to check
     * @return true if date is in future, false otherwise
     */
    public static boolean isFutureDate(LocalDate date) {
        return date.isAfter(LocalDate.now());
    }

    /**
     * Check if a date-time is in the future.
     * @param dateTime the date-time to check
     * @return true if date-time is in future, false otherwise
     */
    public static boolean isFutureDateTime(LocalDateTime dateTime) {
        return dateTime.isAfter(LocalDateTime.now());
    }

    /**
     * Get days between two dates.
     * @param startDate the start date
     * @param endDate the end date
     * @return number of days between dates
     */
    public static long daysBetween(LocalDate startDate, LocalDate endDate) {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    /**
     * Get hours between two date-times.
     * @param startDateTime the start date-time
     * @param endDateTime the end date-time
     * @return number of hours between date-times
     */
    public static long hoursBetween(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        return ChronoUnit.HOURS.between(startDateTime, endDateTime);
    }

    /**
     * Get the current date.
     * @return today's date
     */
    public static LocalDate today() {
        return LocalDate.now();
    }

    /**
     * Get the current date-time.
     * @return current date-time
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }
}
