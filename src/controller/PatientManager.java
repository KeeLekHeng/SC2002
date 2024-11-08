package src.controller;

import java.io.*;
import java.security.Identity;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;

import javax.management.relation.Role;
import src.helper.Helper;
import src.model.MedicalRecord;
import src.model.Patient;

import src.model.enums.Gender;
import src.model.enums.BloodType;
import src.model.enums.Role;

import src.database.Database;
import src.database.FileType;


    
public class PatientManager {
    
    public PatientManager()
    {

    }
    // Assuming data format: Patient ID, Role, Name, Date of Birth, Gender, Blood Type, Contact Information
    public static void createPatient(String patientID, String name, String dob, Gender gender, BloodType bloodType, String phoneNumber, String email){
        Role role = Role.PATIENT;
        Patient newPatient = new Patient (patientID, role, name, dob, gender, bloodType, phoneNumber, email);
        
        //SAVING TO DATABASE
        Database.PATIENTS.put(patientID, newPatient);
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        System.out.println("Patient Created! Patient Details: ");
        printPatientDetails(newPatient);
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue){
        List<Patient> updateList = searchPatientById(PatientId);
        if (updateList.size() == 0) {
            // guest not found
            return false;

        //abit different from IVAN see if it works
        for (Patient patient : updateList){
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

        //SAVING TO DATABASE
        Database.saveFileIntoDatabase(FileType.GUESTS);
        return true;
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, Gender gender){
        List<Patient> updateList = searchPatientById(PatientId);
        if (updateList.size() == 0) {
            // guest not found
            return false;

        //abit different from IVAN see if it works
        for (Patient patient : updateList){
            Patient patientToUpdate;
            MedicalRecord record;

            switch(attributeCode) {
                case 3:
                    patientToUpdate = Database.PATIENTS.get(patientID);
                    record = patientToUpdate.getMedicalRecord();
                    record.setGender(gender);
                    break;
                default:
                    break;
            }
        }

        //SAVING TO DATABASE
        Database.saveFileIntoDatabase(FileType.GUESTS);
        return true;
    }

    public static void printAllPatients(boolean byID) {
        // Create a list to copy patients from the database
        ArrayList<Patient> sortedList = new ArrayList<>();

        // Copy all patients from the database into sortedList          -> vALUES IS IN HASHMAP
        for (Patient patient : Database.PATIENTS.values()) {
            sortedList.add(patient);
        }

        // Sort by patientID if byID is true
        if (byID) {
            for (int index = 1; index < sortedList.size(); index++) {
                Patient currentPatient = sortedList.get(index);
                MedicalRecord record = currentPatient.getMedicalRecord();
                int pid = Integer.parseInt(record.getPatientID().substring(1)); // Parse ID, ignoring the 'P'
                int position = index;

                // Perform insertion sort to keep the list ordered by patientID
                while (position > 0 && pid < Integer.parseInt(sortedList.get(position - 1).getMedicalRecord.getPatientID().substring(1))) {
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
            System.out.println(patient.getPatientID() + " = " + patient);
        }
    }

    public static boolean validatePatientID(String patientID) {
        if (Database.PATIENTS.containsKey(patientID)) {
            return true;
        } else {
            return false;
        }
    }

    public static List<Patient> searchPatientByID (String patientID) {
        List<Patient> searchList = new List<Patient>;
        if (Database.PATIENTS.containsKey(patientID)){                                  //containsKey is for hashmap only
            Patient searchedPatient = Database.PATIENTS.get(patientID);
            searchList.add(searchedPatient);
        }
        return searchList;
    }

        

    /**
     * Initializer for dummy guests in the hotel. 
     */
    public static void initializeDummyPatients() {
        PatientManager.createPatient(P001, "Kee Lek Heng", "2005-07-28", Gender.MALE, "85445065", "keel0004@e.ntu.edu.sg", BloodType.A);
        PatientManager.createPatient(P002, "Doe John", "1995-12-12", Gender.MALE, "8565065", "endy@e.ntu.edu.sg", BloodType.B);
    }
        

    /**
     * Print the complete details of the guest 
     * @param patient {@link Patient} object to print 
     */
    public static void printPatientDetails(Patient patient) {
        MedicalRecord record = patient.getMedicalRecord();

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Patient ID", patient.getUserID()));  // Assuming getUserID() exists in the User superclass
        System.out.println(String.format("%-20s: %s", "Name", record.getName()));
        System.out.println(String.format("%-20s: %s", "Date of Birth", record.getDob()));
        System.out.println(String.format("%-20s: %s", "Gender", record.getGender().toString()));
        System.out.println(String.format("%-20s: %s", "Blood Type", record.getBloodType().toString()));
        System.out.println(String.format("%-20s: %s", "Phone Number", record.getPhonenumber()));
        System.out.println(String.format("%-20s: %s", "Email", record.getEmail()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        }
    }
        



/* 
 * public static void createGuest(String firstName, String lastName, String creditCardNumber, String address,
            Gender gender, Identity identity, String nationality, String contactNo) {
        String name = firstName + " " + lastName;
        int gid = Helper.generateUniqueId(Database.GUESTS);
        String guestId = String.format("G%04d", gid);
        Guest newGuest = new Guest(name, firstName, lastName, creditCardNumber, address, gender, identity, nationality,
                contactNo, guestId);
        Database.GUESTS.put(guestId, newGuest);
        Database.saveFileIntoDatabase(FileType.GUESTS);
        System.out.println("Guest Created! Guest Details: ");
        printGuestDetails(newGuest);
*/

