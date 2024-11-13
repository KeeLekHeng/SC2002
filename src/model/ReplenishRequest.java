package src.model;
import java.io.Serializable;
import src.database.Database;
import src.model.enums.RequestStatus;



public class ReplenishRequest implements Serializable {

    String pharmacistID;
    String requestID;
    String medicationName;
    int medicationAmount;
    String medicineID;
    RequestStatus requestStatus;

    private static final long serialVersionUID = 8L;


    public ReplenishRequest(String pharmacistID, String requestID, String medicationName, int medicationAmount) {
        this.pharmacistID = pharmacistID;
        this.requestID = requestID;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.medicineID = fetchMedicineID(medicationName); 
        this.requestStatus = RequestStatus.PENDING;
    }

    private String fetchMedicineID(String medicationName) {
        Medication medication = Database.MEDICATION.get(medicationName.toLowerCase());
        return (medication != null) ? medication.getMedicineID() : null;
    }

    public String getPharmacistID () {
        return this.pharmacistID;
    }

    public String getRequestID(){
        return this.requestID;
    }

    public String getMedicineID(){
        return this.medicineID;
    }

    public void setPharmacistID (String id) {
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
