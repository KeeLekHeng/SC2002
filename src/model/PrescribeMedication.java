//prescribed medications in outcome record, can have more than one med prescribed so more than one

package src.model;
import java.io.Serializable;
import src.model.enums.PrescribeStatus;;

public class PrescribeMedication implements Serializable {
    private String name;
    private PrescribeStatus prescribeStatus;

    public PrescribeMedication (String name){
        this.name = name;
        this.prescribeStatus = PrescribeStatus.PENDING;
    }

    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public PrescribeStatus getPrescribeStatus () {
        return this.prescribeStatus;
    }

    public void setPrescribeStatus (PrescribeStatus prescribeStatus) {
        this.prescribeStatus = prescribeStatus;
    }
}