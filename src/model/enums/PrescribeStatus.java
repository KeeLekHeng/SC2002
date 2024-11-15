package src.model.enums;

/**
 * Enum representing the status of medication prescription approval by pharmacists.
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum PrescribeStatus {

    /**
     * The medication prescription is pending approval.
     */
    PENDING("Pending"),

    /**
     * The medication prescription is not applicable.
     */
    NA("Not Applicable"),

    /**
     * The medication has been dispensed.
     */
    DISPENSED("Dispensed");

    /**
     * The string representation of the prescribed status.
     */
    public final String prescribedStatusAsStr;

    /**
     * Constructor for the PrescribeStatus Enum.
     * 
     * @param prescribedStatusAsStr Prescribed status as a string
     */
    private PrescribeStatus(String prescribedStatusAsStr) {
        this.prescribedStatusAsStr = prescribedStatusAsStr;
    }
}
