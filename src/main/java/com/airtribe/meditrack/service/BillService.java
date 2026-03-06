package com.airtribe.meditrack.service;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.entity.Appointment;
import com.airtribe.meditrack.exception.AppointmentNotFoundException;
import com.airtribe.meditrack.exception.InvalidDataException;
import com.airtribe.meditrack.util.DataStore;
import com.airtribe.meditrack.util.IdGenerator;
import com.airtribe.meditrack.util.Validator;
import com.airtribe.meditrack.util.BillFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * Service class for billing management with Strategy pattern implementation.
 * Demonstrates use of BillFactory and multiple BillingStrategy implementations.
 */
public class BillService {
    private static final Logger logger = LoggerFactory.getLogger(BillService.class);
    private final DataStore<Bill> billStore;
    private final AppointmentService appointmentService;

    public BillService(DataStore<Bill> billStore, AppointmentService appointmentService) {
        this.billStore = billStore;
        this.appointmentService = appointmentService;
    }

    /**
     * Create a bill for an appointment with a specific billing strategy.
     * Demonstrates Factory pattern for bill creation.
     * 
     * @param appointmentId the appointment ID
     * @param consultationFee the consultation fee
     * @param strategyType the type of billing strategy to use
     * @return the created bill
     */
    public Bill createBill(String appointmentId, double consultationFee, String strategyType)
            throws AppointmentNotFoundException, InvalidDataException {
        logger.debug("Creating bill: appointmentId={}, fee={}, strategy={}", appointmentId, consultationFee, strategyType);
        Validator.validateOrThrow(Validator.isNotEmpty(appointmentId), "Appointment ID cannot be empty");
        Validator.validateOrThrow(consultationFee > 0, "Consultation fee must be positive");

        Optional<Appointment> optionalAppointment = appointmentService.getAppointmentById(appointmentId);
        if (!optionalAppointment.isPresent()) {
            logger.error("Appointment not found for billing: id={}", appointmentId);
            throw new AppointmentNotFoundException("Appointment not found with ID: " + appointmentId);
        }

        Appointment appointment = optionalAppointment.get();
        
        // Convert string strategy type to BillingFactory.BillType enum
        BillFactory.BillType billType = getBillType(strategyType);
        
        // Use factory to create bill with appropriate strategy
        String billId = IdGenerator.generateBillId();
        Bill bill = BillFactory.createBill(billId, appointmentId, appointment.getPatientId(),
            appointment.getDoctorId(), consultationFee, billType);
        
        billStore.add(bill);
        logger.info("Bill created successfully: id={}, amount={}, strategy={}", billId, String.format("%.2f", bill.getTotalAmount()), strategyType);
        return bill;
    }

    /**
     * Create a bill with consultation fee only (default strategy).
     * 
     * @param appointmentId the appointment ID
     * @param consultationFee the consultation fee
     * @return the created bill
     */
    public Bill createSimpleBill(String appointmentId, double consultationFee)
            throws AppointmentNotFoundException, InvalidDataException {
        return createBill(appointmentId, consultationFee, "STANDARD");
    }

    /**
     * Convert string strategy type to BillingFactory.BillType enum.
     * 
     * @param strategyType the strategy type string
     * @return the corresponding BillType enum
     */
    private BillFactory.BillType getBillType(String strategyType) {
        if (strategyType == null) {
            return BillFactory.BillType.STANDARD;
        }

        return switch (strategyType.toUpperCase()) {
            case "PREMIUM" -> BillFactory.BillType.PREMIUM;
            case "DISCOUNTED" -> BillFactory.BillType.DISCOUNTED;
            case "EMERGENCY" -> BillFactory.BillType.EMERGENCY;
            default -> BillFactory.BillType.STANDARD;
        };
    }

    /**
     * Get a bill by ID.
     * 
     * @param billId the bill ID
     * @return Optional containing the bill or empty
     */
    public Optional<Bill> getBillById(String billId) {
        return billStore.findById(billId);
    }

    /**
     * Get all bills.
     * 
     * @return list of all bills
     */
    public List<Bill> getAllBills() {
        return billStore.getAll();
    }

    /**
     * Get bills for a specific patient.
     * Uses Streams API for filtering.
     * 
     * @param patientId the patient ID
     * @return list of bills for the patient
     */
    public List<Bill> getBillsByPatient(String patientId) {
        return billStore.filter(bill -> bill.getPatientId().equals(patientId));
    }

    /**
     * Get bills for a specific doctor.
     * Uses Streams API for filtering.
     * 
     * @param doctorId the doctor ID
     * @return list of bills for the doctor
     */
    public List<Bill> getBillsByDoctor(String doctorId) {
        return billStore.filter(bill -> bill.getDoctorId().equals(doctorId));
    }

    /**
     * Get paid bills.
     * Uses Streams API for filtering.
     * 
     * @return list of paid bills
     */
    public List<Bill> getPaidBills() {
        return billStore.filter(Bill::isPaid);
    }

    /**
     * Get unpaid bills.
     * Uses Streams API for filtering.
     * 
     * @return list of unpaid bills
     */
    public List<Bill> getUnpaidBills() {
        return billStore.filter(bill -> !bill.isPaid());
    }

    /**
     * Add charges to a bill and recalculate amount.
     * 
     * @param billId the bill ID
     * @param labCharges the lab charges to add
     * @param medicineCharges the medicine charges to add
     * @return true if updated, false otherwise
     */
    public boolean addCharges(String billId, double labCharges, double medicineCharges)
            throws AppointmentNotFoundException {
        Optional<Bill> optionalBill = billStore.findById(billId);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            bill.setLabCharges(labCharges);
            bill.setMedicineCharges(medicineCharges);
            return billStore.update(billId, bill);
        }
        throw new AppointmentNotFoundException("Bill not found with ID: " + billId);
    }

    /**
     * Mark bill as paid.
     * 
     * @param billId the bill ID
     * @return true if marked paid, false otherwise
     */
    public boolean markBillAsPaid(String billId) throws AppointmentNotFoundException {
        logger.debug("Marking bill as paid: id={}", billId);
        Optional<Bill> optionalBill = billStore.findById(billId);
        if (optionalBill.isPresent()) {
            Bill bill = optionalBill.get();
            bill.markAsPaid();
            boolean updated = billStore.update(billId, bill);
            if (updated) {
                logger.info("Bill marked as paid: id={}, amount={}", billId, String.format("%.2f", bill.getTotalAmount()));
            }
            return updated;
        }
        logger.error("Bill not found when marking as paid: id={}", billId);
        throw new AppointmentNotFoundException("Bill not found with ID: " + billId);
    }

    /**
     * Calculate total revenue from paid bills.
     * Uses Streams API for aggregation.
     * 
     * @return total revenue
     */
    public double calculateTotalRevenue() {
        return getPaidBills().stream()
                .mapToDouble(Bill::getAmount)
                .sum();
    }

    /**
     * Get total number of bills.
     * 
     * @return total bills count
     */
    public int getTotalBills() {
        return billStore.size();
    }
}
