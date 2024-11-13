package src.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;


public class AppOutcomeRecord implements Serializable {

    private String typeOfService;
    private String consultationNotes;
    private List<PrescribeMedication> medications;

    private static final long serialVersionUID = 3L;

    public AppOutcomeRecord() {
        this.typeOfService = "N/A";
        this.consultationNotes = "N/A";
        this.medications = new ArrayList<>(); // Initialize medications list
    }

    /**
     * Gets the type of service provided during the appointment.
     * @return the type of service.
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

