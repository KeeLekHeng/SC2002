//prescribed medications in outcome record, can have more than one med prescribed so more than one

package src.model;
import java.io.Serializable;
import src.model.enums.PrescribeStatus;;

public class PrescribeMedication implements Serializable {
    private String name;
    //private final String prescriptionID;
    private int amount;
    private PrescribeStatus prescribeStatus;

    private static final long serialVersionUID = 7L;


    public PrescribeMedication (String name, int amount, String prescriptionID){
        //this.prescriptionID = prescriptionID;
        this.name = name;
        this.amount = amount;
        this.prescribeStatus = PrescribeStatus.PENDING;
    }

    public String getMedicationName() {
        return this.name;
    }

    /*public String getPrescriptionID() {
        return this.prescriptionID;
    }
        */

    public int getPrescriptionAmount(){
        return this.amount;
    }

    public void setPrescriptionAmount(int amount){
        this.amount = amount;
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