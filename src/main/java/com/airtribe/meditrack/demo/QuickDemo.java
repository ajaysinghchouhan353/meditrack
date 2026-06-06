package com.airtribe.meditrack.demo;

import com.airtribe.meditrack.constants.Specialization;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Doctor;
import com.airtribe.meditrack.entity.Patient;
import com.airtribe.meditrack.service.AppointmentService;
import com.airtribe.meditrack.service.BillService;
import com.airtribe.meditrack.service.ConsoleNotificationListener;
import com.airtribe.meditrack.service.DoctorService;
import com.airtribe.meditrack.service.NotificationService;
import com.airtribe.meditrack.service.PatientService;
import com.airtribe.meditrack.util.DataStore;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.airtribe.meditrack.util.DateUtil;

/**
 * Quick Demo - Minimal demonstration of core MediTrack features
 * 
 * This is a simplified demo that shows the essential workflow:
 * 1. Register a doctor
 * 2. Register a patient
 * 3. Book an appointment
 * 4. Complete the appointment
 * 5. Generate a bill
 * 6. Process payment
 * 
 * Run this for a quick overview of the system.
 * For comprehensive demos, run DemoRunner.java
 * 
 * @author Airtribe MediTrack Team
 * @version 1.0.0
 */
public class QuickDemo {
    
    public static void main(String[] args) {
        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║        MEDITRACK QUICK DEMO - 60 SECOND OVERVIEW         ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝\n");
        
        try {
            // Initialize services
            System.out.println("→ Initializing MediTrack System...");
            
            DataStore<Doctor> doctorStore = new DataStore<>();
            DataStore<Patient> patientStore = new DataStore<>();
            DataStore<Appointment> appointmentStore = new DataStore<>();
            DataStore<Bill> billStore = new DataStore<>();
            
            NotificationService notificationService = new NotificationService();
            notificationService.registerListener(new ConsoleNotificationListener());
            
            DoctorService doctorService = new DoctorService(doctorStore);
            PatientService patientService = new PatientService(patientStore);
            AppointmentService appointmentService = new AppointmentService(
                appointmentStore, doctorService, patientService, notificationService);
            BillService billService = new BillService(billStore, appointmentService);
            
            System.out.println("✓ System initialized\n");
            
            // Step 1: Register Doctor
            System.out.println("STEP 1: Register Doctor");
            Doctor doctor = doctorService.registerDoctor(
                "Dr. Sarah Johnson",
                "sarah.j@meditrack.com",
                "5551230123",
                "123 Medical Center",
                Specialization.CARDIOLOGY.getName(),
                "MED-98765",
                12
            );
            System.out.println("✓ Doctor registered: " + doctor.getName() + " (" + doctor.getSpecialty().getName() + ")");
            System.out.println("  Doctor ID: " + doctor.getId() + "\n");
            
            // Step 2: Register Patient
            System.out.println("STEP 2: Register Patient");
            Patient patient = patientService.registerPatient(
                "John Smith",
                "john.s@email.com",
                "5551239876",
                "456 Oak Street",
                LocalDate.of(1985, 6, 15),
                "Male",
                "O+"
            );
            System.out.println("✓ Patient registered: " + patient.getName() + " (Blood: " + patient.getBloodGroup() + ")");
            System.out.println("  Patient ID: " + patient.getId() + "\n");
            
            // Step 3: Book Appointment
            System.out.println("STEP 3: Book Appointment");
            LocalDateTime appointmentTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0);
            Appointment appointment = appointmentService.bookAppointment(
                doctor.getId(),
                patient.getId(),
                appointmentTime,
                "Annual cardiac checkup"
            );
            System.out.println("✓ Appointment booked");
            System.out.println("  Appointment ID: " + appointment.getId());
            System.out.println("  Date/Time: " + DateUtil.formatDateTime(appointmentTime));
            System.out.println("  Status: " + appointment.getStatus().getDescription() + "\n");
            
            // Step 4: Complete Appointment
            System.out.println("STEP 4: Complete Appointment");
            appointmentService.markAppointmentCompleted(appointment.getId());
            appointmentService.addAppointmentNotes(
                appointment.getId(), 
                "Patient is healthy. Regular exercise recommended."
            );
            System.out.println("✓ Appointment completed with notes\n");
            
            // Step 5: Generate Bill
            System.out.println("STEP 5: Generate Bill (Strategy Pattern)");
            Bill bill = billService.createBill(appointment.getId(), 750.0, "STANDARD");
            billService.addCharges(bill.getId(), 300.0, 150.0);
            // Refresh bill to get updated charges
            bill = billService.getBillById(bill.getId()).orElse(bill);
            
            System.out.println("✓ Bill generated");
            System.out.println("  Bill ID: " + bill.getId());
            System.out.println("  Strategy: " + (bill.getBillingStrategy() != null ? bill.getBillingStrategy().getStrategyName() : "STANDARD"));
            System.out.println("  Consultation Fee: ₹" + bill.getConsultationFee());
            System.out.println("  Lab Charges: ₹" + bill.getLabCharges());
            System.out.println("  Medicine Charges: ₹" + bill.getMedicineCharges());
            System.out.println("  Total Amount: ₹" + String.format("%.2f", bill.getTotalAmount()) + "\n");
            
            // Step 6: Process Payment
            System.out.println("STEP 6: Process Payment");
            billService.markBillAsPaid(bill.getId());
            // Refresh bill to get updated payment status
            bill = billService.getBillById(bill.getId()).orElse(bill);
            System.out.println("✓ Payment processed successfully");
            System.out.println("  Payment Method: Credit Card");
            System.out.println("  Status: PAID\n");
            
            // Summary
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("                    DEMO COMPLETED!");
            System.out.println("═══════════════════════════════════════════════════════════");
            System.out.println("\n✅ Successfully demonstrated:");
            System.out.println("   • Doctor Management");
            System.out.println("   • Patient Management");
            System.out.println("   • Appointment Booking & Workflow");
            System.out.println("   • Observer Pattern (Notifications)");
            System.out.println("   • Strategy Pattern (Billing)");
            System.out.println("   • Payment Processing");
            
            System.out.println("\n📊 Final Statistics:");
            System.out.println("   Doctors: " + doctorService.getAllDoctors().size());
            System.out.println("   Patients: " + patientService.getAllPatients().size());
            System.out.println("   Appointments: " + appointmentService.getAllAppointments().size());
            System.out.println("   Bills: " + billService.getAllBills().size());
            
            System.out.println("\n💡 For comprehensive demos, run:");
            System.out.println("   mvn exec:java -Dexec.mainClass=\"com.airtribe.meditrack.demo.DemoRunner\"");
            System.out.println();
            
        } catch (Exception e) {
            System.err.println("\n✗ Demo failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
