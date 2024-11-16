package src.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.AppOutcomeRecord;
import src.model.Appointment;
import src.model.Medication;
import src.model.PrescribeMedication;
import src.model.ReplenishRequest;
import src.model.enums.PrescribeStatus;

public class PrescriptionManager {
    
    public PrescriptionManager()
    {

    }


    //view appointment record one by one by VIEW
    public static void viewRecentAppointmentOutcomeRecord() {
        for (String appointmentID : Database.APPOINTMENT.keySet()) {
            
            Appointment appointment = Database.APPOINTMENT.get(appointmentID);
            AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();

            if (outcomeRecord != null && outcomeRecord.getPrescribeStatus() == PrescribeStatus.PENDING) {
                AppointmentManager.printAppointmentOutcomeRecord(appointment);
            }
        }
    }

    public static void submitReplenishRequest(String pharmacistID, String medicationName, int medicationAmount){


        int rid = Helper.generateUniqueId(Database.REQUESTS);
        String requestID = String.format("R%04d", rid);
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
        boolean errorOccurred = false;  

        // Map to store original medication states for rollback if necessary
        Map<String, Medication> originalMedicationsState = new HashMap<>();
        AppOutcomeRecord appOutcomeRecord = null;
        Appointment currentAppointment = null;

        // Iterate through all AppOutcomeRecords to find the one matching the prescriptionID -> To update prescription status with appointmentID and appoutcomerecords
        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getAppOutcomeRecord() != null){
                AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
                if (outcomeRecord.getPrescriptionID().equals(prescriptionID)) {
                    currentAppointment = appointment;
                    appOutcomeRecord = outcomeRecord;
                    break;
                }
            }
        }
    
        for (PrescribeMedication prescribeMedication : prescriptionList) {
            String medicationName = prescribeMedication.getMedicationName();
            int amount = prescribeMedication.getPrescriptionAmount();
    
            Medication medication = null;
            for (Medication med : Database.MEDICATION.values()) {
                if (med.getName().equalsIgnoreCase(medicationName)) {
                    medication = med;
                    break;
                }
            }
    
            // If the medication is not found, return false and neglect all previous updates
            if (medication == null) {
                System.out.println("Unable to find medication with the name: " + medicationName);
                errorOccurred = true;
                break;  
            }
    
            // Save the original state of the medication to restore later if needed
            originalMedicationsState.put(medication.getMedicineID(), new Medication(medication.getName(), medication.getMedicineID(), medication.getStock(), medication.getLowStockAlert())); 
    
            Medication medicationToUpdate = Database.MEDICATION.get(medication.getMedicineID());

            // Check if stock is sufficient before proceeding to the switch
            if (medicationToUpdate.getStock() < amount) {
                System.out.println("Insufficient stock to prescribe! Current Stock Level: " + medicationToUpdate.getStock());
                errorOccurred = true; 
                break;  
            }
    
            // Switch based on the attribute code (1 for accept, 2 for skip)
            switch (attributeCode) {
                case 1:  
                    medicationToUpdate.removeStock(amount);
    
                    // Check if stock is low and send replenish request
                    if (checkStockLevel(medicationToUpdate)) {
                        System.out.println("Low Stock Level Detected for " + medicationToUpdate.getName() + "! Send Replenish Request Urgently! Current Stock Level: " + medicationToUpdate.getStock());
                    }
                    break;
                case 2:  
                    break;
    
                default:
                    break;
            }
    
            Database.MEDICATION.put(medication.getMedicineID(), medicationToUpdate);
        }
    
        // If there was an error, rollback changes made so far
        if (errorOccurred) {
            System.out.println("Error occurred during prescription update. Rolling back changes...");
            for (Map.Entry<String, Medication> entry : originalMedicationsState.entrySet()) {
                String medicationID = entry.getKey();
                Medication originalMedication = entry.getValue();
                Database.MEDICATION.put(medicationID, originalMedication);  
            }
             return false;  
        }
    
        // Save the updated prescriptions back into the database
        // Update the prescription status to DISPENSED
        appOutcomeRecord.setPrescribeStatus(PrescribeStatus.DISPENSED);
        currentAppointment.setAppOutcomeRecord(appOutcomeRecord);

        Database.APPOINTMENT.put(currentAppointment.getAppointmentID(), currentAppointment);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
        return true;  
    }


    public static boolean checkStockLevel(Medication medication){
        return medication.getStock() <= medication.getLowStockAlert();
    }

    //from pharmacist to doctor
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
        if (!prescribeRequestList.isEmpty()){
            return prescribeRequestList;
            } else {
            System.out.println("No Pending Prescriptions to settle .");
            return null;
        }
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
