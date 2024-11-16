package src.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Scanner;

import src.model.enums.PrescribeStatus;
import src.model.enums.RequestStatus;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.Medication;
import src.model.PrescribeMedication;
import src.model.ReplenishRequest;

import src.model.enums.AppointmentStatus;
import src.model.enums.RequestStatus;

//import the views if needed

public class InventoryManager {

    public InventoryManager()
    {

    }

    public static void addNewMedication(String name, int stock, int lowStockAlert){
        int mid = Helper.generateUniqueId(Database.MEDICATION);
        String medicineID = String.format("M%04d", mid);
        Medication newMedication = new Medication(name.toLowerCase(), medicineID, stock, lowStockAlert);

        //Do we need a unique medID? if need make a new medID generator then update all functions
        Database.MEDICATION.put(medicineID, newMedication);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        System.out.println("New Medication added into database! Medication Details:");
        printMedicationDetails(newMedication);
        
    }

    //REMOVE
    public static boolean deleteMedication(String medicineID){
        Medication removeMedicine = InventoryManager.searchMedicineByID(medicineID);
        if (removeMedicine == null){
            //not found in database
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
    
    public static boolean updateMedication(String medicineID, int attributeCode, int newvalue){
        Medication updateMedicine = InventoryManager.searchMedicineByID(medicineID);
        if (updateMedicine == null){
            //not found in database
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

    //Save to database at the end (Try see if works)
    Database.MEDICATION.put(medicineID, medicationToUpdate);
    Database.saveFileIntoDatabase(FileType.MEDICATION);
    return true; 
    } 

    public static ArrayList<ReplenishRequest> getPendingRequests(){
        ArrayList<ReplenishRequest> replenishList = new ArrayList<ReplenishRequest>();

        //copy
        for (ReplenishRequest replenishRequest : Database.REQUESTS.values()){
            if (replenishRequest.getRequestStatus() == RequestStatus.PENDING){
                replenishList.add(replenishRequest);
            }
        }
        return replenishList;
    }

    //approve or reject (ID or Reuqest)
    public static boolean updateReplenishRequests(String requestID, int attributeCode){

        ReplenishRequest requestToUpdate = Database.REQUESTS.get(requestID);  
        String medicationName = requestToUpdate.getMedicationName();     
        Medication medicationToUpdate = null;
        String medicineID = requestToUpdate.getMedicineID();
        int amount;

        switch (attributeCode) {
            case 1:     
                //accept and add stock
                medicationToUpdate = Database.MEDICATION.get(medicineID);
                amount = requestToUpdate.getMedicationAmount();
                medicationToUpdate.addStock(amount);
                requestToUpdate.setRequestStatus(RequestStatus.ACCEPTED);
                break;
            case 2:
                //reject request
                requestToUpdate.setRequestStatus(RequestStatus.DECLINED);
                break;
            case 3:
                break;
            default:
                break;
        }

        //save to both REQUESTS and MEDICINES to Database
        Database.MEDICATION.put(medicineID, medicationToUpdate);
        Database.REQUESTS.put(requestID, requestToUpdate);
        Database.saveFileIntoDatabase(FileType.REQUESTS);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        return true;
        
    }

    

    public static Medication searchMedicineByID(String medicineID){
        if (Database.MEDICATION.containsKey(medicineID)) {
            Medication searchedMedication = Database.MEDICATION.get(medicineID);  
            return searchedMedication;     
        }
        return null;
    }


    //no sorting for medication? (can implement if want any sorting)
    public static void printAllMedication() {
        //copy
        ArrayList<Medication> medicationList = new ArrayList<Medication>();
        //  copy
        for (Medication medication : Database.MEDICATION.values()) {
            medicationList.add(medication);
        }

        //print
        for (Medication medication : medicationList) {
            printMedicationDetails(medication);
        }
    }


    public static void initializeDummyMedication() {
        InventoryManager.addNewMedication("Ibuprofen", 50, 10);
        InventoryManager.addNewMedication("Paracetamol", 100, 20);
        InventoryManager.addNewMedication("Amoxicillin", 75, 15);
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

    public static void printMedicationDetails(Medication medication){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "MedicationID", medication.getMedicineID()));
        System.out.println(String.format("%-20s: %s", "Medication Name", medication.getName()));
        System.out.println(String.format("%-20s: %s", "Current Stock", medication.getStock()));
        System.out.println(String.format("%-20s: %s", "Low Stock Alert", medication.getLowStockAlert()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
    
}


