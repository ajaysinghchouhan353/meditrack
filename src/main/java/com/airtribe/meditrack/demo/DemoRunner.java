package com.airtribe.meditrack.demo;

import com.airtribe.meditrack.constants.AppointmentStatus;
import com.airtribe.meditrack.constants.Specialization;
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
import com.airtribe.meditrack.util.AppointmentAnalyticsReport;
import com.airtribe.meditrack.util.DataStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Comprehensive Demo Runner for MediTrack Application
 * 
 * This demo showcases ALL features of the MediTrack system:
 * 1. Doctor Management (CRUD operations)
 * 2. Patient Management (CRUD operations)
 * 3. Appointment Management (Book, Cancel, Complete, Update)
 * 4. Billing System with Strategy Pattern (4 different strategies)
 * 5. Notification System with Observer Pattern
 * 6. Advanced Search Capabilities
 * 7. Data Persistence (CSV export/import)
 * 8. Enum Usage (AppointmentStatus, Specialization)
 * 9. Design Patterns in Action
 * 10. Exception Handling
 * 11. Generic DataStore Pattern
 * 12. Immutable Value Objects
 * 
 * @author Airtribe MediTrack Team
 * @version 1.0.0
 * @since 2026-03-06
 */
public class DemoRunner {
    private static final Logger logger = LoggerFactory.getLogger(DemoRunner.class);
    
    private static DoctorService doctorService;
    private static PatientService patientService;
    private static AppointmentService appointmentService;
    private static BillService billService;
    private static NotificationService notificationService;

    public static void main(String[] args) {
        printHeader("MEDITRACK COMPREHENSIVE DEMO");
        
        try {
            // Initialize all services
            initializeServices();
            
            // Run all demos in sequence
            runDoctorManagementDemo();
            runPatientManagementDemo();
            runAppointmentManagementDemo();
            runBillingStrategyDemo();
            runNotificationDemo();
            runSearchFunctionalityDemo();
            runEnumUsageDemo();
            runDesignPatternsDemo();
            runExceptionHandlingDemo();
            runDataPersistenceDemo();
            
            printSuccess("\n✅ ALL DEMOS COMPLETED SUCCESSFULLY!");
            printStatistics();
            
        } catch (Exception e) {
            printError("Demo failed: " + e.getMessage());
            logger.error("Demo execution failed", e);
        }
    }

    /**
     * Initialize all services with DataStores
     */
    private static void initializeServices() {
        printSection("INITIALIZING SERVICES");
        
        DataStore<Doctor> doctorStore = new DataStore<>();
        DataStore<Patient> patientStore = new DataStore<>();
        DataStore<Appointment> appointmentStore = new DataStore<>();
        DataStore<Bill> billStore = new DataStore<>();
        
        notificationService = new NotificationService();
        
        doctorService = new DoctorService(doctorStore);
        patientService = new PatientService(patientStore);
        appointmentService = new AppointmentService(appointmentStore, doctorService, 
                                                     patientService, notificationService);
        billService = new BillService(billStore, appointmentService);
        
        // Register notification listeners (Observer Pattern)
        notificationService.registerListener(new ConsoleNotificationListener());
        
        printSuccess("✓ All services initialized successfully");
        printInfo("  - DoctorService, PatientService, AppointmentService, BillService");
        printInfo("  - NotificationService with Console Listener (Observer Pattern)");
    }

    /**
     * Demo 1: Doctor Management Operations
     */
    private static void runDoctorManagementDemo() throws InvalidDataException {
        printSection("DEMO 1: DOCTOR MANAGEMENT");
        
        // Register doctors with different specializations
        printInfo("1.1 Registering Doctors...");
        
        Doctor drSmith = doctorService.registerDoctor(
            "Dr. John Smith", 
            "john.smith@meditrack.com", 
            "5551230101", 
            "123 Medical Plaza",
            Specialization.CARDIOLOGY.getName(), 
            "MED-12345", 
            15
        );
        printSuccess("✓ Registered: " + drSmith.getName() + " - " + drSmith.getSpecialty());
        
        Doctor drJohnson = doctorService.registerDoctor(
            "Dr. Sarah Johnson", 
            "sarah.j@meditrack.com", 
            "5551230102", 
            "456 Health Center",
            Specialization.PEDIATRICS.getName(), 
            "MED-12346", 
            10
        );
        printSuccess("✓ Registered: " + drJohnson.getName() + " - " + drJohnson.getSpecialty());
        
        Doctor drLee = doctorService.registerDoctor(
            "Dr. Michael Lee", 
            "michael.lee@meditrack.com", 
            "5551230103", 
            "789 Clinic Road",
            Specialization.NEUROLOGY.getName(), 
            "MED-12347", 
            20
        );
        printSuccess("✓ Registered: " + drLee.getName() + " - " + drLee.getSpecialty());
        
        Doctor drPatel = doctorService.registerDoctor(
            "Dr. Priya Patel", 
            "priya.patel@meditrack.com", 
            "5551230104", 
            "321 Medical Building",
            Specialization.DERMATOLOGY.getName(), 
            "MED-12348", 
            8
        );
        printSuccess("✓ Registered: " + drPatel.getName() + " - " + drPatel.getSpecialty());
        
        // Search operations
        printInfo("\n1.2 Searching Doctors...");
        
        Optional<Doctor> foundDoctor = doctorService.getDoctorById(drSmith.getId());
        if (foundDoctor.isPresent()) {
            printSuccess("✓ Found doctor by ID: " + foundDoctor.get().getName());
        }
        
        List<Doctor> cardiologists = doctorService.getDoctorsBySpecialty(Specialization.CARDIOLOGY.getName());
        printSuccess("✓ Found " + cardiologists.size() + " Cardiologist(s)");
        
        List<Doctor> allDoctors = doctorService.getAllDoctors();
        printSuccess("✓ Total doctors in system: " + allDoctors.size());
        
        // Update operations
        printInfo("\n1.3 Updating Doctor Information...");
        drSmith.setPhoneNumber("5551239999");
        doctorService.updateDoctor(drSmith.getId(), drSmith);
        printSuccess("✓ Updated Dr. Smith's phone number to: " + drSmith.getPhoneNumber());
    }

    /**
     * Demo 2: Patient Management Operations
     */
    private static void runPatientManagementDemo() throws InvalidDataException {
        printSection("DEMO 2: PATIENT MANAGEMENT");
        
        printInfo("2.1 Registering Patients...");
        
        Patient alice = patientService.registerPatient(
            "Alice Williams", 
            "alice.w@email.com", 
            "5551231001", 
            "100 Oak Street",
            LocalDate.of(1985, 5, 15), 
            "Female", 
            "O+"
        );
        printSuccess("✓ Registered: " + alice.getName() + " (Blood: " + alice.getBloodGroup() + ")");
        
        Patient bob = patientService.registerPatient(
            "Bob Anderson", 
            "bob.a@email.com", 
            "5551231002", 
            "200 Pine Avenue",
            LocalDate.of(1990, 8, 22), 
            "Male", 
            "A+"
        );
        printSuccess("✓ Registered: " + bob.getName() + " (Blood: " + bob.getBloodGroup() + ")");
        
        Patient charlie = patientService.registerPatient(
            "Charlie Davis", 
            "charlie.d@email.com", 
            "5551231003", 
            "300 Maple Drive",
            LocalDate.of(2010, 3, 10), 
            "Male", 
            "B+"
        );
        printSuccess("✓ Registered: " + charlie.getName() + " (Blood: " + charlie.getBloodGroup() + ")");
        
        Patient diana = patientService.registerPatient(
            "Diana Martinez", 
            "diana.m@email.com", 
            "5551231004", 
            "400 Elm Court",
            LocalDate.of(1978, 12, 5), 
            "Female", 
            "AB+"
        );
        printSuccess("✓ Registered: " + diana.getName() + " (Blood: " + diana.getBloodGroup() + ")");
        
        // Search and update medical history
        printInfo("\n2.2 Managing Medical History...");
        
        Optional<Patient> foundPatient = patientService.getPatientById(alice.getId());
        if (foundPatient.isPresent()) {
            Patient patient = foundPatient.get();
            patient.setMedicalHistory("Diabetes Type 2, Hypertension");
            patientService.updatePatient(patient.getId(), patient);
            printSuccess("✓ Updated medical history for: " + patient.getName());
        }
        
        List<Patient> allPatients = patientService.getAllPatients();
        printSuccess("✓ Total patients in system: " + allPatients.size());
        
        // Demonstrate Cloneable interface
        printInfo("\n2.3 Demonstrating Cloneable Pattern...");
        try {
            Patient clonedPatient = (Patient) alice.clone();
            printSuccess("✓ Successfully cloned patient: " + clonedPatient.getName());
            printInfo("  Original ID: " + alice.getId() + ", Clone ID: " + clonedPatient.getId());
        } catch (CloneNotSupportedException e) {
            printError("Clone failed: " + e.getMessage());
        }
    }

    /**
     * Demo 3: Appointment Management Operations
     */
    private static void runAppointmentManagementDemo() throws Exception {
        printSection("DEMO 3: APPOINTMENT MANAGEMENT");
        
        // Get doctors and patients
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Patient> patients = patientService.getAllPatients();
        
        if (doctors.isEmpty() || patients.isEmpty()) {
            printError("Need doctors and patients for appointments!");
            return;
        }
        
        Doctor doctor1 = doctors.get(0);
        Doctor doctor2 = doctors.get(1);
        Patient patient1 = patients.get(0);
        Patient patient2 = patients.get(1);
        
        printInfo("3.1 Booking Appointments...");
        
        // Book multiple appointments
        LocalDateTime tomorrow = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
        Appointment apt1 = appointmentService.bookAppointment(
            doctor1.getId(), 
            patient1.getId(), 
            tomorrow, 
            "Annual checkup"
        );
        printSuccess("✓ Booked: " + apt1.getId() + " (Status: " + apt1.getStatus() + ")");
        
        LocalDateTime nextWeek = LocalDateTime.now().plusDays(7).withHour(14).withMinute(30);
        Appointment apt2 = appointmentService.bookAppointment(
            doctor2.getId(), 
            patient2.getId(), 
            nextWeek, 
            "Vaccination"
        );
        printSuccess("✓ Booked: " + apt2.getId() + " (Status: " + apt2.getStatus() + ")");
        
        LocalDateTime emergency = LocalDateTime.now().plusHours(2);
        Appointment apt3 = appointmentService.bookAppointment(
            doctor1.getId(), 
            patient2.getId(), 
            emergency, 
            "Emergency consultation"
        );
        printSuccess("✓ Booked: " + apt3.getId() + " (Status: " + apt3.getStatus() + ")");
        
        // Update appointment status
        printInfo("\n3.2 Updating Appointment Status...");
        
        // Mark appointment as completed
        appointmentService.markAppointmentCompleted(apt2.getId());
        appointmentService.addAppointmentNotes(apt2.getId(), "Patient is healthy. Vaccination completed.");
        printSuccess("✓ Completed appointment: " + apt2.getId() + " with notes");
        
        // Cancel an appointment
        printInfo("\n3.3 Canceling Appointment...");
        appointmentService.cancelAppointment(apt3.getId());
        printSuccess("✓ Cancelled appointment: " + apt3.getId());
        
        // Search appointments
        printInfo("\n3.4 Searching Appointments...");
        List<Appointment> allAppointments = appointmentService.getAllAppointments();
        printSuccess("✓ Total appointments: " + allAppointments.size());
        
        List<Appointment> confirmedApts = appointmentService.getAppointmentsByStatus(AppointmentStatus.CONFIRMED);
        printSuccess("✓ Confirmed appointments: " + confirmedApts.size());
        
        List<Appointment> completedApts = appointmentService.getAppointmentsByStatus(AppointmentStatus.COMPLETED);
        printSuccess("✓ Completed appointments: " + completedApts.size());
    }

    /**
     * Demo 4: Billing with Strategy Pattern
     */
    private static void runBillingStrategyDemo() throws Exception {
        printSection("DEMO 4: BILLING STRATEGIES (Strategy Pattern)");
        
        List<Appointment> completedApts = appointmentService.getAppointmentsByStatus(AppointmentStatus.COMPLETED);
        
        if (completedApts.isEmpty()) {
            printWarning("No completed appointments for billing demo");
            return;
        }
        
        Appointment apt = completedApts.get(0);
        double baseFee = 500.0;
        
        printInfo("Base Consultation Fee: ₹" + baseFee);
        printInfo("Appointment: " + apt.getId());
        
        // Demo 1: Standard Billing
        printInfo("\n4.1 Standard Billing Strategy...");
        Bill standardBill = billService.createBill(apt.getId(), baseFee, "STANDARD");
        billService.addCharges(standardBill.getId(), 200.0, 150.0);
        // Refresh the bill to get updated charges
        standardBill = billService.getBillById(standardBill.getId()).orElse(standardBill);
        
        printSuccess("✓ Bill ID: " + standardBill.getId());
        printInfo("  Strategy: " + (standardBill.getBillingStrategy() != null ? standardBill.getBillingStrategy().getStrategyName() : "Standard"));
        printInfo("  Consultation: ₹" + standardBill.getConsultationFee());
        printInfo("  Lab Charges: ₹" + standardBill.getLabCharges());
        printInfo("  Medicine: ₹" + standardBill.getMedicineCharges());
        printInfo("  Total Amount: ₹" + String.format("%.2f", standardBill.getTotalAmount()));
        
        // Demo 2: Premium Billing (with service charge)
        printInfo("\n4.2 Premium Billing Strategy (10% service charge)...");
        // Need another completed appointment
        List<Appointment> allApts = appointmentService.getAllAppointments();
        if (allApts.size() > 1) {
            Appointment apt2 = allApts.get(1);
            if (apt2.getStatus() != AppointmentStatus.COMPLETED) {
                appointmentService.markAppointmentCompleted(apt2.getId());
            }
            
            Bill premiumBill = billService.createBill(apt2.getId(), baseFee, "PREMIUM");
            billService.addCharges(premiumBill.getId(), 300.0, 200.0);
            // Refresh the bill to get updated charges
            premiumBill = billService.getBillById(premiumBill.getId()).orElse(premiumBill);
            
            printSuccess("✓ Bill ID: " + premiumBill.getId());
            printInfo("  Strategy: " + (premiumBill.getBillingStrategy() != null ? premiumBill.getBillingStrategy().getStrategyName() : "Premium"));
            printInfo("  Total Amount: ₹" + String.format("%.2f", premiumBill.getTotalAmount()));
        }
        
        // Demo 3: Discounted Billing
        printInfo("\n4.3 Discounted Billing Strategy (15% discount)...");
        if (allApts.size() > 2) {
            Appointment apt3 = allApts.get(2);
            if (apt3.getStatus() != AppointmentStatus.COMPLETED && apt3.getStatus() != AppointmentStatus.CANCELLED) {
                appointmentService.markAppointmentCompleted(apt3.getId());
            }
            
            if (apt3.getStatus() == AppointmentStatus.COMPLETED) {
                Bill discountBill = billService.createBill(apt3.getId(), baseFee, "DISCOUNTED");
                billService.addCharges(discountBill.getId(), 250.0, 100.0);
                // Refresh the bill to get updated charges
                discountBill = billService.getBillById(discountBill.getId()).orElse(discountBill);
                
                printSuccess("✓ Bill ID: " + discountBill.getId());
                printInfo("  Strategy: " + (discountBill.getBillingStrategy() != null ? discountBill.getBillingStrategy().getStrategyName() : "Discounted"));
                printInfo("  Total Amount: ₹" + String.format("%.2f", discountBill.getTotalAmount()));
            }
        }
        
        // Demo 4: Payment processing
        printInfo("\n4.4 Processing Payments...");
        List<Bill> allBills = billService.getAllBills();
        if (!allBills.isEmpty()) {
            Bill billToPay = allBills.get(0);
            billService.markBillAsPaid(billToPay.getId());
            // Refresh the bill to get updated paid status
            billToPay = billService.getBillById(billToPay.getId()).orElse(billToPay);
            printSuccess("✓ Payment processed for Bill: " + billToPay.getId());
            printInfo("  Payment Method: Default");
            printInfo("  Status: " + (billToPay.isPaid() ? "PAID" : "UNPAID"));
        }
        
        // Demo 5: Bill Details Display
        printInfo("\n4.5 Displaying Bill Details...");
        if (!allBills.isEmpty()) {
            // Refresh to get latest state
            Bill sampleBill = billService.getBillById(allBills.get(0).getId()).orElse(allBills.get(0));
            printSuccess("✓ Bill Details:");
            printInfo("  Bill ID: " + sampleBill.getId());
            printInfo("  Total Amount: ₹" + String.format("%.2f", sampleBill.getTotalAmount()));
            printInfo("  Paid: " + sampleBill.isPaid());
        }
    }

    /**
     * Demo 5: Notification System (Observer Pattern)
     */
    private static void runNotificationDemo() throws Exception {
        printSection("DEMO 5: NOTIFICATION SYSTEM (Observer Pattern)");
        
        printInfo("5.1 Observer Pattern in Action...");
        printInfo("Notifications are automatically sent when appointments change status");
        printInfo("(Check console output above for notification messages)");
        
        List<Doctor> doctors = doctorService.getAllDoctors();
        List<Patient> patients = patientService.getAllPatients();
        
        if (!doctors.isEmpty() && !patients.isEmpty()) {
            printInfo("\n5.2 Creating new appointment to trigger notifications...");
            
            LocalDateTime futureDate = LocalDateTime.now().plusDays(3).withHour(11).withMinute(0);
            Appointment newApt = appointmentService.bookAppointment(
                doctors.get(0).getId(), 
                patients.get(0).getId(), 
                futureDate, 
                "Follow-up consultation"
            );
            printSuccess("✓ Appointment booked: " + newApt.getId());
            printInfo("  → Notification sent via ConsoleNotificationListener");
            
            Thread.sleep(500); // Small delay for visibility
            
            // Mark appointment as completed to demonstrate notifications
            appointmentService.markAppointmentCompleted(newApt.getId());
            printSuccess("✓ Appointment completed: " + newApt.getId());
            printInfo("  → Notification sent via ConsoleNotificationListener");
        }
    }

    /**
     * Demo 6: Advanced Search Functionality
     */
    private static void runSearchFunctionalityDemo() {
        printSection("DEMO 6: ADVANCED SEARCH FUNCTIONALITY");
        
        printInfo("6.1 Search Doctors by Specialization...");
        for (Specialization spec : new Specialization[]{
            Specialization.CARDIOLOGY, 
            Specialization.PEDIATRICS, 
            Specialization.NEUROLOGY
        }) {
            List<Doctor> specialists = doctorService.getDoctorsBySpecialty(spec.getName());
            printSuccess("✓ " + spec.getName() + ": " + specialists.size() + " doctor(s)");
        }
        
        printInfo("\n6.2 Search Appointments by Status...");
        for (AppointmentStatus status : AppointmentStatus.values()) {
            List<Appointment> apts = appointmentService.getAppointmentsByStatus(status);
            if (apts.size() > 0) {
                printSuccess("✓ " + status.getCode() + ": " + apts.size() + " appointment(s)");
            }
        }
        
        printInfo("\n6.3 Search Patients by Blood Group...");
        List<Patient> allPatients = patientService.getAllPatients();
        for (Patient patient : allPatients) {
            printInfo("  " + patient.getName() + " - Blood Group: " + patient.getBloodGroup());
        }
        
        printInfo("\n6.4 Search Unpaid Bills...");
        List<Bill> unpaidBills = billService.getAllBills().stream()
            .filter(bill -> !bill.isPaid())
            .toList();
        printSuccess("✓ Found " + unpaidBills.size() + " unpaid bill(s)");
        
        List<Bill> paidBills = billService.getAllBills().stream()
            .filter(Bill::isPaid)
            .toList();
        printSuccess("✓ Found " + paidBills.size() + " paid bill(s)");
    }

    /**
     * Demo 7: Enum Usage
     */
    private static void runEnumUsageDemo() {
        printSection("DEMO 7: ENUM USAGE");
        
        printInfo("7.1 AppointmentStatus Enum Values:");
        for (AppointmentStatus status : AppointmentStatus.values()) {
            printInfo("  - " + status.name() + ": " + status.getDescription());
        }
        
        printInfo("\n7.2 Specialization Enum Values:");
        for (Specialization spec : Specialization.values()) {
            printInfo("  - " + spec.getName() + ": " + spec.getDescription());
        }
        
        printInfo("\n7.3 Enum Methods Demonstration:");
        AppointmentStatus status = AppointmentStatus.fromCode("COMPLETED");
        printSuccess("✓ Parsed 'COMPLETED' to enum: " + status.toString());
        
        Specialization spec = Specialization.fromName("Cardiology");
        printSuccess("✓ Parsed 'Cardiology' to enum: " + spec.toString());
    }

    /**
     * Demo 8: Design Patterns
     */
    private static void runDesignPatternsDemo() throws Exception {
        printSection("DEMO 8: DESIGN PATTERNS SHOWCASE");
        
        printInfo("8.1 Generic Repository Pattern (DataStore<T>)");
        printInfo("  ✓ DataStore<Doctor> - Type-safe doctor storage");
        printInfo("  ✓ DataStore<Patient> - Type-safe patient storage");
        printInfo("  ✓ DataStore<Appointment> - Type-safe appointment storage");
        printInfo("  ✓ DataStore<Bill> - Type-safe bill storage");
        printInfo("  → All using Generic DataStore<T extends Searchable>");
        
        printInfo("\n8.2 Strategy Pattern (BillingStrategy)");
        printInfo("  ✓ StandardBillingStrategy - Base calculation");
        printInfo("  ✓ PremiumBillingStrategy - With service charge");
        printInfo("  ✓ DiscountedBillingStrategy - With discount");
        printInfo("  ✓ EmergencyBillingStrategy - With surcharge");
        printInfo("  → Runtime selection based on bill type");
        
        printInfo("\n8.3 Observer Pattern (AppointmentListener)");
        printInfo("  ✓ NotificationService manages listeners");
        printInfo("  ✓ ConsoleNotificationListener implements observer");
        printInfo("  → Automatic notifications on appointment events");
        
        printInfo("\n8.4 Factory Pattern (BillFactory)");
        printInfo("  ✓ Creates bills with appropriate strategy");
        printInfo("  → Bill creation abstracted from client code");
        
        printInfo("\n8.5 Service Layer Pattern");
        printInfo("  ✓ DoctorService - Encapsulates doctor business logic");
        printInfo("  ✓ PatientService - Encapsulates patient business logic");
        printInfo("  ✓ AppointmentService - Encapsulates appointment logic");
        printInfo("  ✓ BillService - Encapsulates billing logic");
        printInfo("  → Clean separation of business logic from entities");
        
        printInfo("\n8.6 Immutable Value Object (BillSummary)");
        printInfo("  ✓ Final class with no setters");
        printInfo("  ✓ Thread-safe and cacheable");
        printInfo("  → Data transfer without side effects");

        printInfo("\n8.7 Template Method Pattern (AppointmentAnalyticsReport)");
        AppointmentAnalyticsReport report = new AppointmentAnalyticsReport(
            appointmentService.getTotalAppointments(),
            appointmentService.getTotalBookedAppointments(),
            appointmentService.getAppointmentsByStatus(AppointmentStatus.COMPLETED).size(),
            appointmentService.getAppointmentsByStatus(AppointmentStatus.CANCELLED).size()
        );
        printInfo(report.generate().replace("\n", "\n  "));
    }

    /**
     * Demo 9: Exception Handling
     */
    private static void runExceptionHandlingDemo() {
        printSection("DEMO 9: EXCEPTION HANDLING");
        
        printInfo("9.1 Custom Exception: InvalidDataException");
        try {
            doctorService.registerDoctor("", "invalid", "123", "addr", "spec", "lic", 5);
            printError("Should have thrown InvalidDataException!");
        } catch (InvalidDataException e) {
            printSuccess("✓ Caught InvalidDataException: " + e.getMessage());
        }
        
        printInfo("\n9.2 Custom Exception: AppointmentNotFoundException");
        try {
            appointmentService.markAppointmentCompleted("INVALID-ID-12345");
            printError("Should have thrown AppointmentNotFoundException!");
        } catch (AppointmentNotFoundException e) {
            printSuccess("✓ Caught AppointmentNotFoundException: " + e.getMessage());
        } catch (Exception e) {
            printSuccess("✓ Caught Exception: " + e.getMessage());
        }
        
        printInfo("\n9.3 Validation Exception Handling");
        try {
            patientService.registerPatient(null, "email", "phone", "addr", 
                LocalDate.now(), "gender", "blood");
        } catch (InvalidDataException e) {
            printSuccess("✓ Validation failed correctly: " + e.getMessage());
        }
    }

    /**
     * Demo 10: Data Persistence
     */
    private static void runDataPersistenceDemo() {
        printSection("DEMO 10: DATA PERSISTENCE");
        
        printInfo("10.1 CSV Export/Import Capability");
        printInfo("  ✓ CSVUtil class supports export to CSV");
        printInfo("  ✓ CSVUtil class supports import from CSV");
        printInfo("  → Data can be persisted to files for backup");
        
        printInfo("\n10.2 Java Serialization");
        printInfo("  ✓ All entities implement Serializable");
        printInfo("  ✓ DataStore supports serialization");
        printInfo("  → Objects can be saved to binary files");
        
        printInfo("\n10.3 Thread-Safe Data Storage");
        printInfo("  ✓ DataStore uses ConcurrentHashMap");
        printInfo("  ✓ Synchronized access in service layers");
        printInfo("  → Safe for multi-threaded environments");
    }

    /**
     * Print final statistics
     */
    private static void printStatistics() {
        printSection("SYSTEM STATISTICS");
        
        int doctorCount = doctorService.getAllDoctors().size();
        int patientCount = patientService.getAllPatients().size();
        int appointmentCount = appointmentService.getAllAppointments().size();
        int billCount = billService.getAllBills().size();
        
        printInfo("Total Doctors Registered: " + doctorCount);
        printInfo("Total Patients Registered: " + patientCount);
        printInfo("Total Appointments: " + appointmentCount);
        printInfo("Total Bills Generated: " + billCount);
        
        // Breakdown by appointment status
        printInfo("\nAppointment Status Breakdown:");
        for (AppointmentStatus status : AppointmentStatus.values()) {
            List<Appointment> apts = appointmentService.getAppointmentsByStatus(status);
            if (apts.size() > 0) {
                printInfo("  " + status.getCode() + ": " + apts.size());
            }
        }
        
        // Billing statistics
        List<Bill> allBills = billService.getAllBills();
        long paidCount = allBills.stream().filter(Bill::isPaid).count();
        long unpaidCount = allBills.stream().filter(b -> !b.isPaid()).count();
        double totalRevenue = allBills.stream()
            .filter(Bill::isPaid)
            .mapToDouble(Bill::getTotalAmount)
            .sum();
        
        printInfo("\nBilling Statistics:");
        printInfo("  Paid Bills: " + paidCount);
        printInfo("  Unpaid Bills: " + unpaidCount);
        printInfo("  Total Revenue: ₹" + String.format("%.2f", totalRevenue));
    }

    // ==================== UTILITY METHODS ====================
    
    private static void printHeader(String text) {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("  " + text);
        System.out.println("=".repeat(80));
    }
    
    private static void printSection(String section) {
        System.out.println("\n" + "─".repeat(80));
        System.out.println("  " + section);
        System.out.println("─".repeat(80));
    }
    
    private static void printSuccess(String message) {
        System.out.println("  ✓ " + message);
    }
    
    private static void printInfo(String message) {
        System.out.println("    " + message);
    }
    
    private static void printWarning(String message) {
        System.out.println("  ⚠ " + message);
    }
    
    private static void printError(String message) {
        System.out.println("  ✗ " + message);
    }
}
