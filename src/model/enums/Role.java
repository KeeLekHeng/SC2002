package src.model.enums;

/**
 * Enum representing different roles in the system.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum Role {
 
    /**
     * Represents the role of a patient.
     */
    PATIENT("Patient"),

    /**
     * Represents the role of a doctor.
     */
    DOCTOR("Doctor"),

    /**
     * Represents the role of a pharmacist.
     */
    PHARMACIST("Pharmacist"),

    /**
     * Represents the role of an administrator.
     */
    ADMINISTRATOR("Administrator");

    /**
     * A String value for the roles for retrieving purposes.
     */
    public final String RoleAsStr;

    /**
     * Constructor for the Role Enum.
     * @param RoleAsStr Role type as a string
     */
    private Role(String RoleAsStr) {
        this.RoleAsStr = RoleAsStr;
    }
}
