package src.model;

import src.model.enums.BloodType;
import src.model.enums.Gender;
import src.model.enums.Role;

/**
 * Represents a patient in the system, extending the User class.
 * A patient has additional attributes such as doctor, personal details, and blood type.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class Patient extends User {

    /** The ID of the doctor assigned to the patient. */
    public String doctorID;

    /** The unique patient ID. */
    public final String patientID;

    /** The name of the patient. */
    public String name;

    /** The date of birth of the patient. */
    public final String dob;

    /** The gender of the patient. */
    public Gender gender;

    /** The phone number of the patient. */
    public String phonenumber;

    /** The email of the patient. */
    public String email;

    /** The blood type of the patient. */
    private BloodType bloodType;

    /** Serialization identifier for the Patient class. */
    private static final long serialVersionUID = 4L;

    /**
     * Constructs a new Patient with the specified attributes.
     * @param patientID The unique identifier of the patient.
     * @param doctorID The ID of the patient's doctor.
     * @param password The password for the patient.
     * @param role The role of the patient in the system.
     * @param name The name of the patient.
     * @param dob The date of birth of the patient.
     * @param gender The gender of the patient.
     * @param phonenumber The phone number of the patient.
     * @param email The email of the patient.
     * @param bloodType The blood type of the patient.
     */
    public Patient(String patientID, String doctorID, String password, Role role, String name, String dob,
            Gender gender, String phonenumber, String email, BloodType bloodType) {
        super(patientID, password, role);

        this.doctorID = doctorID;
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        setPhonenumber(phonenumber);
        setEmail(email);
        this.bloodType = bloodType;
    }

    /**
     * Gets the ID of the doctor assigned to the patient.
     * @return the doctor ID.
     */
    public String getDoctorID() {
        return this.doctorID;
    }

    /**
     * Gets the unique patient ID.
     * @return the patient ID.
     */
    public String getPatientID() {
        return this.patientID;
    }

    /**
     * Gets the name of the patient.
     * @return the name of the patient.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the patient.
     * @param name The new name of the patient.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the date of birth of the patient.
     * @return the date of birth of the patient.
     */
    public String getDob() {
        return this.dob;
    }

    /**
     * Gets the gender of the patient.
     * @return the gender of the patient.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Gets the phone number of the patient.
     * @return the phone number of the patient.
     */
    public String getPhonenumber() {
        return this.phonenumber;
    }

    /**
     * Sets the phone number of the patient.
     * @param phonenumber The new phone number for the patient.
     */
    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    /**
     * Gets the email of the patient.
     * @return the email of the patient.
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Sets the email of the patient.
     * @param email The new email for the patient.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the blood type of the patient.
     * @return the blood type of the patient.
     */
    public BloodType getBloodType() {
        return this.bloodType;
    }

    /**
     * Sets the ID of the doctor assigned to the patient.
     * @param doctorID The new doctor ID.
     */
    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    /**
     * Sets the gender of the patient.
     * @param gender The new gender for the patient.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Sets the blood type of the patient.
     * @param bloodType The new blood type for the patient.
     */
    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

}
