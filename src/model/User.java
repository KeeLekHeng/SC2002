package src.model;
import java.io.Serializable;
import src.model.enums.Role;


public class User implements Serializable {
    public final int id;
    public final int age;
    private String password;

    private final Role role;

    public User (int id, int age, Role role)
    {
        this.id = id;
        this.age = age;
        password = "password";
        this.role = role;
    }

    //security need enhance??
    public boolean setPassword(String password)
    {
        this.password = password;
        return true; //return true if successfull
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

