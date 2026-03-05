package com.airtribe.meditrack.iface;

import com.airtribe.meditrack.entity.Bill;

/**
 * Strategy interface for different billing strategies.
 * Demonstrates Strategy design pattern for flexible billing calculations.
 */
public interface BillingStrategy {
    /**
     * Calculate the total bill amount based on strategy.
     * @param bill the bill to calculate
     * @return the calculated amount
     */
    double calculateAmount(Bill bill);

    /**
     * Get strategy name.
     * @return strategy name
     */
    String getStrategyName();
}
