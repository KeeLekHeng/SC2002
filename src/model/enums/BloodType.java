package src.model.enums;


public enum BloodType {
 
    A("Type A"),


    B("Type B"),

    O("Type O"),

    AB("Type AB");

    public final String bloodTypeAsStr;


        /**
     * Constructor for the OrderStatus Enum.
     * @param bloodTypeAsStr Order Status as a string
     */

    private BloodType(String bloodTypeAsStr) {
        this.bloodTypeAsStr = bloodTypeAsStr;
    }
}