package com.airtribe.meditrack.util;

import com.airtribe.meditrack.entity.Bill;
import com.airtribe.meditrack.iface.BillingStrategy;

/**
 * Concrete billing strategies demonstrating Strategy design pattern.
 */
public class BillingStrategies {

    /**
     * Standard billing strategy - consultation + lab + medicine + tax.
     */
    public static class StandardBillingStrategy implements BillingStrategy {
        @Override
        public double calculateAmount(Bill bill) {
            double subtotal = bill.getConsultationFee() + 
                             bill.getLabCharges() + 
                             bill.getMedicineCharges();
            return AppConfig.getInstance().addTax(subtotal);
        }

        @Override
        public String getStrategyName() {
            return "Standard Billing";
        }
    }

    /**
     * Premium billing strategy - with service charge.
     */
    public static class PremiumBillingStrategy implements BillingStrategy {
        private static final double SERVICE_CHARGE_PERCENT = 0.10;

        @Override
        public double calculateAmount(Bill bill) {
            double subtotal = bill.getConsultationFee() + 
                             bill.getLabCharges() + 
                             bill.getMedicineCharges();
            double serviceCharge = subtotal * SERVICE_CHARGE_PERCENT;
            return AppConfig.getInstance().addTax(subtotal + serviceCharge);
        }

        @Override
        public String getStrategyName() {
            return "Premium Billing (10% service charge)";
        }
    }

    /**
     * Discounted billing strategy - for bulk appointments.
     */
    public static class DiscountedBillingStrategy implements BillingStrategy {
        private static final double DISCOUNT_PERCENT = 0.15;

        @Override
        public double calculateAmount(Bill bill) {
            double subtotal = bill.getConsultationFee() + 
                             bill.getLabCharges() + 
                             bill.getMedicineCharges();
            double discount = subtotal * DISCOUNT_PERCENT;
            return AppConfig.getInstance().addTax(subtotal - discount);
        }

        @Override
        public String getStrategyName() {
            return "Discounted Billing (15% discount)";
        }
    }

    /**
     * Emergency billing strategy - higher charges for emergency appointments.
     */
    public static class EmergencyBillingStrategy implements BillingStrategy {
        private static final double EMERGENCY_SURCHARGE = 0.25;

        @Override
        public double calculateAmount(Bill bill) {
            double subtotal = bill.getConsultationFee() + 
                             bill.getLabCharges() + 
                             bill.getMedicineCharges();
            double surcharge = subtotal * EMERGENCY_SURCHARGE;
            return AppConfig.getInstance().addTax(subtotal + surcharge);
        }

        @Override
        public String getStrategyName() {
            return "Emergency Billing (25% surcharge)";
        }
    }
}
