//Medication in hospital inventory, NOT those prescribed to patients
//need id for each type medication?
//need include checks for users before editing medicine info? like patient cannot simply access


package src.model;
import java.io.Serializable;
import src.model.enums.RequestStatus;;

public class Medication implements Serializable {

    public String name;
    public String medicineID;
    public int stock;
    public int lowStockAlert;
    public RequestStatus replenishRequestStatus;

    private static final long serialVersionUID = 6L;


    public Medication (String name, String medicineID, int stock, int lowStockAlert) {
        this.name = name;
        this.medicineID = medicineID;
        this.stock = stock;
        this.lowStockAlert = lowStockAlert;
        this.replenishRequestStatus = RequestStatus.NA;
    }

    public String getName () {
        return this.name;
    }

    public String getMedicineID(){
        return this.medicineID;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getStock () {
        return this.stock;
    }

    public void setStock (int amount) {
        this.stock = amount;
    }

    public int getLowStockAlert () {
        return this.lowStockAlert;
    }

    public void setLowStockAlert (int alert) {
        this.lowStockAlert = alert;
    }

    public RequestStatus getReplenishRequestStatus () {
        return this.replenishRequestStatus;
    }

    public void setReplenishRequestStatus (RequestStatus replenishRequestStatus) {
        this.replenishRequestStatus = replenishRequestStatus;
    }

    //additional functions
    public void addStock(int amount) {
        this.stock += amount;
    }

    public void removeStock(int amount) {
        this.stock -= amount;
    }
}

