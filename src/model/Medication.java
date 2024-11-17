package src.model;

import java.io.Serializable;
import src.model.enums.RequestStatus;

/**
 * Represents a medication in the system.
 * A medication has attributes such as name, stock, low stock alert threshold, and replenish request status.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class Medication implements Serializable {

    /** The name of the medication. */
    public String name;

    /** The unique ID of the medication. */
    public String medicineID;

    /** The current stock quantity of the medication. */
    public int stock;

    /** The low stock alert threshold for the medication. */
    public int lowStockAlert;

    /** The replenish request status of the medication. */
    public RequestStatus replenishRequestStatus;

    /** Serialization identifier for the Medication class. */
    private static final long serialVersionUID = 6L;

    /**
     * Constructs a new Medication with the specified attributes.
     * @param name The name of the medication.
     * @param medicineID The unique ID of the medication.
     * @param stock The current stock quantity of the medication.
     * @param lowStockAlert The low stock alert threshold for the medication.
     */
    public Medication(String name, String medicineID, int stock, int lowStockAlert) {
        this.name = name;
        this.medicineID = medicineID;
        this.stock = stock;
        this.lowStockAlert = lowStockAlert;
        this.replenishRequestStatus = RequestStatus.NA;
    }

    /**
     * Gets the name of the medication.
     * @return the name of the medication.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the unique ID of the medication.
     * @return the unique medication ID.
     */
    public String getMedicineID() {
        return this.medicineID;
    }

    /**
     * Sets the name of the medication.
     * @param name The new name for the medication.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the current stock quantity of the medication.
     * @return the stock quantity of the medication.
     */
    public int getStock() {
        return this.stock;
    }

    /**
     * Sets the stock quantity of the medication.
     * @param amount The new stock quantity for the medication.
     */
    public void setStock(int amount) {
        this.stock = amount;
    }

    /**
     * Gets the low stock alert threshold for the medication.
     * @return the low stock alert threshold.
     */
    public int getLowStockAlert() {
        return this.lowStockAlert;
    }

    /**
     * Sets the low stock alert threshold for the medication.
     * @param alert The new low stock alert threshold.
     */
    public void setLowStockAlert(int alert) {
        this.lowStockAlert = alert;
    }

    /**
     * Gets the replenish request status of the medication.
     * @return the replenish request status.
     */
    public RequestStatus getReplenishRequestStatus() {
        return this.replenishRequestStatus;
    }

    /**
     * Sets the replenish request status of the medication.
     * @param replenishRequestStatus The new replenish request status.
     */
    public void setReplenishRequestStatus(RequestStatus replenishRequestStatus) {
        this.replenishRequestStatus = replenishRequestStatus;
    }

    /**
     * Adds stock to the medication.
     * @param amount The amount of stock to add.
     */
    public void addStock(int amount) {
        this.stock += amount;
    }

    /**
     * Removes stock from the medication.
     * @param amount The amount of stock to remove.
     */
    public void removeStock(int amount) {
        this.stock -= amount;
    }
}
