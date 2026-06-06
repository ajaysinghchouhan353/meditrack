package com.airtribe.meditrack.test;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.entity.*;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.service.NotificationService;
import com.airtribe.meditrack.util.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * TestRunner for manual testing of MediTrack functionality.
 * This class runs various tests without using a testing framework.
 */
public class TestRunner {
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    
    private static int totalTests = 0;
    private static int passedTests = 0;
    private static int failedTests = 0;

    public static void main(String[] args) {
        initializeServices();
        runAllTests();
        printTestSummary();
    }

    private static void initializeServices() {
        DataStore<Doctor> doctorStore = new DataStore<>();
        DataStore<Patient> patientStore = new DataStore<>();
        DataStore<Appointment> appointmentStore = new DataStore<>();

        NotificationService notificationService = new NotificationService();
        
        doctorService = new DoctorService(doctorStore);
        patientService = new PatientService(patientStore);
        appointmentService = new AppointmentService(appointmentStore, doctorService, patientService, notificationService);
    }

    private static void runAllTests() {
        System.out.println("=====================================");
        System.out.println("  MEDITRACK - TEST RUNNER");
        System.out.println("=====================================\n");

        // Doctor Service Tests
        testDoctorRegistration();
        testDoctorRetrieval();
        testGetDoctorsBySpecialty();

        // Patient Service Tests
        testPatientRegistration();
        testPatientRetrieval();
        testGetPatientsByBloodGroup();

        // Appointment Service Tests
        testAppointmentBooking();
        testAppointmentRetrieval();
        testCancelAppointment();

        // Additional Appointment Tests (updated to match current implementation)
        testMarkAppointmentCompleted();
        testAddAppointmentNotes();
        testGetAppointmentsByStatus();

        // Utility Tests
        testValidation();
        testIdGenerator();
        testDateUtil();
    }

    private static void testDoctorRegistration() {
        test("Doctor Registration", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Test", "test@hospital.com", "9999999999",
                "Test City", "General", "LIC100", 10
            );
            return doctor != null && doctor.getId().startsWith("DOC");
        });
    }

    private static void testDoctorRetrieval() {
        test("Doctor Retrieval", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Retrieve", "retrieve@hospital.com", "8888888888",
                "Test City", "Cardiology", "LIC101", 8
            );
            Optional<Doctor> retrieved = doctorService.getDoctorById(doctor.getId());
            return retrieved.isPresent() && retrieved.get().getName().equals("Dr. Retrieve");
        });
    }

    private static void testGetDoctorsBySpecialty() {
        test("Get Doctors by Specialty", () -> {
            doctorService.registerDoctor(
                "Dr. Cardio", "cardio@hospital.com", "7777777777",
                "Test City", "Cardiology", "LIC102", 5
            );
            List<Doctor> cardio = doctorService.getDoctorsBySpecialty("Cardiology");
            return cardio.size() >= 1;
        });
    }

    private static void testPatientRegistration() {
        test("Patient Registration", () -> {
            Patient patient = patientService.registerPatient(
                "Test Patient", "patient@email.com", "9111111111",
                "Test City", LocalDate.of(1990, 1, 1), "Male", "O+"
            );
            return patient != null && patient.getId().startsWith("PAT");
        });
    }

    private static void testPatientRetrieval() {
        test("Patient Retrieval", () -> {
            Patient patient = patientService.registerPatient(
                "Retrieve Patient", "retrieve@email.com", "9222222222",
                "Test City", LocalDate.of(1995, 5, 5), "Female", "B+"
            );
            Optional<Patient> retrieved = patientService.getPatientById(patient.getId());
            return retrieved.isPresent() && retrieved.get().getName().equals("Retrieve Patient");
        });
    }

    private static void testGetPatientsByBloodGroup() {
        test("Get Patients by Blood Group", () -> {
            patientService.registerPatient(
                "Blood Type Test", "blood@email.com", "9333333333",
                "Test City", LocalDate.of(1998, 8, 8), "Male", "A+"
            );
            List<Patient> aPositive = patientService.getPatientsByBloodGroup("A+");
            return aPositive.size() >= 1;
        });
    }

    private static void testAppointmentBooking() {
        test("Appointment Booking", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Appointment", "apt@hospital.com", "9444444444",
                "Test City", "General", "LIC103", 7
            );
            Patient patient = patientService.registerPatient(
                "Apt Patient", "aptpatient@email.com", "9555555555",
                "Test City", LocalDate.of(2000, 1, 1), "Male", "O+"
            );
            Appointment apt = appointmentService.bookAppointment(
                doctor.getId(), patient.getId(),
                futureAppointmentTime(2, 10, 0),
                "Test appointment"
            );
            return apt != null && apt.getId().startsWith("APT");
        });
    }

    private static void testAppointmentRetrieval() {
        test("Appointment Retrieval", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. AptRetrieve", "aptret@hospital.com", "9666666666",
                "Test City", "General", "LIC104", 6
            );
            Patient patient = patientService.registerPatient(
                "AptRetrieve Patient", "aptretrieve@email.com", "9777777777",
                "Test City", LocalDate.of(2001, 2, 2), "Female", "AB+"
            );
            Appointment apt = appointmentService.bookAppointment(
                doctor.getId(), patient.getId(),
                futureAppointmentTime(3, 14, 30),
                "Retrieval test"
            );
            Optional<Appointment> retrieved = appointmentService.getAppointmentById(apt.getId());
            return retrieved.isPresent();
        });
    }

    private static void testCancelAppointment() {
        test("Cancel Appointment", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Cancel", "cancel@hospital.com", "9888888888",
                "Test City", "General", "LIC105", 4
            );
            Patient patient = patientService.registerPatient(
                "Cancel Patient", "cancel@email.com", "9111111112",
                "Test City", LocalDate.of(2002, 3, 3), "Male", "A-"
            );
            Appointment apt = appointmentService.bookAppointment(
                doctor.getId(), patient.getId(),
                futureAppointmentTime(4, 11, 0),
                "Cancel test"
            );
            appointmentService.cancelAppointment(apt.getId());
            Optional<Appointment> cancelled = appointmentService.getAppointmentById(apt.getId());
            return cancelled.isPresent() && cancelled.get().getStatus().equals(AppointmentStatus.CANCELLED);
        });
    }

    private static LocalDateTime futureAppointmentTime(int daysAhead, int hour, int minute) {
        return LocalDateTime.now()
            .plusDays(daysAhead)
            .withHour(hour)
            .withMinute(minute)
            .withSecond(0)
            .withNano(0);
    }

    private static void testMarkAppointmentCompleted() {
        test("Mark Appointment Completed", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Complete", "complete@hospital.com", "9000000000",
                "Test City", "General", "LIC200", 3
            );
            Patient patient = patientService.registerPatient(
                "Complete Patient", "completepatient@email.com", "9110000000",
                "Test City", LocalDate.of(1999, 9, 9), "Male", "O+"
            );
            Appointment apt = appointmentService.bookAppointment(
                doctor.getId(), patient.getId(), futureAppointmentTime(1, 9, 0), "Complete test"
            );
            boolean updated = appointmentService.markAppointmentCompleted(apt.getId());
            Optional<Appointment> retrieved = appointmentService.getAppointmentById(apt.getId());
            return updated && retrieved.isPresent() && retrieved.get().getStatus().equals(AppointmentStatus.COMPLETED);
        });
    }

    private static void testAddAppointmentNotes() {
        test("Add Appointment Notes", () -> {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Notes", "notes@hospital.com", "9220000000",
                "Test City", "General", "LIC201", 2
            );
            Patient patient = patientService.registerPatient(
                "Notes Patient", "notespatient@email.com", "9330000000",
                "Test City", LocalDate.of(1992, 2, 2), "Female", "A+"
            );
            Appointment apt = appointmentService.bookAppointment(
                doctor.getId(), patient.getId(), futureAppointmentTime(2, 15, 0), "Notes test"
            );
            boolean updated = appointmentService.addAppointmentNotes(apt.getId(), "Patient showed symptoms");
            Optional<Appointment> retrieved = appointmentService.getAppointmentById(apt.getId());
            return updated && retrieved.isPresent() && retrieved.get().getNotes() != null && !retrieved.get().getNotes().isEmpty();
        });
    }

    private static void testGetAppointmentsByStatus() {
        test("Get Appointments by Status", () -> {
            // Create confirmed and cancelled appointments
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Status", "status@hospital.com", "9440000000",
                "Test City", "General", "LIC202", 1
            );
            Patient patient = patientService.registerPatient(
                "Status Patient", "statuspatient@email.com", "9550000000",
                "Test City", LocalDate.of(1990, 4, 4), "Male", "B+"
            );
            Appointment apt1 = appointmentService.bookAppointment(doctor.getId(), patient.getId(), futureAppointmentTime(3, 10, 0), "Status1");
            Appointment apt2 = appointmentService.bookAppointment(doctor.getId(), patient.getId(), futureAppointmentTime(4, 11, 0), "Status2");
            // Cancel one
            appointmentService.cancelAppointment(apt2.getId());
            // Mark first as completed
            appointmentService.markAppointmentCompleted(apt1.getId());
            // Verify
            int completed = appointmentService.getAppointmentsByStatus(AppointmentStatus.COMPLETED).size();
            int cancelled = appointmentService.getAppointmentsByStatus(AppointmentStatus.CANCELLED).size();
            return completed >= 1 && cancelled >= 1;
        });
    }

    private static void testValidation() {
        test("Email Validation", () -> Validator.isValidEmail("test@email.com"));
        test("Phone Validation", () -> Validator.isValidPhoneNumber("9876543210"));
        test("Not Empty Validation", () -> Validator.isNotEmpty("valid"));
    }

    private static void testIdGenerator() {
        test("Doctor ID Generation", () -> {
            IdGenerator.resetCounters();
            String id = IdGenerator.generateDoctorId();
            return id.startsWith("DOC") && id.length() > 3;
        });
    }

    private static void testDateUtil() {
        test("Date Formatting", () -> {
            LocalDate date = LocalDate.of(2026, 3, 5);
            String formatted = DateUtil.formatDate(date);
            return formatted.equals("2026-03-05");
        });

        test("Date Parsing", () -> {
            LocalDate parsed = DateUtil.parseDate("2026-03-05");
            return parsed.getYear() == 2026 && parsed.getMonthValue() == 3;
        });
    }

    private static void test(String testName, TestCondition condition) {
        totalTests++;
        try {
            boolean result = condition.evaluate();
            if (result) {
                System.out.println("✓ PASS: " + testName);
                passedTests++;
            } else {
                System.out.println("✗ FAIL: " + testName);
                failedTests++;
            }
        } catch (Exception e) {
            System.out.println("✗ ERROR: " + testName + " - " + e.getMessage());
            failedTests++;
        }
    }

    private static void printTestSummary() {
        System.out.println("\n=====================================");
        System.out.println("  TEST SUMMARY");
        System.out.println("=====================================");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Passed: " + passedTests);
        System.out.println("Failed: " + failedTests);
        System.out.println("Success Rate: " + (totalTests > 0 ? 
            (passedTests * 100 / totalTests) + "%" : "0%"));
        System.out.println("=====================================");
    }

    /**
     * Functional interface for test conditions.
     */
    @FunctionalInterface
    interface TestCondition {
        boolean evaluate() throws Exception;
    }
}
