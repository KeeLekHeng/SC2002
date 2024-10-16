//used for Pharmacists to Approve Medicaitons to Patients
package src.model.enums;

public enum PrescribeStatus {

    PENDING("Pending"),

    DISPENSED("Dispensed");


    public final String prescribedStatusAsStr;

        /**
     * Constructor for the OrderStatus Enum.
     * @param prescribedStatusAsStr Order Status as a string
     */

    private PrescribeStatus(String prescribedStatusAsStr) {
        this.prescribedStatusAsStr = prescribedStatusAsStr;
    }
}