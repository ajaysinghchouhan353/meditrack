package com.airtribe.meditrack.util;

/**
 * Singleton pattern implementation for application configuration.
 * Demonstrates lazy initialization and thread-safe singleton.
 */
public class AppConfig {
    private static volatile AppConfig instance;
    private static final Object lock = new Object();

    // Configuration properties
    private double consultationFee;
    private double labCharges;
    private double taxRate;
    private int maxPatientsPerDay;
    private int appointmentDurationMinutes;

    /**
     * Private constructor to prevent instantiation.
     */
    private AppConfig() {
        // Initialize default values
        this.consultationFee = 500.0;
        this.labCharges = 200.0;
        this.taxRate = 0.18;  // 18% GST
        this.maxPatientsPerDay = 30;
        this.appointmentDurationMinutes = 30;
    }

    /**
     * Get singleton instance using lazy initialization with double-checked locking.
     * @return the AppConfig instance
     */
    public static AppConfig getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new AppConfig();
                }
            }
        }
        return instance;
    }

    // Getters and setters
    public double getConsultationFee() {
        return consultationFee;
    }

    public void setConsultationFee(double consultationFee) {
        this.consultationFee = consultationFee;
    }

    public double getLabCharges() {
        return labCharges;
    }

    public void setLabCharges(double labCharges) {
        this.labCharges = labCharges;
    }

    public double getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(double taxRate) {
        this.taxRate = taxRate;
    }

    public int getMaxPatientsPerDay() {
        return maxPatientsPerDay;
    }

    public void setMaxPatientsPerDay(int maxPatientsPerDay) {
        this.maxPatientsPerDay = maxPatientsPerDay;
    }

    public int getAppointmentDurationMinutes() {
        return appointmentDurationMinutes;
    }

    public void setAppointmentDurationMinutes(int appointmentDurationMinutes) {
        this.appointmentDurationMinutes = appointmentDurationMinutes;
    }

    /**
     * Calculate tax on amount.
     * @param amount the base amount
     * @return tax amount
     */
    public double calculateTax(double amount) {
        return amount * taxRate;
    }

    /**
     * Calculate amount with tax.
     * @param amount the base amount
     * @return total amount including tax
     */
    public double addTax(double amount) {
        return amount + calculateTax(amount);
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "consultationFee=" + consultationFee +
                ", labCharges=" + labCharges +
                ", taxRate=" + taxRate +
                ", maxPatientsPerDay=" + maxPatientsPerDay +
                ", appointmentDurationMinutes=" + appointmentDurationMinutes +
                '}';
    }
}
