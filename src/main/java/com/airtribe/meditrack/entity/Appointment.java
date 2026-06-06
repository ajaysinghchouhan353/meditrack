package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.interfaces.Searchable;
import com.airtribe.meditrack.constants.AppointmentStatus;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Appointment entity with cloning support (deep copy).
 * Demonstrates Cloneable interface with enum usage for status.
 * Immutable LocalDateTime is safely shared in clones.
 */
public class Appointment implements Searchable, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String doctorId;
    private String patientId;
    private LocalDateTime appointmentDateTime;
    private AppointmentStatus status;  // Use enum instead of String
    private String reason;
    private String notes;

    public Appointment(String id, String doctorId, String patientId, 
                      LocalDateTime appointmentDateTime, String reason) {
        this.id = id;
        this.doctorId = doctorId;
        this.patientId = patientId;
        this.appointmentDateTime = appointmentDateTime;
        this.reason = reason;
        this.status = AppointmentStatus.PENDING;
        this.notes = "";
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getAppointmentDateTime() {
        return appointmentDateTime;
    }

    public void setAppointmentDateTime(LocalDateTime appointmentDateTime) {
        this.appointmentDateTime = appointmentDateTime;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    /**
     * Set status from string (for backward compatibility).
     * @param statusStr the status string
     */
    public void setStatusFromString(String statusStr) {
        this.status = AppointmentStatus.fromCode(statusStr);
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Create a shallow copy of appointment.
     * @return a shallow copy
     */
    public Appointment shallowCopy() {
        try {
            return (Appointment) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Shallow copy failed", e);
        }
    }

    /**
     * Create a deep copy of appointment.
     * LocalDateTime is immutable, so safe to share.
     * @return a deep copy of this appointment
     */
    @Override
    public Appointment clone() throws CloneNotSupportedException {
        Appointment cloned = (Appointment) super.clone();
        
        // Deep copy string fields
        cloned.id = new String(this.id);
        cloned.doctorId = new String(this.doctorId);
        cloned.patientId = new String(this.patientId);
        cloned.reason = new String(this.reason);
        cloned.notes = new String(this.notes);
        
        // Enum is immutable, no need to copy
        // LocalDateTime is immutable, safe to share
        
        return cloned;
    }

    /**
     * Deep copy using copy constructor pattern.
     * @param source the appointment to copy
     * @return a new deep-copied appointment
     */
    public static Appointment deepCopy(Appointment source) {
        Appointment copy = new Appointment(
            source.id,
            source.doctorId,
            source.patientId,
            source.appointmentDateTime,
            source.reason
        );
        copy.status = source.status;
        copy.notes = new String(source.notes);
        return copy;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", patientId='" + patientId + '\'' +
                ", appointmentDateTime=" + appointmentDateTime +
                ", status=" + status +
                ", reason='" + reason + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
