package src.model;

/**
 * Represents a time slot for a doctor, including the doctor ID and the allocated time slot.
 * This class allows the mapping of a specific doctor to a time slot for appointments.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class AppointmentSlot {

    /** Unique identifier of the doctor associated with the time slot. */
    public final String doctorID;

    /** Time slot allocated for the doctor's appointment. */
    public TimeSlot timeSlot;

    /**
     * Constructs a new AppointmentSlot with the specified doctor ID and time slot.
     * @param doctorID Unique identifier for the doctor.
     * @param timeSlot Time slot allocated for the appointment.
     */
    public AppointmentSlot(String doctorID, TimeSlot timeSlot) {
        this.doctorID = doctorID;
        this.timeSlot = timeSlot;
    }

    /**
     * Gets the unique identifier of the doctor associated with the appointment.
     * @return the doctor's ID.
     */
    public String getDoctorID() {
        return this.doctorID;
    }

    /**
     * Gets the time slot allocated for the doctor's appointment.
     * @return the time slot for the appointment.
     */
    public TimeSlot getTimeSlot() {
        return this.timeSlot;
    }

    /**
     * Sets the time slot for the doctor's appointment.
     * @param timeSlot The new time slot for the appointment.
     */
    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}