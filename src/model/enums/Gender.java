package src.model.enums;


public enum Gender {
 
    FEMALE("Female"),


    MALE("Male");


    public final String genderAsStr;


        /**
     * Constructor for the OrderStatus Enum.
     * @param genderAsStr Order Status as a string
     */

    private Gender(String genderAsStr) {
        this.genderAsStr = genderAsStr;
    }
}
