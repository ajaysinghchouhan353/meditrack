package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for CSV file operations.
 */
public class CSVUtil {
    private static final Logger logger = LoggerFactory.getLogger(CSVUtil.class);
    private CSVUtil() {
        // Private constructor to prevent instantiation
    }

    /**
     * Write doctors to CSV file.
     * @param filePath the file path
     * @param doctors the list of doctors
     */
    public static void writeDoctorsToCSV(String filePath, List<Doctor> doctors) throws IOException {
        logger.debug("Writing {} doctors to CSV file: {}", doctors.size(), filePath);
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.println("ID,Name,Email,Phone,Address,Specialty,License,Experience,Available");
            for (Doctor doctor : doctors) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%d,%b%n",
                    doctor.getId(), doctor.getName(), doctor.getEmail(),
                    doctor.getPhoneNumber(), doctor.getAddress(), doctor.getSpecialty(),
                    doctor.getLicenseNumber(), doctor.getYearsOfExperience(),
                    doctor.isAvailable());
            }
            logger.info("Successfully exported {} doctors to CSV: {}", doctors.size(), filePath);
        } catch (IOException e) {
            logger.error("Failed to write doctors to CSV file: {}", filePath, e);
            throw e;
        }
    }

    /**
     * Write patients to CSV file.
     * @param filePath the file path
     * @param patients the list of patients
     */
    public static void writePatientsToCSV(String filePath, List<Patient> patients) throws IOException {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.println("ID,Name,Email,Phone,Address,DOB,Gender,BloodGroup");
            for (Patient patient : patients) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s,%s%n",
                    patient.getId(), patient.getName(), patient.getEmail(),
                    patient.getPhoneNumber(), patient.getAddress(), patient.getDateOfBirth(),
                    patient.getGender(), patient.getBloodGroup());
            }
        }
    }

    /**
     * Write appointments to CSV file.
     * @param filePath the file path
     * @param appointments the list of appointments
     */
    public static void writeAppointmentsToCSV(String filePath, List<Appointment> appointments) 
            throws IOException {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.println("ID,DoctorID,PatientID,DateTime,Status,Reason,Notes");
            for (Appointment appointment : appointments) {
                writer.printf("%s,%s,%s,%s,%s,%s,%s%n",
                    appointment.getId(), appointment.getDoctorId(), appointment.getPatientId(),
                    DateUtil.formatDateTime(appointment.getAppointmentDateTime()),
                    appointment.getStatus(), appointment.getReason(), appointment.getNotes());
            }
        }
    }

    /**
     * Write bills to CSV file.
     * @param filePath the file path
     * @param bills the list of bills
     */
    public static void writeBillsToCSV(String filePath, List<Bill> bills) throws IOException {
        try (PrintWriter writer = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(filePath), StandardCharsets.UTF_8))) {
            writer.println("ID,AppointmentID,PatientID,DoctorID,ConsultationFee,LabCharges," +
                    "MedicineCharges,TotalAmount,BillDate,Paid");
            for (Bill bill : bills) {
                writer.printf("%s,%s,%s,%s,%.2f,%.2f,%.2f,%.2f,%s,%b%n",
                    bill.getId(), bill.getAppointmentId(), bill.getPatientId(),
                    bill.getDoctorId(), bill.getConsultationFee(), bill.getLabCharges(),
                    bill.getMedicineCharges(), bill.getTotalAmount(), bill.getBillDate(),
                    bill.isPaid());
            }
        }
    }

    /**
     * Read data from CSV file (generic method).
     * @param filePath the file path
     * @return list of lines from CSV
     */
    public static List<String> readCSV(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return new ArrayList<>();
        }
        return Files.readAllLines(path, StandardCharsets.UTF_8);
    }
}
