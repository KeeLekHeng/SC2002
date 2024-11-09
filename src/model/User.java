package src.model;
import java.io.Serializable;
import src.model.enums.Role;


public class User implements Serializable {
    public final String id;
    private String password;
    private final Role role;

    private static final long serialVersionUID = 1L;

    public User (String id, String password, Role role)
    {
        this.id = id;
        this.password = password;
        this.role = role;
    }

    public String getId () {
        return this.id;
    }

 
    ////////security need enhance??
    public String getPassword () {
        return this.password;
    }

    public boolean setPassword(String password)
    {
        this.password = password;
        return true; //return true if successfull
    }
    /////////////

    public Role getRole () {
        return this.role;
    }

    public boolean isPatient() {
        return this.role == Role.PATIENT;    //comparing directly with enum
    }

    public boolean isDoctor() {
        return this.role == Role.DOCTOR;    //comparing directly with enum
    }

    public boolean isPharmacist() {
        return this.role == Role.PHARMACIST;    //comparing directly with enum
    }

    public boolean isAdministrator() {
        return this.role == Role.ADMINISTRATOR;    //comparing directly with enum
    }
}

