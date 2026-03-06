package com.airtribe.meditrack.entity;

import com.airtribe.meditrack.iface.Payable;
import com.airtribe.meditrack.iface.Searchable;
import com.airtribe.meditrack.iface.BillingStrategy;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Bill entity with Strategy pattern implementation.
 * Supports multiple billing calculation strategies.
 */
public class Bill implements Searchable, Payable, Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String appointmentId;
    private String patientId;
    private String doctorId;
    private double consultationFee;
    private double labCharges;
    private double medicineCharges;
    private double totalAmount;
    private LocalDate billDate;
    private boolean paid;
    private transient BillingStrategy billingStrategy;  // Strategy object

    public Bill(String id, String appointmentId, String patientId, String doctorId,
                double consultationFee) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.consultationFee = consultationFee;
        this.labCharges = 0.0;
        this.medicineCharges = 0.0;
        this.billDate = LocalDate.now();
        this.paid = false;
        this.billingStrategy = null;  // Will use default calculation
        calculateTotalAmount();
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
        calculateTotalAmount();
    }

    public double getLabCharges() {
        return labCharges;
    }

    public void setLabCharges(double labCharges) {
        this.labCharges = labCharges;
        calculateTotalAmount();
    }

    public double getMedicineCharges() {
        return medicineCharges;
    }

    public void setMedicineCharges(double medicineCharges) {
        this.medicineCharges = medicineCharges;
        calculateTotalAmount();
    }

    /**
     * Set the billing strategy for this bill.
     * @param billingStrategy the strategy to use
     */
    public void setBillingStrategy(BillingStrategy billingStrategy) {
        this.billingStrategy = billingStrategy;
        calculateTotalAmount();
    }

    /**
     * Get the billing strategy.
     * @return the current strategy or null
     */
    public BillingStrategy getBillingStrategy() {
        return billingStrategy;
    }

    @Override
    public double getAmount() {
        return totalAmount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    /**
     * Calculate total amount using strategy or default calculation.
     */
    private void calculateTotalAmount() {
        if (billingStrategy != null) {
            // Use strategy pattern to calculate amount
            this.totalAmount = billingStrategy.calculateAmount(this);
        } else {
            // Default calculation (no tax, just sum of all charges)
            this.totalAmount = consultationFee + labCharges + medicineCharges;
        }
    }

    public LocalDate getBillDate() {
        return billDate;
    }

    public void setBillDate(LocalDate billDate) {
        this.billDate = billDate;
    }

    public boolean isPaid() {
        return paid;
    }

    @Override
    public void markAsPaid() {
        this.paid = true;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", totalAmount=" + totalAmount +
                ", billDate=" + billDate +
                ", paid=" + paid +
                (billingStrategy != null ? ", strategy='" + billingStrategy.getStrategyName() + "'" : "") +
                '}';
    }
}
