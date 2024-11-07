package src.model;

import src.model.enums.Role;
import java.io.Serializable;

public class Pharmacist extends User implements Serializable {

    public Pharmacist(int pharmacistID, Role role) {
        super(pharmacistID,role);
    }
}