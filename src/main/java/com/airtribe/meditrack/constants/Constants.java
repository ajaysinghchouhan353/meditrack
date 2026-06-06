package com.airtribe.meditrack.constants;

import java.io.File;

/**
 * Constants for the MediTrack application.
 */
public final class Constants {
    public static final String DATA_DIRECTORY;
    public static final String DOCTORS_DATA_FILE;
    public static final String PATIENTS_DATA_FILE;
    public static final String APPOINTMENTS_DATA_FILE;
    public static final String BILLS_DATA_FILE;

    static {
        DATA_DIRECTORY = "data";
        DOCTORS_DATA_FILE = DATA_DIRECTORY + File.separator + "doctors.ser";
        PATIENTS_DATA_FILE = DATA_DIRECTORY + File.separator + "patients.ser";
        APPOINTMENTS_DATA_FILE = DATA_DIRECTORY + File.separator + "appointments.ser";
        BILLS_DATA_FILE = DATA_DIRECTORY + File.separator + "bills.ser";
    }

    private Constants() {
        // Private constructor to prevent instantiation
    }

    // Appointment related constants
    public static final String APPOINTMENT_BOOKED = "BOOKED";
    public static final String APPOINTMENT_CANCELLED = "CANCELLED";
    public static final String APPOINTMENT_COMPLETED = "COMPLETED";

    // Booking time constraints (in hours before appointment)
    public static final int MIN_BOOKING_ADVANCE_HOURS = 1;
    public static final int MAX_BOOKING_ADVANCE_DAYS = 30;

    // Doctor related constants
    public static final int MAX_APPOINTMENTS_PER_SLOT = 3;
    public static final String[] SPECIALTIES = {"General", "Cardiology", "Neurology", "Orthopedics", "Dermatology"};

    // Billing constants
    public static final double CONSULTATION_FEE = 500.0;
    public static final double PREMIUM_CONSULTATION_FEE = 750.0;

    // File paths for data persistence
    public static final String DOCTORS_FILE = "doctors.csv";
    public static final String PATIENTS_FILE = "patients.csv";
    public static final String APPOINTMENTS_FILE = "appointments.csv";
    public static final String BILLS_FILE = "bills.csv";

    // Date format
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "HH:mm:ss";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // Error messages
    public static final String INVALID_EMAIL = "Invalid email format";
    public static final String INVALID_PHONE = "Invalid phone number format";
    public static final String INVALID_DATE = "Invalid date format";
    public static final String DOCTOR_NOT_FOUND = "Doctor not found";
    public static final String PATIENT_NOT_FOUND = "Patient not found";
    public static final String APPOINTMENT_NOT_FOUND = "Appointment not found";
    public static final String SLOT_NOT_AVAILABLE = "Appointment slot not available";
    public static final String INSUFFICIENT_DATA = "Insufficient data provided";
}
