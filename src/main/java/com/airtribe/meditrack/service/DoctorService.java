package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing doctors.
 */
public class DoctorService {
    private final DataStore<Doctor> doctorStore;

    public DoctorService(DataStore<Doctor> doctorStore) {
        this.doctorStore = doctorStore;
    }

    /**
     * Register a new doctor.
     * @param name the doctor's name
     * @param email the doctor's email
     * @param phoneNumber the doctor's phone number
     * @param address the doctor's address
     * @param specialty the doctor's specialty
     * @param licenseNumber the doctor's license number
     * @param yearsOfExperience the doctor's years of experience
     * @return the registered doctor
     */
    public Doctor registerDoctor(String name, String email, String phoneNumber, String address,
                                 String specialty, String licenseNumber, int yearsOfExperience)
            throws InvalidDataException {
        validateDoctorData(name, email, phoneNumber, specialty, licenseNumber, yearsOfExperience);

        String doctorId = IdGenerator.generateDoctorId();
        Doctor doctor = new Doctor(doctorId, name, email, phoneNumber, address,
                specialty, licenseNumber, yearsOfExperience);
        doctorStore.add(doctor);
        return doctor;
    }

    /**
     * Get a doctor by ID.
     * @param doctorId the doctor's ID
     * @return Optional containing the doctor or empty
     */
    public Optional<Doctor> getDoctorById(String doctorId) {
        return doctorStore.findById(doctorId);
    }

    /**
     * Get all doctors.
     * @return list of all doctors
     */
    public List<Doctor> getAllDoctors() {
        return doctorStore.getAll();
    }

    /**
     * Get all doctors by specialty.
     * @param specialty the specialty
     * @return list of doctors with the given specialty
     */
    public List<Doctor> getDoctorsBySpecialty(String specialty) {
        Specialization spec = Specialization.fromName(specialty);
        return doctorStore.filter(doctor -> doctor.getSpecialty() == spec);
    }

    /**
     * Get all available doctors.
     * @return list of available doctors
     */
    public List<Doctor> getAvailableDoctors() {
        return doctorStore.filter(Doctor::isAvailable);
    }

    /**
     * Update doctor information.
     * @param doctorId the doctor's ID
     * @param doctor the updated doctor information
     * @return true if updated, false otherwise
     */
    public boolean updateDoctor(String doctorId, Doctor doctor) throws InvalidDataException {
        validateDoctorData(doctor.getName(), doctor.getEmail(), doctor.getPhoneNumber(),
            doctor.getSpecialty().getName(), doctor.getLicenseNumber(), doctor.getYearsOfExperience());
        return doctorStore.update(doctorId, doctor);
    }

    /**
     * Delete a doctor.
     * @param doctorId the doctor's ID
     * @return true if deleted, false otherwise
     */
    public boolean deleteDoctor(String doctorId) {
        return doctorStore.remove(doctorId);
    }

    /**
     * Set doctor availability.
     * @param doctorId the doctor's ID
     * @param available the availability status
     * @return true if updated, false otherwise
     */
    public boolean setDoctorAvailability(String doctorId, boolean available) {
        Optional<Doctor> optionalDoctor = doctorStore.findById(doctorId);
        if (optionalDoctor.isPresent()) {
            Doctor doctor = optionalDoctor.get();
            doctor.setAvailable(available);
            return doctorStore.update(doctorId, doctor);
        }
        return false;
    }

    /**
     * Get the total number of doctors.
     * @return total number of doctors
     */
    public int getTotalDoctors() {
        return doctorStore.size();
    }

    /**
     * Validate doctor data.
     */
    private void validateDoctorData(String name, String email, String phoneNumber,
                                   String specialty, String licenseNumber, int yearsOfExperience)
            throws InvalidDataException {
        Validator.validateOrThrow(Validator.isNotEmpty(name), "Doctor name cannot be empty");
        Validator.validateOrThrow(Validator.isValidEmail(email), "Invalid email format");
        Validator.validateOrThrow(Validator.isValidPhoneNumber(phoneNumber), 
                "Invalid phone number format");
        Validator.validateOrThrow(Validator.isNotEmpty(specialty), "Specialty cannot be empty");
        Validator.validateOrThrow(Validator.isNotEmpty(licenseNumber), "License number cannot be empty");
        Validator.validateOrThrow(yearsOfExperience >= 0, "Years of experience cannot be negative");
    }
}
