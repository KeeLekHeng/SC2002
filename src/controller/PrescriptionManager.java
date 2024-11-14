package src.controller;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;
import src.controller.InventoryManager;
import src.controller.AppointmentManager;

import src.database.Database;
import src.database.FileType;
import src.helper.Helper;

import src.model.ReplenishRequest;
import src.model.Medication;
import src.model.PrescribeMedication;
import src.model.enums.RequestStatus;

import src.database.Database;
import src.database.FileType;
import src.model.AppOutcomeRecord;
import src.model.Appointment;
import src.model.enums.PrescribeStatus;

public class PrescriptionManager {
    
    public PrescriptionManager()
    {

    }


    //view appointment record one by one by VIEW
    public static void viewAppointmentOutcomeRecord(String appointmentID){
        //AppointmentManager.printAppointmentOutcomeRecord();
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

    //Must fulfill all medicine in a prescription or else not allowed
    public boolean updatePrescriptionStatus(String prescriptionID, int attributeCode){
        
        if (searchPrescriptionById(prescriptionID) == null) {
            // prescription not found
            return false;
        }

        List<PrescribeMedication> prescriptionList = Database.PRESCRIPTION.get(prescriptionID);
        
        for (PrescribeMedication prescribeMedication : prescriptionList) {
            String medicationName = prescribeMedication.getMedicationName();
            int amount = prescribeMedication.getPrescriptionAmount();
    
            // Find the corresponding medication in the database
            Medication medication = null;
            for (Medication med : Database.MEDICATION.values()) {
                if (med.getName().equalsIgnoreCase(medicationName)) {
                    medication = med;
                    break;
                } else {
                    //one med cant find then ELSE return false;
                    System.out.println("Unable to find medication with the name:" + medicationName);
                    return false;
                }          
            }
    
    
            // Check if stock is sufficient before proceeding to the switch
            Medication medicationToUpdate = Database.MEDICATION.get(medication.getMedicineID());
            if (medicationToUpdate.getStock() < amount) {
                System.out.println("Insufficient stock to prescribe! Current Stock Level: " + medicationToUpdate.getStock());
                return false;  
            }
    
            // Switch based on the attribute code (1 for accept, 2 for skip)
            switch (attributeCode) {
                case 1:  
                    // Remove the stock from the inventory
                    medicationToUpdate.removeStock(amount);
    
                    // Check if stock is low and send replenish request
                    if (checkStockLevel(medicationToUpdate)) {
                        System.out.println("Low Stock Level Detected for " + medicationToUpdate.getName() + "! Send Replenish Request Urgently! Current Stock Level: " + medicationToUpdate.getStock());
                    }
    
                    // Update the prescription status to DISPENSED
                    prescribeMedication.setPrescribeStatus(PrescribeStatus.DISPENSED);
                    break;
    
                case 2:  
                    break;
    
                default:
                    break;
            }
        }
    
        // Save the updated data back into the database
        Database.PRESCRIPTION.put(prescriptionID, prescriptionList);
        Database.saveFileIntoDatabase(FileType.PRESCRIPTIONS);
        return true;
    }


    public static boolean checkStockLevel(Medication medication){
        if (medication.getStock() <= medication.getLowStockAlert()){
            return true;
        } else {
            return false;
        }
    }

    public static ArrayList<List<PrescribeMedication>> getPendingRequests(){
        ArrayList<List<PrescribeMedication>> prescribeRequestList = new ArrayList<>();

        //copy
        for (Appointment appointment : Database.APPOINTMENT.values()){
            if(appointment.getAppOutcomeRecord() != null){
                AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
                if(outcomeRecord.getPrescribeStatus() == PrescribeStatus.PENDING){
                    prescribeRequestList.add(outcomeRecord.getPrescribeMedications());
                }
            }
        }

        return prescribeRequestList;
    }

    public static List<PrescribeMedication> searchPrescriptionById(String prescriptionID) {
        List<PrescribeMedication> prescription = null;
        if (Database.PRESCRIPTION.containsKey(prescriptionID)) {
            prescription = Database.PRESCRIPTION.get(prescriptionID);
        }
        return prescription;
            
    }

    public static void viewMedicationInventory(){
        InventoryManager.printAllMedication();
    }

  
    public static void printPrescriptionRequest(AppOutcomeRecord outcomeRecord){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "PrescriptionID", outcomeRecord.getPrescriptionID()));
        System.out.println(String.format("%-20s: %s", "Prescription Status", outcomeRecord.getPrescribeStatus()));
       
        for (PrescribeMedication prescribeMedication : outcomeRecord.getPrescribeMedications()) {
            System.out.println(String.format("%-20s: %s", "Medication Name", prescribeMedication.getMedicationName()));
            System.out.println(String.format("%-20s: %s", "Amount", prescribeMedication.getPrescriptionAmount()));
            System.out.println(String.format("%-40s", "").replace(" ", "-"));
        }
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
