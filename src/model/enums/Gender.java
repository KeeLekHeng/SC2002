package src.model.enums;

/**
 * Enum representing the gender of an individual.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public enum Gender {
 
    /**
     * The individual is female.
     */
    FEMALE("Female"),

    /**
     * The individual is male.
     */
    MALE("Male");

    /**
     * The string representation of the gender.
     */
    public final String genderAsStr;

    /**
     * Constructor for the Gender Enum.
     * @param genderAsStr Gender as a string
     */
    private Gender(String genderAsStr) {
        this.genderAsStr = genderAsStr;
    }
}