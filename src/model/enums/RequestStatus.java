package src.model.enums;

/**
 * Enum representing the status of a request for replenishing medications or appointment approval by doctors.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum RequestStatus {
 
    /**
     * The request has been accepted.
     */
    ACCEPTED("Accepted"),

    /**
     * The request has been declined.
     */
    DECLINED("Declined"),

    /**
     * The request is pending.
     */
    PENDING("Pending"),

    /**
     * The request is not available.
     */
    NA("Not Available");

    /**
     * The string representation of the request status.
     */
    public final String requestStatusAsStr;

    /**
     * Constructor for the RequestStatus Enum.
     * @param requestStatusAsStr Request status as a string
     */
    private RequestStatus(String requestStatusAsStr) {
        this.requestStatusAsStr = requestStatusAsStr;
    }
}
