//prescribed medications in outcome record, can have more than one med prescribed so more than one

package src.model;
import java.io.Serializable;


public class PrescribeMedication implements Serializable {
    private String name;
    private int amount;

    private static final long serialVersionUID = 7L;


    public PrescribeMedication (String name, int amount){
        this.name = name;
        this.amount = amount;
    }

    public String getMedicationName() {
        return this.name;
    }

    public int getPrescriptionAmount(){
        return this.amount;
    }

    public void setPrescriptionAmount(int amount){
        this.amount = amount;
    }

    public void setName (String name) {
        this.name = name;
    }
}