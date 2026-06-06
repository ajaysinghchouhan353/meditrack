package com.airtribe.meditrack.interfaces;

/**
 * Payable interface for entities that can be paid.
 */
public interface Payable {
    /**
     * Get the amount to be paid.
     * @return the payable amount
     */
    double getAmount();

    /**
     * Mark the bill as paid.
     */
    void markAsPaid();
}
