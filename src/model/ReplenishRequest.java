package src.model;

import java.io.Serializable;
import java.util.Map;
import src.database.Database;
import src.model.enums.RequestStatus;

/**
 * Represents a replenishment request made by a pharmacist to request additional medication.
 * Contains details about the request, such as the pharmacist's ID, request ID, medication details, and request status.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class ReplenishRequest implements Serializable {

    /** The ID of the pharmacist who made the request. */
    private String pharmacistID;

    /** The unique ID for the replenishment request. */
    private String requestID;

    /** The name of the medication requested for replenishment. */
    private String medicationName;

    /** The amount of the medication requested. */
    private int medicationAmount;

    /** The ID of the medication, fetched from the database. */
    private String medicineID;

    /** The current status of the replenishment request. */
    private RequestStatus requestStatus;

    /** Serialization identifier for the ReplenishRequest class. */
    private static final long serialVersionUID = 8L;

    /**
     * Constructs a new ReplenishRequest with the specified pharmacist ID, request ID, medication name, and medication amount.
     * The medication ID is fetched from the database, and the request status is set to PENDING by default.
     * @param pharmacistID The ID of the pharmacist making the request.
     * @param requestID The unique ID for the replenishment request.
     * @param medicationName The name of the medication requested for replenishment.
     * @param medicationAmount The amount of medication requested for replenishment.
     */
    public ReplenishRequest(String pharmacistID, String requestID, String medicationName, int medicationAmount) {
        this.pharmacistID = pharmacistID;
        this.requestID = requestID;
        this.medicationName = medicationName;
        this.medicationAmount = medicationAmount;
        this.medicineID = fetchMedicineID(medicationName);
        this.requestStatus = RequestStatus.PENDING;
    }

    /**
     * Fetches the medicine ID for the given medication name from the database.
     * @param medicationName The name of the medication.
     * @return The ID of the medication, or null if not found.
     */
    private String fetchMedicineID(String medicationName) {
        for (Map.Entry<String, Medication> entry : Database.MEDICATION.entrySet()) {
            Medication medication = entry.getValue();
            if (medication != null && medication.getName().equalsIgnoreCase(medicationName)) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * Gets the pharmacist ID associated with the replenishment request.
     * @return the pharmacist ID.
     */
    public String getPharmacistID() {
        return this.pharmacistID;
    }

    /**
     * Gets the unique request ID associated with the replenishment request.
     * @return the request ID.
     */
    public String getRequestID() {
        return this.requestID;
    }

    /**
     * Gets the medicine ID associated with the replenishment request.
     * @return the medicine ID.
     */
    public String getMedicineID() {
        return this.medicineID;
    }

    /**
     * Sets the pharmacist ID for the replenishment request.
     * @param id The new pharmacist ID.
     */
    public void setPharmacistID(String id) {
        this.pharmacistID = id;
    }

    /**
     * Gets the name of the medication requested for replenishment.
     * @return the medication name.
     */
    public String getMedicationName() {
        return this.medicationName;
    }

    /**
     * Sets the name of the medication requested for replenishment.
     * @param medicationName The new medication name.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Gets the amount of medication requested for replenishment.
     * @return the medication amount.
     */
    public int getMedicationAmount() {
        return this.medicationAmount;
    }

    /**
     * Sets the amount of medication requested for replenishment.
     * @param medicationAmount The new medication amount.
     */
    public void setMedicationAmount(int medicationAmount) {
        this.medicationAmount = medicationAmount;
    }

    /**
     * Gets the current status of the replenishment request.
     * @return the request status.
     */
    public RequestStatus getRequestStatus() {
        return this.requestStatus;
    }

    /**
     * Sets the status of the replenishment request.
     * @param requestStatus The new request status.
     */
    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}