package src.model;

import java.io.Serializable;
import src.model.enums.AppointmentStatus;

/**
 * Represents an appointment with a doctor, containing details about the appointment date, time, 
 * status, and outcome record.
 *
 * The outcome record remains at its default values (null) until the appointment is completed.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class Appointment implements Serializable {

    /** Unique identifier of the doctor associated with the appointment. */
    private String doctorID;

    /** Unique identifier of the patient associated with the appointment. */
    private String patientID;

    /** Unique identifier of the appointment. */
    private final String appointmentID;

    /** Status of the appointment, such as pending, confirmed, or completed. */
    private AppointmentStatus appointmentStatus;

    /** Time slot allocated for the appointment. */
    private TimeSlot timeSlot;

    /** Outcome record of the appointment, initially null. */
    private AppOutcomeRecord outcomeRecord;

    /** Serialization identifier for the Appointment class. */
    private static final long serialVersionUID = 2L;

    /**
     * Constructs a new Appointment with the specified appointment ID, doctor ID, patient ID, and time slot.
     * The initial status of the appointment is set to PENDING, and the outcome record is set to null.
     * @param appointmentID Unique identifier for the appointment.
     * @param doctorID Unique identifier of the doctor.
     * @param patientID Unique identifier of the patient.
     * @param timeSlot Time slot allocated for the appointment.
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
     * Gets the unique identifier of the appointment.
     * @return the appointment's ID.
     */
    public String getAppointmentID() {
        return this.appointmentID;
    }

    /**
     * Gets the unique identifier of the doctor associated with the appointment.
     * @return the doctor's ID.
     */
    public String getDoctorID() {
        return this.doctorID;
    }

    /**
     * Gets the unique identifier of the patient associated with the appointment.
     * @return the patient's ID.
     */
    public String getPatientID() {
        return this.patientID;
    }

    /**
     * Sets the unique identifier of the patient associated with the appointment.
     * @param id New patient ID.
     */
    public void setPatientID(String id) {
        this.patientID = id;
    }

    /**
     * Sets the unique identifier of the doctor associated with the appointment.
     * @param id New doctor ID.
     */
    public void setDoctorID(String id) {
        this.doctorID = id;
    }

    /**
     * Gets the status of the appointment.
     * @return the current status of the appointment.
     */
    public AppointmentStatus getAppointmentStatus() {
        return this.appointmentStatus;
    }

    /**
     * Sets the status of the appointment.
     * @param status The new status for the appointment.
     */
    public void setAppointmentStatus(AppointmentStatus status) {
        this.appointmentStatus = status;
    }

    /**
     * Gets the time slot allocated for the appointment.
     * @return the time slot for the appointment.
     */
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    /**
     * Sets the time slot for the appointment.
     * @param timeSlot The new time slot for the appointment.
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    /**
     * Gets the outcome record of the appointment.
     * @return the outcome record, which is null until the appointment is completed.
     */
    public AppOutcomeRecord getAppOutcomeRecord() {
        return this.outcomeRecord;
    }

    /**
     * Sets the outcome record for the appointment.
     * @param outcomeRecord The outcome record of the appointment.
     */
    public void setAppOutcomeRecord(AppOutcomeRecord outcomeRecord) {
        this.outcomeRecord = outcomeRecord;
    }
}