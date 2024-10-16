package src.model.enums;


public enum EmploymentStatus {
 
    EMPLOYED("Employed"),


    REMOVED("Removed");


    public final String employmentStatusAsStr;


        /**
     * Constructor for the OrderStatus Enum.
     * @param employmentStatusAsStr Order Status as a string
     */

    private EmploymentStatus(String employmentStatusAsStr) {
        this.employmentStatusAsStr = employmentStatusAsStr;
    }
}