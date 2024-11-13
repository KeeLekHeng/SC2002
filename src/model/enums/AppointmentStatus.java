package src.model.enums;

/**
 * Enum representing the different statuses an appointment can have.
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum AppointmentStatus {
 
    /**
     * The appointment is confirmed.
     */
    CONFIRMED("Confirmed"),

    /**
     * The appointment is canceled.
     */
    CANCELED("Canceled"),

    /**
     * The appointment is completed.
     */
    COMPLETED("Completed"),

    /**
     * The appointment is pending.
     */
    PENDING("Pending");

    /**
     * The string representation of the appointment status.
     */
    public final String appointmentStatusAsStr;

    /**
     * Constructor for the AppointmentStatus Enum.
     *
     * @param appointmentStatusTypeAsStr The string representation of the appointment status.
     */
    private AppointmentStatus(String appointmentStatusTypeAsStr) {
        this.appointmentStatusAsStr = appointmentStatusTypeAsStr;
    }
}