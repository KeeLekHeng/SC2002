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

    public boolean updatePrescriptionStatus(String prescriptionID, int attributeCode){
        
        if (searchPrescriptionById(prescriptionID) == null) {
            // prescription not found
            return false;
        }

        PrescribeMedication prescription = Database.PRESCRIPTION.get(prescriptionID);
        String medicationName = prescription.getMedicationName();
        int amount;

        //find the medication in prescription
        Medication medication = null;
        for (Medication med : Database.MEDICATION.values()) {
        if (med.getName().toLowerCase().equals(medicationName.toLowerCase())) {
        medication = med;
        break;
    } else {
        return false;
    }

    }
        Medication medicationToUpdate = null;
        switch(attributeCode){
            //1 is accept, 2 is skip
            //if stock too low then trigger alert
            //if not enough stock, then dont approve prescription
            case 1:
                medicationToUpdate = Database.MEDICATION.get(medication.getMedicineID());
                amount = prescription.getPrescriptionAmount();

                if(amount > medicationToUpdate.getStock()){
                    int stockLevel = medicationToUpdate.getStock(); 
                    System.out.println("Insufficient stock to prescribe! Current Stock Level: " + stockLevel);
                    return false;
                }
                

                medicationToUpdate.removeStock(amount);
                if (checkStockLevel(medicationToUpdate)){
                    int stockLevel = medicationToUpdate.getStock(); 
                    System.out.println("Low Stock Level Detected! Send Replenish Request Urgently! Current Stock Level: " + stockLevel);
                }

                prescription.setPrescribeStatus(PrescribeStatus.DISPENSED);
                break;
            case 2:
                break;
            default:
                break;
        }
        
        //save and put into database                                                                            //initialized medicationTOUPDATE to null
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
