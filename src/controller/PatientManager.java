package src.controller;

import java.util.ArrayList;
import java.util.Comparator;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.AppOutcomeRecord;
import src.model.Appointment;
import src.model.Patient;
import src.model.PrescribeMedication;
import src.model.enums.AppointmentStatus;
import src.model.enums.BloodType;
import src.model.enums.Gender;
import src.model.enums.Role;

public class PatientManager {

    public PatientManager() {
    }

    // Assuming data format: Patient ID, Role, Name, Date of Birth, Gender, Blood
    // Type, Contact Information
    public static void createPatient(String name, String dob, Gender gender, String phoneNumber, String email,
            BloodType bloodType) {
        int gid = Helper.generateUniqueId(Database.PATIENTS);
        String patientID = String.format("P%04d", gid);
        String password = "password";
        Role role = Role.PATIENT;
        String doctorID = null;
        Patient newPatient = new Patient(patientID, doctorID, password, role, name, dob, gender, phoneNumber, email,
                bloodType);

        // Saving to database
        Database.PATIENTS.put(patientID, newPatient);
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        System.out.println("Patient Created! Patient Details: ");
        viewPatientRecords(newPatient);
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            // Patient not found
            return false;
        }

        switch (attributeCode) {
            case 1:
                patientToUpdate = Database.PATIENTS.get(patientID);
                patientToUpdate.setPhonenumber(newvalue);
                break;
            case 2:
                patientToUpdate = Database.PATIENTS.get(patientID);
                patientToUpdate.setEmail(newvalue);
                break;
            case 3:
                patientToUpdate = Database.PATIENTS.get(patientID);
                patientToUpdate.setDoctorID(newvalue);
            case 4:
                patientToUpdate = Database.PATIENTS.get(patientID);
                patientToUpdate.setName(newvalue);
            default:
                break;
        }

        // Saving to database
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, Gender gender) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            // Patient not found
            return false;
        }

        switch (attributeCode) {
            case 5:
                patientToUpdate = Database.PATIENTS.get(patientID);
                patientToUpdate.setGender(gender);
                break;
            default:
                break;
        }

        // Saving to database
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    public static boolean updatePatientDetails(String patientID, int attributeCode, BloodType bloodType) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            // Patient not found
            return false;
        }

        switch (attributeCode) {
            case 6:
                patientToUpdate = Database.PATIENTS.get(patientID);
                patientToUpdate.setBloodType(bloodType);
                break;
            default:
                break;
        }

        // Saving to database
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    /**
     * Prints details of all patients in the database, sorted by either Patient ID
     * or Name.
     * 
     * @param byID - if true, sort by Patient ID; if false, sort by Name.
     */
    public static void printAllPatients(boolean byID) {
        // Create a list to hold patients from the database
        ArrayList<Patient> patientsList = new ArrayList<>(Database.PATIENTS.values());

        // Sort the list based on the sorting preference
        if (byID) {
            // Sort by Patient ID (numerically)
            patientsList
                    .sort(Comparator.comparingInt(patient -> Integer.parseInt(patient.getPatientID().substring(1))));
        } else {
            // Sort by Name alphabetically
            patientsList.sort(Comparator.comparing(Patient::getName));
        }

        // Print all patients' details
        System.out.println(String.format("%-40s", "").replace(" ", "="));
        System.out.println(String.format("%-10s %-20s %-15s %-10s %-15s %-15s %-25s",
                "Patient ID", "Name", "Date of Birth", "Gender",
                "Blood Type", "Phone Number", "Email"));
        System.out.println(String.format("%-40s", "").replace(" ", "="));

        for (Patient patient : patientsList) {
            System.out.println(String.format("%-10s %-20s %-15s %-10s %-15s %-15s %-25s",
                    patient.getPatientID(), patient.getName(), patient.getDob(),
                    patient.getGender(), patient.getBloodType(),
                    patient.getPhonenumber(), patient.getEmail()));
        }

        System.out.println(String.format("%-40s", "").replace(" ", "="));
    }

    public static void showPatientOverview(boolean byID, String doctorId) {
        ArrayList<Patient> viewList = new ArrayList<>();

        for (Patient patient : Database.PATIENTS.values()) {
            // Loops through viewList to check for duplicates
            // Add patient into viewList if there's no duplicate and doctorId is correct
            if (patient.getDoctorID().equals(doctorId)) {
                patient = Database.PATIENTS.get(patient.getPatientID());
                viewList.add(patient);
            }
        }

        if (byID) {
            viewList.sort(Comparator.comparingInt(patient -> Integer.parseInt(patient.getId().substring(1))));
        } else {
            viewList.sort(Comparator.comparing(patient -> patient.getName()));
        }

        for (Patient patient : viewList) {
            viewPatientRecords(patient);
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
     * Print the complete details of the patient
     * 
     * @param patient {@link Patient} object to print
     */
    public static void viewPatientRecords(Patient patient) {

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Patient ID", patient.getPatientID()));
        System.out.println(String.format("%-20s: %s", "Name", patient.getName()));
        System.out.println(String.format("%-20s: %s", "Date of Birth", patient.getDob()));
        System.out.println(String.format("%-20s: %s", "Gender", patient.getGender().toString()));
        System.out.println(String.format("%-20s: %s", "Blood Type", patient.getBloodType().toString()));
        System.out.println(String.format("%-20s: %s", "Phone Number", patient.getPhonenumber()));
        System.out.println(String.format("%-20s: %s", "Email", patient.getEmail()));
        printPastMedicalRecord();
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    public static void printPastMedicalRecord() {
        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
        for (Appointment app : Database.APPOINTMENT.values()) {
            if (app.getAppointmentStatus() == AppointmentStatus.COMPLETED && app.getAppOutcomeRecord() != null) {
                appointmentList.add(app);
            }
        }

        if (!appointmentList.isEmpty()) {
            System.out.println("Previous Medical Records:");
            for (Appointment app : appointmentList) {
                AppOutcomeRecord outcome = app.getAppOutcomeRecord();
                System.out.println(String.format("%-20s: %s", "Date", outcome.getEndDateTime()));
                System.out.println(String.format("%-20s: %s", "Diagnoses", outcome.getConsultationNotes()));
                System.out.println(String.format("%-20s: %s", "Treatments", outcome.getTypeOfService()));

                for (PrescribeMedication prescribeMedication : outcome.getPrescribeMedications()) {
                    System.out.println(
                            String.format("%-20s: %s", "Medication Name", prescribeMedication.getMedicationName()));
                    System.out
                            .println(String.format("%-20s: %s", "Amount", prescribeMedication.getPrescriptionAmount()));
                    System.out.println(String.format("%-40s", "").replace(" ", "-"));
                }
            }
        } else {
            System.out.println("Previous Medical Records: None");
        }

    }

    /**
     * Initializer for dummy patients in the hospital.
     */
    public static void initializeDummyPatients() {
        PatientManager.createPatient("Kee Lek Heng", "2005-07-28", Gender.MALE, "85445065", "keel0004@e.ntu.edu.sg",
                BloodType.A);
        PatientManager.createPatient("Doe John", "1995-12-12", Gender.MALE, "8565065", "endy@e.ntu.edu.sg",
                BloodType.B);
    }

}
