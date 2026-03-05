package com.airtribe.meditrack.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * Immutable BillSummary entity representing an aggregated bill summary.
 */
public final class BillSummary implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String patientId;
    private final String patientName;
    private final List<Bill> bills;
    private final double totalAmount;
    private final int totalBills;
    private final LocalDate summaryDate;

    public BillSummary(String patientId, String patientName, List<Bill> bills) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.bills = List.copyOf(bills);
        this.totalAmount = bills.stream().mapToDouble(Bill::getTotalAmount).sum();
        this.totalBills = bills.size();
        this.summaryDate = LocalDate.now();
    }

    public String getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public int getTotalBills() {
        return totalBills;
    }

    public LocalDate getSummaryDate() {
        return summaryDate;
    }

    public double getAverageBillAmount() {
        return totalBills > 0 ? totalAmount / totalBills : 0.0;
    }

    @Override
    public String toString() {
        return "BillSummary{" +
                "patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", totalAmount=" + totalAmount +
                ", totalBills=" + totalBills +
                ", summaryDate=" + summaryDate +
                '}';
    }
}
