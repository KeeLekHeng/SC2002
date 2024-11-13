package src.model;

import java.io.Serializable;
import src.model.enums.Role;

/**
 * Represents a user with a unique ID, password, and role in the system.
 * Provides methods to retrieve user information and verify the user's role.
 * 
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class User implements Serializable {

    /** Unique identifier for the user. */
    public final String id;

    /** Password for the user. */
    private String password;

    /** Role of the user, defining access rights within the system. */
    protected Role role;

    /** Serialization identifier for the User class. */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new User with the specified ID, password, and role.
     * 
     * @param id       Unique identifier of the user.
     * @param password Password for the user.
     * @param role     Role of the user within the system.
     */
    public User(String id, String password, Role role) {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets the unique identifier of the user.
     * 
     * @return the user's ID.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Gets the password of the user.
     * 
     * @return the user's password.
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets a new password for the user.
     * 
     * @param password New password for the user.
     * @return true if the password was successfully set.
     */
    public boolean setPassword(String password) {
        this.password = password;
        return true; // Indicates success
    }

    /**
     * Gets the role of the user.
     * 
     * @return the user's role.
     */
    public Role getRole() {
        return this.role;
    }

    /**
     * Checks if the user is a patient.
     * 
     * @return true if the user's role is PATIENT.
     */
    public boolean isPatient() {
        return this.role == Role.PATIENT;
    }

    /**
     * Checks if the user is a doctor.
     * 
     * @return true if the user's role is DOCTOR.
     */
    public boolean isDoctor() {
        return this.role == Role.DOCTOR;
    }

    /**
     * Checks if the user is a pharmacist.
     * 
     * @return true if the user's role is PHARMACIST.
     */
    public boolean isPharmacist() {
        return this.role == Role.PHARMACIST;
    }

    /**
     * Checks if the user is an administrator.
     * 
     * @return true if the user's role is ADMINISTRATOR.
     */
    public boolean isAdministrator() {
        return this.role == Role.ADMINISTRATOR;
    }
}
