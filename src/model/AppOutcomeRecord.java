package src.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import src.model.enums.PrescribeStatus;


public class AppOutcomeRecord implements Serializable {

    private String typeOfService;
    private String prescriptionID;
    private String consultationNotes;
    private final LocalDateTime endDateTime;
    private List<PrescribeMedication> medications;
    private PrescribeStatus prescribeStatus; 

    private static final long serialVersionUID = 3L;

    public AppOutcomeRecord() {
        this.endDateTime = LocalDateTime.now();
        this.typeOfService = "N/A";
        this.consultationNotes = "N/A";
        this.prescriptionID = "";
        this.medications = new ArrayList<>(); 
        this.prescribeStatus = PrescribeStatus.PENDING;
    }

    /**
     * Gets the prescriptionID provided during the appointment.
     * @return prescriptionID.
     */
    public String getPrescriptionID() {
        return prescriptionID;
    }

    /**
     * Sets the prescriptionID for the appointment.
     * @param prescriptionID PrescriptionID.
     */
    public void setPrescriptionID(String prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    /**
     * Gets the prescribe status for the prescription.
     * @return prescribeStatus Status of prescription.
     */
    public PrescribeStatus getPrescribeStatus() {
        return this.prescribeStatus;
    }

    /**
     * Sets the prescribe status for the prescription.
     * @return prescribeStatus Status of prescription.
     */
    public void setPrescribeStatus(PrescribeStatus prescribeStatus) {
        this.prescribeStatus = prescribeStatus;
    }

    /**
     * Gets the type of service provided for the appointment.
     * @return typeOfService New type of service.
     */
    public String getTypeOfService() {
        return this.typeOfService;
    }

    /**
     * Sets the type of service for the appointment.
     * @param typeOfService New type of service.
     */
    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    /**
     * Gets the type of service provided during the appointment.
     * @return the type of service.
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

     /**
     * Gets the notes taken during the consultation.
     * @return the consultation notes.
     */
    public String getConsultationNotes() {
        return this.consultationNotes;
    }

    /**
     * Sets the consultation notes.
     * @param consultationNotes New consultation notes.
     */
    public void setConsultationNotes(String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }

        /**
     * Gets the list of medications prescribed during the appointment.
     * @return a list of prescribed medications.
     */
    public List<PrescribeMedication> getPrescribeMedications() {
        return this.medications;
    }

    /**
     * Sets the list of medications prescribed during the appointment.
     * @param medications New list of prescribed medications.
     */
    public void setPrescribeMedications(List<PrescribeMedication> medications) {
        this.medications = medications;
    }

    /**
     * Adds a medication to the list of prescribed medications.
     * @param prescribeMedication Medication to add to the list.
     */
    public void addMedication(PrescribeMedication prescribeMedication) {
        this.medications.add(prescribeMedication);
    }
}