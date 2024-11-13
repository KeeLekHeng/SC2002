package src.controller;

import java.util.ArrayList;
import java.util.Comparator;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.MedicalRecord;
import src.model.Patient;
import src.model.enums.BloodType;
import src.model.enums.Gender;
import src.model.enums.Role;

public class PatientManager {
    
    public PatientManager() {
    }

    // Assuming data format: Patient ID, Role, Name, Date of Birth, Gender, Blood Type, Contact Information
    public static void createPatient(String name, String dob, Gender gender, String phoneNumber, String email, BloodType bloodType) {
        int gid = Helper.generateUniqueId(Database.PATIENTS);
        String patientID = String.format("P%04d", gid);
        String password = "password";
        Role role = Role.PATIENT;
        Patient newPatient = new Patient(patientID, password, role, name, dob, gender, phoneNumber, email, bloodType);
        

        // Saving to database
        Database.PATIENTS.put(patientID, newPatient);
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        System.out.println("Patient Created! Patient Details: ");
        printPatientDetails(newPatient);
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            // Patient not found
            return false;
        }
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
                while (position > 0 && pid < Integer.parseInt(sortedList.get(position - 1).getMedicalRecord().getPatientId().substring(1))) {
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

    public static Patient searchPatientByID(String patientID) {
        // Check if the patientID exists in the database
        if (Database.PATIENTS.containsKey(patientID)) {
            return Database.PATIENTS.get(patientID);
        } else {
            // Return null if patient is not found
            return null;
        }
    }

    /**
     * Initializer for dummy patients in the hospital. 
     */
    public static void initializeDummyPatients() {
        PatientManager.createPatient("Kee Lek Heng", "2005-07-28", Gender.MALE, "85445065", "keel0004@e.ntu.edu.sg", BloodType.A);
        PatientManager.createPatient("Doe John", "1995-12-12", Gender.MALE, "8565065", "endy@e.ntu.edu.sg", BloodType.B);
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
