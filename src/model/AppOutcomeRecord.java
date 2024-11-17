package src.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.model.enums.PrescribeStatus;

/**
 * Represents the outcome record of an appointment, including details such as prescription ID, consultation notes, prescribed medications, and prescription status.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class AppOutcomeRecord implements Serializable {

    /** Type of service provided during the appointment. */
    private String typeOfService;

    /** Prescription ID for the appointment. */
    private String prescriptionID;

    /** Consultation notes taken during the appointment. */
    private String consultationNotes;

    /** End date and time of the appointment. */
    private final LocalDateTime endDateTime;

    /** List of medications prescribed during the appointment. */
    private List<PrescribeMedication> medications;

    /** Status of the prescription. */
    private PrescribeStatus prescribeStatus;

    /** Serialization identifier for the AppOutcomeRecord class. */
    private static final long serialVersionUID = 3L;

    /**
     * Constructs a new AppOutcomeRecord with default values.
     */
    public AppOutcomeRecord() {
        this.endDateTime = LocalDateTime.now();
        this.typeOfService = "N/A";
        this.consultationNotes = "N/A";
        this.prescriptionID = "";
        this.medications = new ArrayList<>();
        this.prescribeStatus = PrescribeStatus.PENDING;
    }

    /**
     * Gets the prescription ID provided during the appointment.
     * @return prescriptionID The unique ID associated with the prescription.
     */
    public String getPrescriptionID() {
        return prescriptionID;
    }

    /**
     * Sets the prescription ID for the appointment.
     * @param prescriptionID The unique ID to set for the prescription.
     */
    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    /**
     * Gets the prescription status for the appointment.
     * @return prescribeStatus The current status of the prescription.
     */
    public PrescribeStatus getPrescribeStatus() {
        return this.prescribeStatus;
    }

    /**
     * Sets the prescription status for the appointment.
     * @param prescribeStatus The status to set for the prescription.
     */
    public void setPrescribeStatus(PrescribeStatus prescribeStatus) {
        this.prescribeStatus = prescribeStatus;
    }

    /**
     * Gets the type of service provided for the appointment.
     * @return typeOfService The type of service performed during the appointment.
     */
    public String getTypeOfService() {
        return this.typeOfService;
    }

    /**
     * Sets the type of service for the appointment.
     * @param typeOfService The new type of service.
     */
    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    /**
     * Gets the end date and time of the appointment.
     * @return endDateTime The date and time when the appointment concluded.
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Gets the consultation notes for the appointment.
     * @return consultationNotes The notes recorded during the consultation.
     */
    public String getConsultationNotes() {
        return this.consultationNotes;
    }

    /**
     * Sets the consultation notes for the appointment.
     * @param consultationNotes The new consultation notes.
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

    /**
     * Gets the list of prescribed medications for the appointment.
     * @return medications A list of medications prescribed during the appointment.
     */
    public List<PrescribeMedication> getPrescribeMedications() {
        return this.medications;
    }

    /**
     * Sets the list of prescribed medications for the appointment.
     * @param medications The list of medications to set.
     */
    public void setPrescribeMedications(List<PrescribeMedication> medications) {
        this.medications = medications;
    }

    /**
     * Adds a medication to the prescribed medications list.
     * @param prescribeMedication The medication to add to the list.
     */
    public void addMedication(PrescribeMedication prescribeMedication) {
        this.medications.add(prescribeMedication);
    }
}
