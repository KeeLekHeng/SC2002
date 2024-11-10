package src.controller;

import java.awt.HeadlessException;
import java.util.ArrayList;

import src.database.Database;
import src.database.FileType;
import src.helper.Helper;

import src.model.ReplenishRequest;
import src.model.Medication;
import src.model.PrescribeMedication;
import src.model.enums.RequestStatus;

import src.database.Database;
import src.database.FileType;
import src.model.enums.PrescribeStatus;

public class PrescriptionManager {
    
    public PrescriptionManager()
    {

    }


    //view appointment record one by one by VIEW
    public static void viewAppointmentOutcomeRecord(String appointmentID){
        AppointmentManager.printAppointmentOutcomeRecord();
    }

    public static void submitReplenishRequest(String pharmacistID, String medicationName, int medicationAmount){


        int rid = Helper.generateUniqueId(Database.REQUESTS);
        String requestID = String.format("R%05d", rid);
        ReplenishRequest replenishRequest = new ReplenishRequest(pharmacistID, requestID, medicationName, medicationAmount);

        Database.REQUESTS.put(requestID, replenishRequest);
        Database.saveFileIntoDatabase(FileType.REQUESTS);
        System.out.println("New Replenish Request added into database! Request Details:");
        printReplenishRequest(replenishRequest);
    }

    public boolean updatePrescriptionStatus(String prescriptionID, int attributeCode) {
     
        PrescribeMedication prescription = Database.PRESCRIPTION.get(prescriptionID);
        if (prescription == null) {
            // Prescription not found
            return false;
        }
        
        String medicationName = prescription.getMedicationName();
        int amount;
   
        // Find the medication in the database
        Medication medication = null;
        for (Medication med : Database.MEDICATION.values()) {
            if (med.getName().equalsIgnoreCase(medicationName)) {
                medication = med;
                break;
            }
        }
        
        if (medication == null) {
            // Medication not found
            return false;
        }
   
        Medication medicationToUpdate = null;
        switch (attributeCode) {
            case 1: // Accept prescription
                medicationToUpdate = Database.MEDICATION.get(medication.getMedicineID());
                amount = prescription.getPrescriptionAmount();
   
                if (amount > medicationToUpdate.getStock()) {
                    System.out.println("Insufficient stock to prescribe! Current Stock Level: " + medicationToUpdate.getStock());
                    return false;
                }
   
                medicationToUpdate.removeStock(amount);
                if (checkStockLevel(medicationToUpdate)) {
                    System.out.println("Low Stock Level Detected! Send Replenish Request Urgently! Current Stock Level: " + medicationToUpdate.getStock());
                }
   
                prescription.setPrescribeStatus(PrescribeStatus.DISPENSED);
                break;
            case 2: // Skip prescription
                break;
            default:
                return false;
        }
   
        // Save changes to the database
        Database.PRESCRIPTION.put(prescriptionID, prescription);
        Database.MEDICATION.put(medication.getMedicineID(), medicationToUpdate);
        Database.saveFileIntoDatabase(FileType.PRESCRIPTIONS);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        return true;
    }

    public static boolean checkStockLevel(Medication medication){
        if (medication.getStock() <= medication.getLowStockAlert()){
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<PrescribeMedication> getPendingRequests(){
        ArrayList<PrescribeMedication> prescribeRequestList = new ArrayList<PrescribeMedication>();

        //copy
        for (PrescribeMedication prescribeMedication : Database.PRESCRIPTION.values()){
            if (prescribeMedication.getPrescribeStatus() == PrescribeStatus.PENDING){
                prescribeRequestList.add(prescribeMedication);
            }
        }
        return prescribeRequestList;
    }

    public static PrescribeMedication searchPrescriptionById(String prescriptionID) {
        PrescribeMedication prescription = null;
        if (Database.PRESCRIPTION.containsKey(prescriptionID)) {
            prescription = Database.PRESCRIPTION.get(prescriptionID);
        }
        return prescription;
            
    }

    public static void viewMedicationInventory(){
        InventoryManager.printAllMedication();
    }

    //send an alert when logged in and stock levels are low? or when prescribe and stock levels hit below
    public static void printPrescriptionRequest(PrescribeMedication prescribeMedication){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "PrescriptionID", prescribeMedication.getPrescriptionID()));
        System.out.println(String.format("%-20s: %s", "Medication Name", prescribeMedication.getMedicationName()));
        System.out.println(String.format("%-20s: %s", "Current Status", prescribeMedication.getPrescribeStatus()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
    
    public static void printReplenishRequest(ReplenishRequest replenishRequest){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "PharmacistID", replenishRequest.getPharmacistID()));
        System.out.println(String.format("%-20s: %s", "RequestID", replenishRequest.getRequestID()));
        System.out.println(String.format("%-20s: %s", "Medication Name", replenishRequest.getMedicationName()));
        System.out.println(String.format("%-20s: %s", "Required Stock", replenishRequest.getMedicationAmount()));
        System.out.println(String.format("%-20s: %s", "Current Status", replenishRequest.getRequestStatus()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
    
}