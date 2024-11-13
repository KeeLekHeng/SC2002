package src.model;

import java.io.Serializable;
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
    private String doctorID;

    /** Unique identifier of the patient associated with the appointment. */
    private String patientID;

    /** Unique identifier of the patient associated with the appointment. */
    private final String appointmentID;


    /** Status of the appointment, such as pending, confirmed, or completed. */
    private AppointmentStatus appointmentStatus;

    /** Time slot allocated for the appointment. */
    private TimeSlot timeSlot;

    private AppOutcomeRecord outcomeRecord;

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
    public Appointment(String appointmentID, String doctorID, String patientID, TimeSlot timeSlot) {
        this.appointmentID = appointmentID;
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.timeSlot = timeSlot;
        this.outcomeRecord = null;
    }

    /**
     * Gets the doctor's unique identifier.
     * @return the appointment's ID.
     */
    public String getAppointmentID() {
        return this.appointmentID;
    }

    /**
     * Gets the doctor's unique identifier.
     * @return the doctor's ID.
     */
    public String getDoctorID() {
        return this.doctorID;
    }

    /**
     * Gets the doctor's unique identifier.
     * @return the patient's ID.
     */
    public String getPatientID() {
        return this.patientID;
    }

    /**
     * Sets the doctor's unique identifier.
     * @param id New patient ID.
     */
    public void setPatientID(String id) {
        this.patientID = id;
    }

    /**
     * Sets the doctor's unique identifier.
     * @param id New doctor ID.
     */
    public void setDoctorID(String id) {
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

    public TimeSlot getTimeSlot () {
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public AppOutcomeRecord getAppOutcomeRecord () {
        return this.outcomeRecord;
    }

    public void setAppOutcomeRecord (AppOutcomeRecord outcomeRecord) {
        this.outcomeRecord = outcomeRecord;
    }
}