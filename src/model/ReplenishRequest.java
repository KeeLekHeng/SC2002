package src.model;
import java.io.Serializable;
import src.model.enums.RequestStatus;



public class ReplenishRequest implements Serializable {

    int pharmacistID;
    String medicationName;
    int medicationAmount;
    RequestStatus requestStatus;

    private static final long serialVersionUID = 8L;


    public ReplenishRequest (int pharmacistID, String medicationName, int medicationAmount) {
        this.pharmacistID = pharmacistID;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.requestStatus = RequestStatus.PENDING;
    }

    public int getPharmacistID () {
        return this.pharmacistID;
    }

    public void setPharmacistID (int id) {
        this.pharmacistID = id;
    }

    public String getMedicationName () {
        return this.medicationName;
    }

    public void setMedicationName (String medicationName) {
        this.medicationName = medicationName;
    }

    public int getMedicationAmount () {
        return this.medicationAmount;
    }

    public void setMedicationAmount (int medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    public RequestStatus getRequestStatus () {
        return this.requestStatus;
    }

    public void setRequestStatus (RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
