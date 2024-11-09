package src.controller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import src.helper.Helper;
import src.model.MedicalRecord;
import src.model.Patient;

import src.model.enums.Gender;
import src.model.enums.BloodType;
import src.model.enums.Role;

import src.database.Database;
import src.database.FileType;

public class PatientManager {
    
    public PatientManager() {
    }

    // Assuming data format: Patient ID, Role, Name, Date of Birth, Gender, Blood Type, Contact Information
    public static void createPatient(String patientID, String name, String dob, Gender gender, String phoneNumber, String email, BloodType bloodType) {
        Role role = Role.PATIENT;
        Patient newPatient = new Patient(patientID, role, name, dob, gender, phoneNumber, email, bloodType);
        

        // Saving to database
        Database.PATIENTS.put(patientID, newPatient);
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        System.out.println("Patient Created! Patient Details: ");
        printPatientDetails(newPatient);
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue) {
        List<Patient> updateList = searchPatientByID(patientID);
        if (updateList.size() == 0) {
            // Patient not found
            return false;
        }

        // Update patient details
        for (Patient patient : updateList) {
            Patient patientToUpdate;
            MedicalRecord record;

            switch(attributeCode) {
                case 1:
                    patientToUpdate = Database.PATIENTS.get(patientID);
                    record = patientToUpdate.getMedicalRecord();
                    record.setPhonenumber(newvalue);
                    break;
                case 2:
                    patientToUpdate = Database.PATIENTS.get(patientID);
                    record = patientToUpdate.getMedicalRecord();
                    record.setEmail(newvalue);
                    break;
                default:
                    break;
            }
        }

        // Saving to database
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    public static void printAllPatients(boolean byID) {
        // Create a list to copy patients from the database
        ArrayList<Patient> sortedList = new ArrayList<>();

        // Copy all patients from the database into sortedList
        for (Patient patient : Database.PATIENTS.values()) {
            sortedList.add(patient);
        }

        // Sort by patientID if byID is true
        if (byID) {
            for (int index = 1; index < sortedList.size(); index++) {
                Patient currentPatient = sortedList.get(index);
                MedicalRecord record = currentPatient.getMedicalRecord();
                int pid = Integer.parseInt(record.getPatientId().substring(1)); // Parse ID, ignoring the 'P'
                int position = index;

                // Perform insertion sort to keep the list ordered by patientID
                while (position > 0 && pid < Integer.parseInt(sortedList.get(position - 1).getMedicalRecord().getPatientID().substring(1))) {
                    sortedList.set(position, sortedList.get(position - 1));
                    position--;
                }
                sortedList.set(position, currentPatient);
            }
        } else {
            // Sort by patient name if byID is false
            sortedList.sort(Comparator.comparing(patient -> patient.getMedicalRecord().getName()));
        }

        // Print sorted list of patients
        for (Patient patient : sortedList) {
            MedicalRecord record = patient.getMedicalRecord();
            System.out.println(record.getPatientId() + " = " + patient);
        }
    }

    public static boolean validatePatientID(String patientID) {
        return Database.PATIENTS.containsKey(patientID);
    }

    public static ArrayList<Patient> searchPatientByID(String patientID) {
        ArrayList<Patient> searchList = new ArrayList<>();
        if (Database.PATIENTS.containsKey(patientID)) {
            Patient searchedPatient = Database.PATIENTS.get(patientID);
            searchList.add(searchedPatient);
        }
        return searchList;
    }

    /**
     * Initializer for dummy patients in the hospital. 
     */
    public static void initializeDummyPatients() {
        PatientManager.createPatient("P001", "Kee Lek Heng", "2005-07-28", Gender.MALE, BloodType.A, "85445065", "keel0004@e.ntu.edu.sg");
        PatientManager.createPatient("P002", "Doe John", "1995-12-12", Gender.MALE, BloodType.B, "8565065", "endy@e.ntu.edu.sg");
    }

    /**
     * Print the complete details of the patient 
     * @param patient {@link Patient} object to print 
     */
    public static void printPatientDetails(Patient patient) {
        MedicalRecord record = patient.getMedicalRecord();

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Patient ID", record.getPatientId()));  
        System.out.println(String.format("%-20s: %s", "Name", record.getName()));
        System.out.println(String.format("%-20s: %s", "Date of Birth", record.getDob()));
        System.out.println(String.format("%-20s: %s", "Gender", record.getGender().toString()));
        System.out.println(String.format("%-20s: %s", "Blood Type", record.getBloodType().toString()));
        System.out.println(String.format("%-20s: %s", "Phone Number", record.getPhonenumber()));
        System.out.println(String.format("%-20s: %s", "Email", record.getEmail()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
}
