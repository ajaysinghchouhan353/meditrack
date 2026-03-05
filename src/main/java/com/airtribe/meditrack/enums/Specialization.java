package com.airtribe.meditrack.enums;

/**
 * Specialization enum defining all medical specialties with descriptions.
 * Provides type-safe constants for doctor specializations.
 */
public enum Specialization {
    GENERAL("General Practice", "General medical practice and primary care"),
    CARDIOLOGY("Cardiology", "Heart and cardiovascular system diseases"),
    NEUROLOGY("Neurology", "Brain and nervous system disorders"),
    ORTHOPEDICS("Orthopedics", "Bones, joints, and musculoskeletal system"),
    DERMATOLOGY("Dermatology", "Skin, hair, and nails diseases"),
    PEDIATRICS("Pediatrics", "Medical care for children and infants"),
    PSYCHIATRY("Psychiatry", "Mental health and psychological disorders"),
    SURGERY("General Surgery", "Surgical procedures and treatments");

    private final String name;
    private final String description;

    Specialization(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Create Specialization from string name (case-insensitive).
     * Falls back to GENERAL if specialty name not found.
     * 
     * @param specialtyName the specialty name to convert
     * @return the corresponding Specialization enum or GENERAL
     */
    public static Specialization fromName(String specialtyName) {
        if (specialtyName == null || specialtyName.isEmpty()) {
            return GENERAL;
        }

        // Try exact match
        for (Specialization spec : values()) {
            if (spec.name.equalsIgnoreCase(specialtyName)) {
                return spec;
            }
        }

        // Try enum constant name
        try {
            String upperName = specialtyName.toUpperCase().replace(" ", "_").replace("-", "_");
            return Specialization.valueOf(upperName);
        } catch (IllegalArgumentException e) {
            // Return GENERAL as default
            return GENERAL;
        }
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
