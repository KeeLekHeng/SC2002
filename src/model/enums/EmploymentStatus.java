package src.model.enums;

/**
 * Enum representing the employment status of an individual.
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum EmploymentStatus {
 
    /**
     * The individual is employed.
     */
    EMPLOYED("Employed"),

    /**
     * The individual's employment status is removed.
     */
    REMOVED("Removed");

    /**
     * The string representation of the employment status.
     */
    public final String employmentStatusAsStr;

    /**
     * Constructor for the EmploymentStatus Enum.
     * 
     * @param employmentStatusAsStr Employment status as a string
     */
    private EmploymentStatus(String employmentStatusAsStr) {
        this.employmentStatusAsStr = employmentStatusAsStr;
    }
}