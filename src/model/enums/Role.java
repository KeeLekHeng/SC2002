package src.model.enums;


public enum Role {
 
    PATIENT("Patient"),


    DOCTOR("Doctor"),


    PHARMACIST("Pharmacist"),


    ADMINISTRATOR("Administrator");

    /**
     * A String value for the roles for retrieving purposes.
     */
    public final String RoleAsStr;
    
    /**
     * Constructor for the RoomType Enum.
     * @param RoleAsStr Room type as a string.
     * 
     */

    private Role(String RoleAsStr) {
        this.RoleAsStr = RoleAsStr;
    }
}