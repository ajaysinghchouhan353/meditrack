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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Clean entry point for MediTrack.
 * Supports interactive menu mode and --demo mode.
 */
public class Main {
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
                        System.out.println("Invalid option.");
                }
            } catch (Exception ex) {
                System.err.println("Operation failed: " + ex.getMessage());
            }

            System.out.println();
        }

        scanner.close();
        System.out.println("Goodbye from MediTrack.");
    }

    private static void printBanner() {
        System.out.println("========================================");
        System.out.println("MEDITRACK - Clinic Management System");
        System.out.println("========================================");
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
                System.out.println("Registered: " + doctor);
                break;
            case "2":
                List<Doctor> doctors = doctorService.getAllDoctors();
                if (doctors.isEmpty()) {
                    System.out.println("No doctors found.");
                } else {
                    doctors.forEach(System.out::println);
                }
                break;
            case "3":
                System.out.print("Doctor ID: ");
                String doctorId = scanner.nextLine();
                Optional<Doctor> d = doctorService.getDoctorById(doctorId);
                System.out.println(d.map(Object::toString).orElse("Doctor not found."));
                break;
            default:
                System.out.println("Invalid doctor menu option.");
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
                System.out.println("Registered: " + patient);
                break;
            case "2":
                List<Patient> patients = patientService.getAllPatients();
                if (patients.isEmpty()) {
                    System.out.println("No patients found.");
                } else {
                    patients.forEach(System.out::println);
                }
                break;
            case "3":
                System.out.print("Patient ID: ");
                String patientId = scanner.nextLine();
                Optional<Patient> p = patientService.getPatientById(patientId);
                System.out.println(p.map(Object::toString).orElse("Patient not found."));
                break;
            default:
                System.out.println("Invalid patient menu option.");
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
                System.out.println("Booked: " + appointment);
                break;
            case "2":
                List<Appointment> allAppointments = appointmentService.getAllAppointments();
                if (allAppointments.isEmpty()) {
                    System.out.println("No appointments found.");
                } else {
                    allAppointments.forEach(System.out::println);
                }
                break;
            case "3":
                System.out.print("Appointment ID: ");
                String appointmentId = scanner.nextLine();
                Optional<Appointment> ap = appointmentService.getAppointmentById(appointmentId);
                System.out.println(ap.map(Object::toString).orElse("Appointment not found."));
                break;
            case "4":
                System.out.print("Appointment ID to cancel: ");
                appointmentService.cancelAppointment(scanner.nextLine());
                System.out.println("Cancelled.");
                break;
            case "5":
                System.out.print("Appointment ID to complete: ");
                appointmentService.markAppointmentCompleted(scanner.nextLine());
                System.out.println("Marked completed.");
                break;
            default:
                System.out.println("Invalid appointment menu option.");
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
                System.out.println("Created: " + bill);
                break;
            case "2":
                List<Bill> bills = billService.getAllBills();
                if (bills.isEmpty()) {
                    System.out.println("No bills found.");
                } else {
                    bills.forEach(System.out::println);
                }
                break;
            case "3":
                System.out.print("Bill ID: ");
                String billId = scanner.nextLine();
                Optional<Bill> b = billService.getBillById(billId);
                System.out.println(b.map(Object::toString).orElse("Bill not found."));
                break;
            case "4":
                System.out.print("Bill ID to mark as paid: ");
                billService.markBillAsPaid(scanner.nextLine());
                System.out.println("Marked paid.");
                break;
            case "5":
                System.out.println("Total Revenue: " + String.format("%.2f", billService.calculateTotalRevenue()));
                break;
            default:
                System.out.println("Invalid billing menu option.");
        }
    }

    private static void runDemo() {
        System.out.println("\nRunning full demo...");
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

            System.out.println("Doctor registered: " + doctor.getId());
            System.out.println("Patient registered: " + patient.getId());
            System.out.println("Appointment booked: " + appointment.getId());
            System.out.println("Bill created: " + bill.getId() + ", amount=" + String.format("%.2f", bill.getTotalAmount()));

            System.out.println("Summary");
            System.out.println("Doctors: " + doctorService.getTotalDoctors());
            System.out.println("Patients: " + patientService.getTotalPatients());
            System.out.println("Appointments: " + appointmentService.getTotalAppointments());
            System.out.println("Bills: " + billService.getTotalBills());
        } catch (InvalidDataException | AppointmentNotFoundException ex) {
            System.err.println("Demo failed: " + ex.getMessage());
        }
    }
}
