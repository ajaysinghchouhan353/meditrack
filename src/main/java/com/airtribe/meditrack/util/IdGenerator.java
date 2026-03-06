package com.airtribe.meditrack.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for generating unique IDs.
 */
public class IdGenerator {
    private static final AtomicLong DOCTOR_ID_COUNTER = new AtomicLong(1000);
    private static final AtomicLong PATIENT_ID_COUNTER = new AtomicLong(2000);
    private static final AtomicLong APPOINTMENT_ID_COUNTER = new AtomicLong(3000);
    private static final AtomicLong BILL_ID_COUNTER = new AtomicLong(4000);

    private IdGenerator() {
        // Private constructor to prevent instantiation
    }

    /**
     * Generate a unique doctor ID.
     * @return unique doctor ID
     */
    public static String generateDoctorId() {
        return "DOC" + DOCTOR_ID_COUNTER.getAndIncrement();
    }

    /**
     * Generate a unique patient ID.
     * @return unique patient ID
     */
    public static String generatePatientId() {
        return "PAT" + PATIENT_ID_COUNTER.getAndIncrement();
    }

    /**
     * Generate a unique appointment ID.
     * @return unique appointment ID
     */
    public static String generateAppointmentId() {
        return "APT" + APPOINTMENT_ID_COUNTER.getAndIncrement();
    }

    /**
     * Generate a unique bill ID.
     * @return unique bill ID
     */
    public static String generateBillId() {
        return "BILL" + BILL_ID_COUNTER.getAndIncrement();
    }

    /**
     * Reset ID counters (useful for testing).
     */
    public static void resetCounters() {
        DOCTOR_ID_COUNTER.set(1000);
        PATIENT_ID_COUNTER.set(2000);
        APPOINTMENT_ID_COUNTER.set(3000);
        BILL_ID_COUNTER.set(4000);
    }
}
