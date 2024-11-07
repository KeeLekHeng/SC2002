//used to Replenish Medications in Inventory, and for doctors to accept/decline appointments

package src.model.enums;

public enum RequestStatus {
 
    ACCEPTED("Accepted"),

    DECLINED("Declined"),

    PENDING("Pending"),

    NA("Not Available");


    public final String requestStatusAsStr;

        /**
     * Constructor for the OrderStatus Enum.
     * @param requestStatusAsStr Order Status as a string
     */

    private RequestStatus(String requestStatusAsStr) {
        this.requestStatusAsStr = requestStatusAsStr;
    }
}