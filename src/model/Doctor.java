package src.model;

import src.model.enums.Role;
import java.io.Serializable;

public class Doctor extends User implements Serializable {

    public Doctor(int doctorID, Role role) {
        super(doctorID,role);
    }
}