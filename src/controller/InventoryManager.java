package src.controller;

import java.util.ArrayList;
import src.model.enums.RequestStatus;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.Medication;
import src.model.ReplenishRequest;

/**
 * Manages the inventory of medications, including adding, deleting, updating, and printing details of medications.
 * Handles replenish requests and allows modifications to the stock.
 * @author Benjamin Kam, Kee
 * @version 1.0
 * @since 2024-11-20
 */
public class InventoryManager {

    /**
     * Constructs an InventoryManager instance.
     */
    public InventoryManager() {
    }

    /**
     * Adds a new medication to the inventory.
     * @param name the name of the medication
     * @param stock the stock quantity of the medication
     * @param lowStockAlert the threshold for low stock alert
     */
    public static void addNewMedication(String name, int stock, int lowStockAlert){
        int mid = Helper.generateUniqueId(Database.MEDICATION);
        String medicineID = String.format("M%04d", mid);
        Medication newMedication = new Medication(name.toLowerCase(), medicineID, stock, lowStockAlert);
        Database.MEDICATION.put(medicineID, newMedication);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        System.out.println("New Medication added into database! Medication Details:");
        printMedicationDetails(newMedication);
    }

    /**
     * Deletes a medication from the inventory by its ID.
     * @param medicineID the ID of the medication to be deleted
     * @return true if the medication is successfully deleted, false if not found
     */
    public static boolean deleteMedication(String medicineID){
        Medication removeMedicine = InventoryManager.searchMedicineByID(medicineID);
        if (removeMedicine == null){
            return false;
        }

        printMedicationDetails(removeMedicine);
        if (Helper.promptConfirmation("remove this medicine")) {
            Database.MEDICATION.remove(medicineID);
        } else {
            return false;
        }

        Database.saveFileIntoDatabase(FileType.MEDICATION);
        return true;
    }

    /**
     * Updates the stock or low stock alert of a specific medication.
     * @param medicineID the ID of the medication to be updated
     * @param attributeCode the code representing the attribute to be updated
     * @param newvalue the new value to set
     * @return true if the medication is successfully updated, false if not found
     */
    public static boolean updateMedication(String medicineID, int attributeCode, int newvalue){
        Medication updateMedicine = InventoryManager.searchMedicineByID(medicineID);
        if (updateMedicine == null){
            return false;
        }

        Medication medicationToUpdate = null;
        switch (attributeCode) {
            case 1: 
                medicationToUpdate = Database.MEDICATION.get(medicineID);
                medicationToUpdate.addStock(newvalue);
                break;
            case 2: 
                medicationToUpdate = Database.MEDICATION.get(medicineID);
                medicationToUpdate.removeStock(newvalue);
                break;
            case 3: 
                medicationToUpdate = Database.MEDICATION.get(medicineID);
                medicationToUpdate.setStock(newvalue);
                break;
            case 4:
                medicationToUpdate = Database.MEDICATION.get(medicineID);
                medicationToUpdate.setLowStockAlert(newvalue);
                break;
            default:
                break;
        }

        Database.MEDICATION.put(medicineID, medicationToUpdate);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        return true;
    }

    /**
     * Retrieves all pending replenish requests.
     * @return a list of replenish requests that are in a pending state, or null if none
     */
    public static ArrayList<ReplenishRequest> getPendingRequests(){
        ArrayList<ReplenishRequest> replenishList = new ArrayList<ReplenishRequest>();

        for (ReplenishRequest replenishRequest : Database.REQUESTS.values()){
            if (replenishRequest.getRequestStatus() == RequestStatus.PENDING){
                replenishList.add(replenishRequest);
            }
        }

        if (!replenishList.isEmpty()){
            return replenishList;
        } else {
            System.out.println("No Pending Replenish Requests.");
            return null;
        }
    }

    /**
     * Updates the status of a replenish request.
     * @param requestID the ID of the replenish request to be updated
     * @param attributeCode the code representing the action to take (e.g., accept or decline)
     * @return true if the replenish request is successfully updated
     */
    public static boolean updateReplenishRequests(String requestID, int attributeCode){
        ReplenishRequest requestToUpdate = Database.REQUESTS.get(requestID);     
        Medication medicationToUpdate = null;
        String medicineID = requestToUpdate.getMedicineID();
        int amount;

        switch (attributeCode) {
            case 1:     
                medicationToUpdate = Database.MEDICATION.get(medicineID);
                amount = requestToUpdate.getMedicationAmount();
                medicationToUpdate.addStock(amount);
                requestToUpdate.setRequestStatus(RequestStatus.ACCEPTED);
                break;
            case 2:
                requestToUpdate.setRequestStatus(RequestStatus.DECLINED);
                break;
            case 3:
                break;
            default:
                break;
        }

        Database.MEDICATION.put(medicineID, medicationToUpdate);
        Database.REQUESTS.put(requestID, requestToUpdate);
        Database.saveFileIntoDatabase(FileType.REQUESTS);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        return true;   
    }

    /**
     * Searches for a medication by its ID.
     * @param medicineID the ID of the medication to search for
     * @return the medication object if found, null otherwise
     */
    public static Medication searchMedicineByID(String medicineID){
        if (Database.MEDICATION.containsKey(medicineID)) {
            Medication searchedMedication = Database.MEDICATION.get(medicineID);  
            return searchedMedication;     
        }
        return null;
    }

    /**
     * Prints all medications in the inventory.
     */
    public static void printAllMedication() {
        ArrayList<Medication> medicationList = new ArrayList<Medication>();
        for (Medication medication : Database.MEDICATION.values()) {
            medicationList.add(medication);
        }
        if (!medicationList.isEmpty()){
            System.out.println("Medications in inventory:");
            for (Medication medication : medicationList){
            printMedicationDetails(medication);
            }
        } else {
            System.out.println("No medications in storage");
        }
    }

    /**
     * Initializes dummy medication entries in the inventory.
     */
    public static void initializeDummyMedication() {
        addNewMedication("Ibuprofen", 50, 10);
        addNewMedication("Paracetamol", 100, 20);
        addNewMedication("Amoxicillin", 75, 15);
    }

    /**
     * Prints details of a specific replenish request.
     * @param replenishRequest the replenish request to be printed
     */
    public static void printReplenishRequest(ReplenishRequest replenishRequest){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "PharmacistID", replenishRequest.getPharmacistID()));
        System.out.println(String.format("%-20s: %s", "RequestID", replenishRequest.getRequestID()));
        System.out.println(String.format("%-20s: %s", "Medication Name", replenishRequest.getMedicationName()));
        System.out.println(String.format("%-20s: %s", "Required Stock", replenishRequest.getMedicationAmount()));
        System.out.println(String.format("%-20s: %s", "Current Status", replenishRequest.getRequestStatus()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    /**
     * Prints the details of a specific medication.
     * @param medication the medication to print details for
     */
    public static void printMedicationDetails(Medication medication){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "MedicationID", medication.getMedicineID()));
        System.out.println(String.format("%-20s: %s", "Medication Name", medication.getName()));
        System.out.println(String.format("%-20s: %s", "Current Stock", medication.getStock()));
        System.out.println(String.format("%-20s: %s", "Low Stock Alert", medication.getLowStockAlert()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
}