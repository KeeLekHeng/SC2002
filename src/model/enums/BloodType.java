package src.model.enums;

/**
 * Enum representing the different blood types.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum BloodType {

    /**
     * Blood type A.
     */
    A("Type A"),

    /**
     * Blood type B.
     */
    B("Type B"),

    /**
     * Blood type O.
     */
    O("Type O"),

    /**
     * Blood type AB.
     */
    AB("Type AB");

    /**
     * The string representation of the blood type.
     */
    public final String bloodTypeAsStr;

    /**
     * Constructor for the BloodType Enum.
     * @param bloodTypeAsStr The string representation of the blood type.
     */
    private BloodType(String bloodTypeAsStr) {
        this.bloodTypeAsStr = bloodTypeAsStr;
    }
}
