package com.airtribe.meditrack.constants;

/**
 * Enum for medical specializations.
 * Demonstrates enum usage for domain-specific constants.
 */
public enum Specialization {
    GENERAL("General", "General Practice"),
    CARDIOLOGY("Cardiology", "Heart and cardiovascular diseases"),
    NEUROLOGY("Neurology", "Nervous system and brain disorders"),
    ORTHOPEDICS("Orthopedics", "Bone and joint disorders"),
    DERMATOLOGY("Dermatology", "Skin and hair disorders"),
    PEDIATRICS("Pediatrics", "Child healthcare"),
    PSYCHIATRY("Psychiatry", "Mental health and behavioral disorders"),
    SURGERY("Surgery", "General and specialized surgery");

    private final String name;
    private final String description;

    /**
     * Constructor for Specialization enum.
     * @param name the specialization name
     * @param description the specialization description
     */
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
     * Get enum value from string name.
     * @param name the specialization name
     * @return Specialization enum or GENERAL as default
     */
    public static Specialization fromName(String name) {
        for (Specialization spec : Specialization.values()) {
            if (spec.name.equalsIgnoreCase(name)) {
                return spec;
            }
        }
        return GENERAL;
    }

    @Override
    public String toString() {
        return name + " - " + description;
    }
}
