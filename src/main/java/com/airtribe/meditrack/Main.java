package com.airtribe.meditrack;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.ConsoleNotificationListener;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.NotificationService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clean entry point for MediTrack.
 * Supports interactive menu mode and --demo mode.
 * Integrated with SLF4J for comprehensive logging.
 */
public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static BillService billService;
    private static NotificationService notificationService;
    private static Scanner scanner;

    public static void main(String[] args) {
        initializeServices();

        if (args.length > 0 && "--demo".equalsIgnoreCase(args[0])) {
            runDemo();
            return;
        }

        runMenu();
    }

    private static void initializeServices() {
        DataStore<Doctor> doctorStore = new DataStore<>();
        DataStore<Patient> patientStore = new DataStore<>();
        DataStore<Appointment> appointmentStore = new DataStore<>();
        DataStore<Bill> billStore = new DataStore<>();

        notificationService = new NotificationService();
        notificationService.registerListener(new ConsoleNotificationListener());

        doctorService = new DoctorService(doctorStore);
        patientService = new PatientService(patientStore);
        appointmentService = new AppointmentService(
            appointmentStore,
            doctorService,
            patientService,
            notificationService
        );
        billService = new BillService(billStore, appointmentService);
        scanner = new Scanner(System.in);
    }

    private static void runMenu() {
        boolean running = true;
        printBanner();

        while (running) {
            printMainMenu();
            String choice = scanner.nextLine().trim();

            try {
                switch (choice) {
                    case "1":
                        handleDoctors();
                        break;
                    case "2":
                        handlePatients();
                        break;
                    case "3":
                        handleAppointments();
                        break;
                    case "4":
                        handleBilling();
                        break;
                    case "5":
                        runDemo();
                        break;
                    case "0":
                        running = false;
                        break;
                    default:
                        logger.warn("Invalid option: " + choice);
                }
            } catch (Exception ex) {
                logger.error("Operation failed: " + ex.getMessage(), ex);
            }

            System.out.println();
        }

        scanner.close();
        logger.info("Application closed. Goodbye from MediTrack.");
    }

    private static void printBanner() {
        logger.info("========================================");
        logger.info("MEDITRACK - Clinic Management System");
        logger.info("========================================");
    }

    private static void printMainMenu() {
        System.out.println();
        System.out.println("1. Doctor Management");
        System.out.println("2. Patient Management");
        System.out.println("3. Appointment Management");
        System.out.println("4. Billing Management");
        System.out.println("5. Run Full Demo");
        System.out.println("0. Exit");
        System.out.print("Select option: ");
    }

    private static void handleDoctors() throws InvalidDataException {
        System.out.println("\nDoctor Menu: 1) Register 2) List 3) Search");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Phone: ");
                String phone = scanner.nextLine();
                System.out.print("Address: ");
                String address = scanner.nextLine();
                System.out.print("Specialty (CARDIOLOGY/NEUROLOGY/etc): ");
                String specialty = scanner.nextLine();
                System.out.print("License Number: ");
                String license = scanner.nextLine();
                System.out.print("Years of Experience: ");
                int years = Integer.parseInt(scanner.nextLine().trim());

                Doctor doctor = doctorService.registerDoctor(
                    name, email, phone, address, specialty, license, years
                );
                logger.info("Doctor registered: " + doctor.getId() + ", Name: " + name);
                break;
            case "2":
                List<Doctor> doctors = doctorService.getAllDoctors();
                if (doctors.isEmpty()) {
                    logger.info("No doctors found in the system.");
                } else {
                    doctors.forEach(d -> logger.info("Doctor: " + d));
                }
                break;
            case "3":
                System.out.print("Doctor ID: ");
                String doctorId = scanner.nextLine();
                Optional<Doctor> d = doctorService.getDoctorById(doctorId);
                if (d.isPresent()) {
                    logger.info("Doctor found: " + d.get());
                } else {
                    logger.warn("Doctor not found for ID: " + doctorId);
                }
                break;
            default:
                logger.warn("Invalid doctor menu option: " + choice);
        }
    }

    private static void handlePatients() throws InvalidDataException {
        System.out.println("\nPatient Menu: 1) Register 2) List 3) Search");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Name: ");
                String name = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Phone: ");
                String phone = scanner.nextLine();
                System.out.print("Address: ");
                String address = scanner.nextLine();
                System.out.print("DOB (YYYY-MM-DD): ");
                LocalDate dob = LocalDate.parse(scanner.nextLine().trim());
                System.out.print("Gender: ");
                String gender = scanner.nextLine();
                System.out.print("Blood Group: ");
                String bloodGroup = scanner.nextLine();

                Patient patient = patientService.registerPatient(
                    name, email, phone, address, dob, gender, bloodGroup
                );
                logger.info("Patient registered: " + patient.getId() + ", Name: " + name);
                break;
            case "2":
                List<Patient> patients = patientService.getAllPatients();
                if (patients.isEmpty()) {
                    logger.info("No patients found in the system.");
                } else {
                    patients.forEach(p -> logger.info("Patient: " + p));
                }
                break;
            case "3":
                System.out.print("Patient ID: ");
                String patientId = scanner.nextLine();
                Optional<Patient> p = patientService.getPatientById(patientId);
                if (p.isPresent()) {
                    logger.info("Patient found: " + p.get());
                } else {
                    logger.warn("Patient not found for ID: " + patientId);
                }
                break;
            default:
                logger.warn("Invalid patient menu option: " + choice);
        }
    }

    private static void handleAppointments() throws InvalidDataException, AppointmentNotFoundException {
        System.out.println("\nAppointment Menu: 1) Book 2) List 3) Search 4) Cancel 5) Complete");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Doctor ID: ");
                String doctorId = scanner.nextLine();
                System.out.print("Patient ID: ");
                String patientId = scanner.nextLine();
                System.out.print("DateTime (YYYY-MM-DDTHH:MM): ");
                LocalDateTime dateTime = LocalDateTime.parse(scanner.nextLine().trim());
                System.out.print("Reason: ");
                String reason = scanner.nextLine();
                Appointment appointment = appointmentService.bookAppointment(doctorId, patientId, dateTime, reason);
                logger.info("Appointment booked: " + appointment.getId() + " for patient " + patientId);
                break;
            case "2":
                List<Appointment> allAppointments = appointmentService.getAllAppointments();
                if (allAppointments.isEmpty()) {
                    logger.info("No appointments found in the system.");
                } else {
                    allAppointments.forEach(a -> logger.info("Appointment: " + a));
                }
                break;
            case "3":
                System.out.print("Appointment ID: ");
                String appointmentId = scanner.nextLine();
                Optional<Appointment> ap = appointmentService.getAppointmentById(appointmentId);
                if (ap.isPresent()) {
                    logger.info("Appointment found: " + ap.get());
                } else {
                    logger.warn("Appointment not found for ID: " + appointmentId);
                }
                break;
            case "4":
                System.out.print("Appointment ID to cancel: ");
                String cancelId = scanner.nextLine();
                appointmentService.cancelAppointment(cancelId);
                logger.info("Appointment cancelled: " + cancelId);
                break;
            case "5":
                System.out.print("Appointment ID to complete: ");
                String completeId = scanner.nextLine();
                appointmentService.markAppointmentCompleted(completeId);
                logger.info("Appointment marked completed: " + completeId);
                break;
            default:
                logger.warn("Invalid appointment menu option: " + choice);
        }
    }

    private static void handleBilling() throws InvalidDataException, AppointmentNotFoundException {
        System.out.println("\nBilling Menu: 1) Create 2) List 3) Search 4) Mark Paid 5) Revenue");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                System.out.print("Appointment ID: ");
                String appointmentId = scanner.nextLine();
                System.out.print("Consultation Fee: ");
                double fee = Double.parseDouble(scanner.nextLine().trim());
                System.out.print("Strategy (STANDARD/PREMIUM/DISCOUNTED/EMERGENCY): ");
                String strategy = scanner.nextLine();
                Bill bill = billService.createBill(appointmentId, fee, strategy);
                logger.info("Bill created: " + bill.getId() + " for appointment " + appointmentId + ", amount=" + String.format("%.2f", bill.getTotalAmount()));
                break;
            case "2":
                List<Bill> bills = billService.getAllBills();
                if (bills.isEmpty()) {
                    logger.info("No bills found in the system.");
                } else {
                    bills.forEach(bl -> logger.info("Bill: " + bl));
                }
                break;
            case "3":
                System.out.print("Bill ID: ");
                String billId = scanner.nextLine();
                Optional<Bill> b = billService.getBillById(billId);
                if (b.isPresent()) {
                    logger.info("Bill found: " + b.get());
                } else {
                    logger.warn("Bill not found for ID: " + billId);
                }
                break;
            case "4":
                System.out.print("Bill ID to mark as paid: ");
                String paidId = scanner.nextLine();
                billService.markBillAsPaid(paidId);
                logger.info("Bill marked as paid: " + paidId);
                break;
            case "5":
                logger.info("Total Revenue: " + String.format("%.2f", billService.calculateTotalRevenue()));
                break;
            default:
                logger.warn("Invalid billing menu option: " + choice);
        }
    }

    private static void runDemo() {
        logger.info("Starting full demo mode...");
        try {
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Rajesh Kumar", "rajesh@hospital.com", "9876543210", "Mumbai", "Cardiology", "LIC001", 15
            );
            Patient patient = patientService.registerPatient(
                "Ajay Chouhan", "ajay@email.com", "9123456789", "Bangalore",
                LocalDate.of(1990, 5, 15), "Male", "O+"
            );
            Appointment appointment = appointmentService.bookAppointment(
                doctor.getId(), patient.getId(), LocalDateTime.now().plusDays(2), "Routine checkup"
            );
            Bill bill = billService.createBill(appointment.getId(), 600.0, "STANDARD");

            logger.info("Demo Operation: Doctor registered with ID: " + doctor.getId());
            logger.info("Demo Operation: Patient registered with ID: " + patient.getId());
            logger.info("Demo Operation: Appointment booked with ID: " + appointment.getId());
            logger.info("Demo Operation: Bill created with ID: " + bill.getId() + ", amount=" + String.format("%.2f", bill.getTotalAmount()));

            logger.info("Demo Summary - Doctors: " + doctorService.getTotalDoctors());
            logger.info("Demo Summary - Patients: " + patientService.getTotalPatients());
            logger.info("Demo Summary - Appointments: " + appointmentService.getTotalAppointments());
            logger.info("Demo Summary - Bills: " + billService.getTotalBills());
        } catch (InvalidDataException | AppointmentNotFoundException ex) {
            logger.error("Demo failed with exception: " + ex.getMessage(), ex);
        }
    }
}
