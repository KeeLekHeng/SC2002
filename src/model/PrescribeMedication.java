package src.model;

import java.io.Serializable;

/**
 * Represents a prescribed medication with a name and amount.
 * This class is used to track multiple medications prescribed in an outcome record.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class PrescribeMedication implements Serializable {
    
    /** The name of the prescribed medication. */
    private String name;

    /** The amount of the prescribed medication. */
    private int amount;

    /** Serialization identifier for the PrescribeMedication class. */
    private static final long serialVersionUID = 7L;

    /**
     * Constructs a new PrescribeMedication with the specified name and amount.
     * @param name The name of the medication.
     * @param amount The amount of the medication prescribed.
     */
    public PrescribeMedication(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Gets the name of the prescribed medication.
     * @return the medication name.
     */
    public String getMedicationName() {
        return this.name;
    }

    /**
     * Gets the amount of the prescribed medication.
     * @return the prescription amount.
     */
    public int getPrescriptionAmount() {
        return this.amount;
    }

    /**
     * Sets the amount of the prescribed medication.
     * @param amount The new prescription amount.
     */
    public void setPrescriptionAmount(int amount) {
        this.amount = amount;
    }

    /**
     * Sets the name of the prescribed medication.
     * @param name The new name of the medication.
     */
    public void setName(String name) {
        this.name = name;
    }
}
