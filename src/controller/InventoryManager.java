package src.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import src.model.enums.PrescribeStatus;
import src.model.enums.RequestStatus;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.Medication;
import src.model.PrescribeMedication;
import src.model.MedicationInventory;

//import the views if needed

public class InventoryManager {

    public InventoryManager()
    {

    }

    public static void addNewMedication(String name, int stock, int lowStockAlert){
        Medication newMedication = new Medication(name.toLowerCase(), stock, lowStockAlert);

        //Do we need a unique medID? if need make a new medID generator then update all functions
        Database.MEDICINES.put(newMedication);
        Database.saveFileIntoDatabase(FileType.MEDICINES);
        System.out.println("New Medication added into database! Medication Details:");
        printMedicationDetails(newMedication);
        
    }

    //REMOVE
    public static boolean removeMedication(String name){
        List<Medication> removeList = InventoryManager.searchMedicineByName(name.toLowerCase());
        if (removeList.size() == 0){
            //not found in database
            return false;
        }

        for (Medication medication : removeList){
            printMedicationDetails(medication);
            if (Helper.promptConfirmation("remove this medicine")) {
                Database.MEDICINES.remove(name);
            } else {
                return false;
            }
        }
        Database.saveFileIntoDatabase(FileType.MEDICINES);
        return true;
    }
    
    public static boolean updateMedication(String name, int attributeCode, int newvalue){
        List<Medication> updateList = InventoryManager.searchMedicineByName(name.toLowerCase());
        if (updateList.size() == 0){
            //not found in database
            return false;
        }
        for (Medication medication : updateList) {
            Medication medicationToUpdate;
            switch (attributeCode) {
                case 1: 
                    medicationToUpdate = Database.MEDICINES.get(name.toLowerCase())
                    medicationToUpdate.setStock(newvalue);
                    Database.MEDICINES.put(medication.getName(), medicationToUpdate);
                    break;
                case 2:
                    medicationToUpdate = Database.MEDICINES.get(name.toLowerCase())
                    medicationToUpdate.setLowStockAlert(newvalue);
                    Database.MEDICINES.put(medication.getName(), medicationToUpdate);
                    break;
                default:
                    break;
            }
        }
        Database.saveFileIntoDatabase(FileType.MEDICINES);
        return true; 
    }


    public static List<Medication> searchMedicineByName(String name){
        List<Medication> searchList = new List<Medication>();
        if (Database.MEDICINES.containsKey(name.toLowerCase())) {
            Medication searchedMedication = Database.MEDICINES.get(name);       //no tolowercase
            searchList.add(searchedMedication);
        }
        return searchList;
    }

    public static void initializeDummyMedication() {
        InventoryManager.addNewMedication("Ibuprofen", 50, 10);
        InventoryManager.addNewMedication("Paracetamol", 100, 20);
        InventoryManager.addNewMedication("Amoxicillin", 75, 15);
    }
    public static void printMedicationDetails(Medication medication){
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Medication Name", medication.getName()));
        System.out.println(String.format("%-20s: %s", "Current Stock", medication.getStock()));
        System.out.println(String.format("%-20s: %s", "Low Stock Alert", medication.getLowStockAlert()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
    
}



/* 
Database.MEDICINES.put(patientID, newPatient);
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        System.out.println("Patient Created! Patient Details: ");
        printPatientDetails(newPatient);

        */