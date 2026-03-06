package com.airtribe.meditrack.entity;

import java.time.LocalDate;

/**
 * Patient entity with cloning support (deep copy).
 * Demonstrates Cloneable interface with proper deep copy implementation.
 * Medical history and other nested data are properly cloned.
 */
public class Patient extends Person implements Cloneable {
    private static final long serialVersionUID = 1L;

    private LocalDate dateOfBirth;
    private String gender;
    private String bloodGroup;
    private String medicalHistory;

    public Patient(String id, String name, String email, String phoneNumber, String address,
                   LocalDate dateOfBirth, String gender, String bloodGroup) {
        super(id, name, email, phoneNumber, address);
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodGroup = bloodGroup;
        this.medicalHistory = "";
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    /**
     * Create a shallow copy of patient.
     * Same as Object.clone() - references are shared.
     * @return a shallow copy
     */
    public Patient shallowCopy() {
        try {
            return (Patient) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Shallow copy failed", e);
        }
    }

    /**
     * Create a deep copy of patient.
     * All nested objects (LocalDate, String) are independently copied.
     * @return a deep copy of this patient
     */
    @Override
    public Patient clone() throws CloneNotSupportedException {
        Patient cloned = (Patient) super.clone();
        
        // Deep copy of immutable objects (LocalDate is immutable, but good practice)
        // LocalDate.clone() not needed as LocalDate is immutable
        // For String - also immutable
        // For medicalHistory - create new String to be safe
        cloned.medicalHistory = new String(this.medicalHistory);
        
        return cloned;
    }

    /**
     * Deep copy using copy constructor pattern.
     * Alternative to clone() method.
     * @param source the patient to copy
     * @return a new deep-copied patient
     */
    public static Patient deepCopy(Patient source) {
        Patient copy = new Patient(
            source.id,
            new String(source.name),
            new String(source.email),
            new String(source.phoneNumber),
            new String(source.address),
            source.dateOfBirth,  // LocalDate is immutable, safe to share
            new String(source.gender),
            new String(source.bloodGroup)
        );
        copy.medicalHistory = new String(source.medicalHistory);
        return copy;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", bloodGroup='" + bloodGroup + '\'' +
                ", medicalHistory='" + medicalHistory + '\'' +
                '}';
    }
}
