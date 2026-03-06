package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.iface.BillingStrategy;

/**
 * Factory pattern implementation for creating different bill types with strategies.
 * Demonstrates Factory design pattern for object creation.
 */
public class BillFactory {

    /**
     * Bill type enumeration.
     */
    public enum BillType {
        STANDARD, PREMIUM, DISCOUNTED, EMERGENCY
    }

    /**
     * Create a bill with appropriate strategy.
     * @param id the bill ID
     * @param appointmentId the appointment ID
     * @param patientId the patient ID
     * @param doctorId the doctor ID
     * @param consultationFee the consultation fee
     * @param billType the bill type
     * @return configured Bill object
     */
    public static Bill createBill(String id, String appointmentId, String patientId, 
                                  String doctorId, double consultationFee, BillType billType) {
        Bill bill = new Bill(id, appointmentId, patientId, doctorId, consultationFee);
        
        BillingStrategy strategy = getStrategy(billType);
        bill.setBillingStrategy(strategy);
        
        return bill;
    }

    /**
     * Create a bill from template.
     * @param templateBill the template bill
     * @param newBillId the new bill ID
     * @param billType the bill type
     * @return new Bill with copied properties
     */
    public static Bill createFromTemplate(Bill templateBill, String newBillId, BillType billType) {
        Bill bill = new Bill(newBillId, templateBill.getAppointmentId(), 
                          templateBill.getPatientId(), templateBill.getDoctorId(),
                          templateBill.getConsultationFee());
        
        bill.setLabCharges(templateBill.getLabCharges());
        bill.setMedicineCharges(templateBill.getMedicineCharges());
        
        BillingStrategy strategy = getStrategy(billType);
        bill.setBillingStrategy(strategy);
        
        return bill;
    }

    /**
     * Get billing strategy based on bill type.
     * @param billType the bill type
     * @return the appropriate BillingStrategy
     */
    private static BillingStrategy getStrategy(BillType billType) {
        return switch (billType) {
            case STANDARD -> new BillingStrategies.StandardBillingStrategy();
            case PREMIUM -> new BillingStrategies.PremiumBillingStrategy();
            case DISCOUNTED -> new BillingStrategies.DiscountedBillingStrategy();
            case EMERGENCY -> new BillingStrategies.EmergencyBillingStrategy();
        };
    }
}
