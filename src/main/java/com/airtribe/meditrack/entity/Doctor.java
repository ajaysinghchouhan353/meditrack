package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.constants.Specialization;

/**
 * Doctor entity representing a healthcare professional with type-safe specialization.
 */
public class Doctor extends Person {
    private static final long serialVersionUID = 1L;

    private Specialization specialty;
    private String licenseNumber;
    private int yearsOfExperience;
    private boolean available;

    public Doctor(String id, String name, String email, String phoneNumber, String address,
                  Specialization specialty, String licenseNumber, int yearsOfExperience) {
        super(id, name, email, phoneNumber, address);
        this.specialty = specialty;
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.available = true;
    }

    /**
     * Construct Doctor with specialty as string, converting to enum.
     */
    public Doctor(String id, String name, String email, String phoneNumber, String address,
                  String specialtyStr, String licenseNumber, int yearsOfExperience) {
        super(id, name, email, phoneNumber, address);
        this.specialty = Specialization.fromName(specialtyStr);
        this.licenseNumber = licenseNumber;
        this.yearsOfExperience = yearsOfExperience;
        this.available = true;
    }

    public Specialization getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialization specialty) {
        this.specialty = specialty;
    }

    /**
     * Set specialty from string.
     */
    public void setSpecialtyFromString(String specialtyStr) {
        this.specialty = Specialization.fromName(specialtyStr);
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public Doctor clone() throws CloneNotSupportedException {
        Doctor clonedDoctor = new Doctor(
                getId(),
                getName(),
                getEmail(),
                getPhoneNumber(),
                getAddress(),
                specialty,
                licenseNumber,
                yearsOfExperience
        );
        clonedDoctor.setAvailable(available);
        return clonedDoctor;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", specialty=" + specialty.getName() + " (" + specialty.getDescription() + ")" +
                ", licenseNumber='" + licenseNumber + '\'' +
                ", yearsOfExperience=" + yearsOfExperience +
                ", available=" + available +
                '}';
    }
}
