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
import src.model.enums.AppointmentStatus;
import src.model.enums.PrescribeStatus;

/**
 * Manages various prescription-related functionalities such as updating
 * prescription status,
 * submitting replenish requests, viewing recent prescription outcomes, and
 * checking stock levels.
 * 
 * @author Benjamin, Kee
 * @version 1.0
 * @since 2024-11-21
 */
public class PrescriptionManager {

    public PrescriptionManager() {
    }

    /**
     * Views the most recent appointment outcomes that have a pending prescription.
     * 
     * @return true if there are pending prescriptions; false otherwise
     */
    public static boolean viewRecentAppointmentOutcomeRecord() {
        int success = 0;
        if (Database.APPOINTMENT.isEmpty()) {
            return false;
        }
        for (String appointmentID : Database.APPOINTMENT.keySet()) {
            Appointment appointment = Database.APPOINTMENT.get(appointmentID);
            AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
            if (outcomeRecord != null && outcomeRecord.getPrescribeStatus() == PrescribeStatus.PENDING) {
                AppointmentManager.printAppointmentOutcomeRecord(appointment);
                success++;
            }
        }
        return success != 0;
    }

    /**
     * Submits a replenish request for medication.
     * 
     * @param pharmacistID     the ID of the pharmacist
     * @param medicationName   the name of the medication to replenish
     * @param medicationAmount the amount of medication to replenish
     */
    public static void submitReplenishRequest(String pharmacistID, String medicationName, int medicationAmount) {
        int rid = Helper.generateUniqueId(Database.REQUESTS);
        String requestID = String.format("R%04d", rid);
        ReplenishRequest replenishRequest = new ReplenishRequest(pharmacistID, requestID, medicationName,
                medicationAmount);
        Database.REQUESTS.put(requestID, replenishRequest);
        Database.saveFileIntoDatabase(FileType.REQUESTS);
        System.out.println("New Replenish Request added into database! Request Details:");
        printReplenishRequest(replenishRequest);
    }

    /**
     * Updates the status of a prescription and processes it based on the specified
     * action.
     * 
     * @param prescriptionID the ID of the prescription
     * @param attributeCode  the action to take (1 for accept, 2 for skip)
     * @return true if the prescription status was successfully updated; false
     *         otherwise
     */
    public boolean updatePrescriptionStatus(String prescriptionID, int attributeCode) {
        if (searchPrescriptionById(prescriptionID) == null) {
            return false;
        }
        List<PrescribeMedication> prescriptionList = Database.PRESCRIPTION.get(prescriptionID);
        boolean errorOccurred = false;

        Map<String, Medication> originalMedicationsState = new HashMap<>();
        AppOutcomeRecord appOutcomeRecord = null;
        Appointment currentAppointment = null;

        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getAppOutcomeRecord() != null) {
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
            if (medication == null) {
                System.out.println("Unable to find medication with the name: " + medicationName);
                errorOccurred = true;
                break;
            }
            originalMedicationsState.put(medication.getMedicineID(), new Medication(medication.getName(),
                    medication.getMedicineID(), medication.getStock(), medication.getLowStockAlert()));
            Medication medicationToUpdate = Database.MEDICATION.get(medication.getMedicineID());

            if (medicationToUpdate.getStock() < amount) {
                System.out.println(
                        "Insufficient stock to prescribe! Current Stock Level: " + medicationToUpdate.getStock());
                errorOccurred = true;
                break;
            }

            switch (attributeCode) {
                case 1:
                    medicationToUpdate.removeStock(amount);
                    if (checkStockLevel(medicationToUpdate)) {
                        System.out.println("Low Stock Level Detected for " + medicationToUpdate.getName()
                                + "! Send Replenish Request Urgently! Current Stock Level: "
                                + medicationToUpdate.getStock());
                    }
                    break;
                case 2:
                    break;
                default:
                    break;
            }
            Database.MEDICATION.put(medication.getMedicineID(), medicationToUpdate);
        }

        if (errorOccurred) {
            System.out.println("Error occurred during prescription update. Rolling back changes...");
            for (Map.Entry<String, Medication> entry : originalMedicationsState.entrySet()) {
                String medicationID = entry.getKey();
                Medication originalMedication = entry.getValue();
                Database.MEDICATION.put(medicationID, originalMedication);
            }
            return false;
        }

        appOutcomeRecord.setPrescribeStatus(PrescribeStatus.DISPENSED);
        currentAppointment.setAppOutcomeRecord(appOutcomeRecord);
        Database.APPOINTMENT.put(currentAppointment.getAppointmentID(), currentAppointment);
        Database.saveFileIntoDatabase(FileType.MEDICATION);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
        return true;
    }

    /**
     * Checks if the stock level of a medication is below the alert threshold.
     * 
     * @param medication the medication to check
     * @return true if the stock is low; false otherwise
     */
    public static boolean checkStockLevel(Medication medication) {
        return medication.getStock() <= medication.getLowStockAlert();
    }

    /**
     * Retrieves all pending prescriptions.
     * 
     * @return a list of pending prescriptions
     */
    public static ArrayList<List<PrescribeMedication>> getPendingRequests() {
        ArrayList<List<PrescribeMedication>> prescribeRequestList = new ArrayList<>();
        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getAppOutcomeRecord() != null) {
                AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
                if (outcomeRecord.getPrescribeStatus() == PrescribeStatus.PENDING) {
                    prescribeRequestList.add(outcomeRecord.getPrescribeMedications());
                }
            }
        }
        if (!prescribeRequestList.isEmpty()) {
            return prescribeRequestList;
        } else {
            System.out.println("No Pending Prescriptions to settle.");
            return null;
        }
    }

    /**
     * Searches for a prescription by its ID.
     * 
     * @param prescriptionID the ID of the prescription
     * @return the prescription list if found; null otherwise
     */
    public static List<PrescribeMedication> searchPrescriptionById(String prescriptionID) {
        return Database.PRESCRIPTION.get(prescriptionID);
    }

    /**
     * Views the inventory of medications.
     */
    public static void viewMedicationInventory() {
        InventoryManager.printAllMedication();
    }

    /**
     * Prints all pending prescriptions.
     * 
     * @return true if there are pending prescriptions; false otherwise
     */
    public static boolean printAllPrescriptions() {
        boolean hasPendingPrescriptions = false;
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println("Printing all prescriptions that have not been dispensed:");
        System.out.println(String.format("%-40s", "").replace(" ", "-"));

        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getAppointmentStatus() == AppointmentStatus.COMPLETED) {
                AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
                if (outcomeRecord != null && outcomeRecord.getPrescriptionID() != null) {
                    String prescriptionID = outcomeRecord.getPrescriptionID();
                    List<PrescribeMedication> medications = outcomeRecord.getPrescribeMedications();
                    if (outcomeRecord.getPrescribeStatus() != PrescribeStatus.DISPENSED) {
                        hasPendingPrescriptions = true;
                        System.out.println(String.format("%-20s: %s", "Prescription ID", prescriptionID));
                        for (PrescribeMedication medication : medications) {
                            System.out.println(
                                    String.format("%-20s: %s", "Medication Name", medication.getMedicationName()));
                            System.out
                                    .println(String.format("%-20s: %d", "Amount", medication.getPrescriptionAmount()));
                            System.out.println(String.format("%-40s", "").replace(" ", "-"));
                        }
                    }
                }
            }
        }

        if (!hasPendingPrescriptions) {
            System.out.println("No upcoming prescription requests");
            return false;
        }

        if (hasPendingPrescriptions) {
            System.out.println(String.format("%-40s", "").replace(" ", "-"));
        }
        return true;
    }

    /**
     * Prints the details of a prescription request.
     * 
     * @param outcomeRecord The AppOutcomeRecord containing the prescription
     *                      details.
     */
    public static void printPrescriptionRequest(AppOutcomeRecord outcomeRecord) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "PrescriptionID", outcomeRecord.getPrescriptionID()));
        System.out.println(String.format("%-20s: %s", "Prescription Status", outcomeRecord.getPrescribeStatus()));

        for (PrescribeMedication prescribeMedication : outcomeRecord.getPrescribeMedications()) {
            System.out.println(String.format("%-20s: %s", "Medication Name", prescribeMedication.getMedicationName()));
            System.out.println(String.format("%-20s: %s", "Amount", prescribeMedication.getPrescriptionAmount()));
            System.out.println(String.format("%-40s", "").replace(" ", "-"));
        }
    }

    /**
     * Prints the details of a replenish request.
     * 
     * @param replenishRequest The ReplenishRequest containing the replenishment
     *                         details.
     */
    public static void printReplenishRequest(ReplenishRequest replenishRequest) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "PharmacistID", replenishRequest.getPharmacistID()));
        System.out.println(String.format("%-20s: %s", "RequestID", replenishRequest.getRequestID()));
        System.out.println(String.format("%-20s: %s", "Medication Name", replenishRequest.getMedicationName()));
        System.out.println(String.format("%-20s: %s", "Required Stock", replenishRequest.getMedicationAmount()));
        System.out.println(String.format("%-20s: %s", "Current Status", replenishRequest.getRequestStatus()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
}

