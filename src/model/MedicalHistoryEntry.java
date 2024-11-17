package src.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents an entry in the medical history.
 * A medical history entry includes details such as the diagnosis, prescription, treatment, and the date of the entry.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class MedicalHistoryEntry implements Serializable {

    /** The date of the medical history entry. */
    private Date date;

    /** The diagnosis made during the medical consultation. */
    private String diagnosis;

    /** The prescription provided during the medical consultation. */
    private String prescription;

    /** The treatment recommended for the diagnosis. */
    private String treatment;

    /** Serialization identifier for the MedicalHistoryEntry class. */
    private static final long serialVersionUID = 5L;

    /**
     * Constructs a new MedicalHistoryEntry with the specified details.
     * @param diagnosis The diagnosis made during the medical consultation.
     * @param prescription The prescription provided during the medical consultation.
     * @param treatment The treatment recommended for the diagnosis.
     */
    public MedicalHistoryEntry(String diagnosis, String prescription, String treatment) {
        this.date = new Date();
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.treatment = treatment;
    }

    /**
     * Gets the date of the medical history entry.
     * @return the date of the medical history entry.
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * Sets the date of the medical history entry.
     * @param date The new date for the medical history entry.
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets the diagnosis from the medical history entry.
     * @return the diagnosis from the medical history entry.
     */
    public String getDiagnosis() {
        return this.diagnosis;
    }

    /**
     * Sets the diagnosis for the medical history entry.
     * @param diagnosis The new diagnosis for the medical history entry.
     */
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    /**
     * Gets the prescription from the medical history entry.
     * @return the prescription from the medical history entry.
     */
    public String getPrescription() {
        return this.prescription;
    }

    /**
     * Sets the prescription for the medical history entry.
     * @param prescription The new prescription for the medical history entry.
     */
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    /**
     * Gets the treatment from the medical history entry.
     * @return the treatment from the medical history entry.
     */
    public String getTreatment() {
        return this.treatment;
    }

    /**
     * Sets the treatment for the medical history entry.
     * @param treatment The new treatment for the medical history entry.
     */
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    /**
     * Returns a string representation of the medical history entry.
     * @return a string describing the medical history entry.
     */
    public String toString() {
        return "Date:" + date + ", Diagnosis: " + diagnosis + ", Prescription: " + prescription + ", Treatment: " + treatment;
    }
}
