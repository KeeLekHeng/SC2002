package src.model.enums;


public enum AppointmentStatus {
 
    CONFIRMED("Confirmed"),

    CANCELED("Canceled"),

    COMPLETED("Completed"),

    PENDING("Pending");


    public final String appointmentStatusAsStr;


        /**
     * Constructor for the OrderStatus Enum.
     * @param appointmentStatusAsStr Order Status as a string
     */

    private AppointmentStatus(String appointmentStatusTypeAsStr) {
        this.appointmentStatusAsStr = appointmentStatusTypeAsStr;
    }
}