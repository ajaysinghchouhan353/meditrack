package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;

/**
 * Service class for managing patients.
 */
public class PatientService {
    private static final Logger logger = LoggerFactory.getLogger(PatientService.class);
    private final DataStore<Patient> patientStore;

    public PatientService(DataStore<Patient> patientStore) {
        this.patientStore = patientStore;
    }

    /**
     * Register a new patient.
     * @param name the patient's name
     * @param email the patient's email
     * @param phoneNumber the patient's phone number
     * @param address the patient's address
     * @param dateOfBirth the patient's date of birth
     * @param gender the patient's gender
     * @param bloodGroup the patient's blood group
     * @return the registered patient
     */
    public Patient registerPatient(String name, String email, String phoneNumber, String address,
                                   LocalDate dateOfBirth, String gender, String bloodGroup)
            throws InvalidDataException {
        logger.debug("Registering patient: name={}, email={}, gender={}, bloodGroup={}", name, email, gender, bloodGroup);
        validatePatientData(name, email, phoneNumber, gender, bloodGroup);

        String patientId = IdGenerator.generatePatientId();
        Patient patient = new Patient(patientId, name, email, phoneNumber, address,
                dateOfBirth, gender, bloodGroup);
        patientStore.add(patient);
        logger.info("Patient registered successfully: id={}, name={}", patientId, name);
        return patient;
    }

    /**
     * Get a patient by ID.
     * @param patientId the patient's ID
     * @return Optional containing the patient or empty
     */
    public Optional<Patient> getPatientById(String patientId) {
        return patientStore.findById(patientId);
    }

    /**
     * Search a patient by ID.
     * @param patientId the patient ID
     * @return Optional containing the patient or empty
     */
    public Optional<Patient> searchPatient(String patientId) {
        return getPatientById(patientId);
    }

    /**
     * Search patients by name.
     * @param name the name to search for
     * @return list of matching patients
     */
    public List<Patient> searchPatient(String name, boolean searchByName) {
        if (!searchByName) {
            return List.of();
        }
        return searchPatientByName(name);
    }

    /**
     * Search patients by age.
     * @param age the age to search for
     * @return list of matching patients
     */
    public List<Patient> searchPatient(int age) {
        return searchPatientByAge(age);
    }

    /**
     * Get all patients.
     * @return list of all patients
     */
    public List<Patient> getAllPatients() {
        return patientStore.getAll();
    }

    /**
     * Get patients by gender.
     * @param gender the gender
     * @return list of patients with the given gender
     */
    public List<Patient> getPatientsByGender(String gender) {
        return patientStore.filter(patient -> patient.getGender().equalsIgnoreCase(gender));
    }

    /**
     * Get patients by blood group.
     * @param bloodGroup the blood group
     * @return list of patients with the given blood group
     */
    public List<Patient> getPatientsByBloodGroup(String bloodGroup) {
        return patientStore.filter(patient -> patient.getBloodGroup().equalsIgnoreCase(bloodGroup));
    }

    /**
     * Search patients by name.
     * @param name the name to search for
     * @return list of matching patients
     */
    public List<Patient> searchPatientByName(String name) {
        return patientStore.filter(patient -> patient.getName() != null
                && patient.getName().toLowerCase().contains(name.toLowerCase()));
    }

    /**
     * Search patients by age.
     * @param age the age to search for
     * @return list of matching patients
     */
    public List<Patient> searchPatientByAge(int age) {
        return patientStore.filter(patient -> patient.getDateOfBirth() != null
                && Period.between(patient.getDateOfBirth(), LocalDate.now()).getYears() == age);
    }

    /**
     * Update patient information.
     * @param patientId the patient's ID
     * @param patient the updated patient information
     * @return true if updated, false otherwise
     */
    public boolean updatePatient(String patientId, Patient patient) throws InvalidDataException {
        validatePatientData(patient.getName(), patient.getEmail(), patient.getPhoneNumber(),
                patient.getGender(), patient.getBloodGroup());
        return patientStore.update(patientId, patient);
    }

    /**
     * Delete a patient.
     * @param patientId the patient's ID
     * @return true if deleted, false otherwise
     */
    public boolean deletePatient(String patientId) {
        logger.debug("Attempting to delete patient: id={}", patientId);
        boolean deleted = patientStore.remove(patientId);
        if (deleted) {
            logger.info("Patient deleted successfully: id={}", patientId);
        } else {
            logger.warn("Failed to delete patient: id={}", patientId);
        }
        return deleted;
    }

    /**
     * Update patient medical history.
     * @param patientId the patient's ID
     * @param medicalHistory the updated medical history
     * @return true if updated, false otherwise
     */
    public boolean updateMedicalHistory(String patientId, String medicalHistory) {
        Optional<Patient> optionalPatient = patientStore.findById(patientId);
        if (optionalPatient.isPresent()) {
            Patient patient = optionalPatient.get();
            patient.setMedicalHistory(medicalHistory);
            return patientStore.update(patientId, patient);
        }
        return false;
    }

    /**
     * Get the total number of patients.
     * @return total number of patients
     */
    public int getTotalPatients() {
        return patientStore.size();
    }

    /**
     * Validate patient data.
     */
    private void validatePatientData(String name, String email, String phoneNumber,
                                    String gender, String bloodGroup) throws InvalidDataException {
        Validator.validateOrThrow(Validator.isNotEmpty(name), "Patient name cannot be empty");
        Validator.validateOrThrow(Validator.isValidEmail(email), "Invalid email format");
        Validator.validateOrThrow(Validator.isValidPhoneNumber(phoneNumber), 
                "Invalid phone number format");
        Validator.validateOrThrow(Validator.isNotEmpty(gender), "Gender cannot be empty");
        Validator.validateOrThrow(Validator.isNotEmpty(bloodGroup), "Blood group cannot be empty");
    }
}
