package src.model;

import src.model.enums.Role;
import java.io.Serializable;

public class Administrator extends User implements Serializable {

    public Administrator(int administratorID, Role role) {
        super(administratorID,role);
    }
}