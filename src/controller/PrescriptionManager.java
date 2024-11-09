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
        PrescribeMedication prescription = Database.PRESCRIPTION.get(prescriptionID);
        Medication medication = Database.MEDICATION.get(medicationName.toLowerCase());
        int amount;

        switch(attributeCode){
            //1 is accept, 2 is reject, 3 is skip
            case 1:
                break;
            case 2:
                break;
            case 3:
                break;
            default:
                break;
        }

        return true;
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

    /*
     * public static boolean updateReplenishRequests(String requestID, int attributeCode){

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
     */


    //send an alert when logged in and stock levels are low? or when prescribe and stock levels hit below
    public void printPrescriptionRequest(PrescribeMedication prescribeMedication){
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
