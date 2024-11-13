package src.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import src.model.enums.AppointmentStatus;

/**
 * Represents an appointment with a doctor, containing details about the appointment date, time, 
 * status, and outcome record.
 *
 * The "Outcome Record" values remain at default values until the appointment is completed.
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class Appointment implements Serializable {

    /** Unique identifier of the doctor associated with the appointment. */
    private int doctorID;


    /** Status of the appointment, such as pending, confirmed, or completed. */
    private AppointmentStatus appointmentStatus;

    /** Time slot allocated for the appointment. */
    private TimeSlot timeSlot;

    // Outcome Record
    /** Type of service provided during the appointment. */
    private String typeOfService;

    /** List of medications prescribed during the appointment. Can initially be undefined. */
    private List<PrescribeMedication> medications;

    /** Notes taken during the consultation. */
    private String consultationNotes;

    /** Serialization identifier for the Appointment class. */
    private static final long serialVersionUID = 2L;

    /**
     * Constructs a new Appointment with the specified doctor ID, date, time, and time slot.
     * The initial status of the appointment is set to PENDING, and the outcome record is
     * set to default values ("N/A").
     * 
     * @param doctorID       Unique identifier of the doctor.
     * @param timeSlot       Time slot allocated for the appointment.
     */
    public Appointment(int doctorID, TimeSlot timeSlot) {
        this.doctorID = doctorID;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.timeSlot = timeSlot;
        this.typeOfService = "N/A";
        this.consultationNotes = "N/A";
        this.medications = new ArrayList<>(); // Initialize medications list
    }

    /**
     * Gets the doctor's unique identifier.
     * @return the doctor's ID.
     */
    public int getDoctorID() {
        return this.doctorID;
    }

    /**
     * Sets the doctor's unique identifier.
     * @param id New doctor ID.
     */
    public void setDoctorID(int id) {
        this.doctorID = id;
    }

    /**
     * Gets the status of the appointment.
     * @return the current appointment status.
     */
    public AppointmentStatus getAppointmentStatus() {
        return this.appointmentStatus;
    }

    /**
     * Sets the status of the appointment.
     * @param status New status for the appointment.
     */
    public void setAppointmentStatus(AppointmentStatus status) {
        this.appointmentStatus = status;
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
     * Adds a medication to the list of prescribed medications.
     * @param prescribeMedication Medication to add to the list.
     */
    public void addMedication(PrescribeMedication prescribeMedication) {
        this.medications.add(prescribeMedication);
    }
}
