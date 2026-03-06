package com.airtribe.meditrack.constants;

/**
 * Enum for appointment status values.
 * Demonstrates best-practice use of enums instead of string constants.
 */
public enum AppointmentStatus {
    PENDING("PENDING", "Appointment is scheduled"),
    CONFIRMED("CONFIRMED", "Appointment is confirmed by doctor"),
    COMPLETED("COMPLETED", "Appointment has been completed"),
    CANCELLED("CANCELLED", "Appointment has been cancelled"),
    NO_SHOW("NO_SHOW", "Patient did not attend the appointment");

    private final String code;
    private final String description;

    /**
     * Constructor for AppointmentStatus enum.
     * @param code the status code
     * @param description the status description
     */
    AppointmentStatus(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get enum value from string code.
     * @param code the code
     * @return AppointmentStatus enum or null
     */
    public static AppointmentStatus fromCode(String code) {
        for (AppointmentStatus status : AppointmentStatus.values()) {
            if (status.code.equalsIgnoreCase(code)) {
                return status;
            }
        }
        return PENDING;
    }

    @Override
    public String toString() {
        return code + " - " + description;
    }
}
