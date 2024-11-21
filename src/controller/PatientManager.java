package src.controller;

import java.time.format.DateTimeFormatter;
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

/**
 * PatientManager is a controller class responsible for managing patient data,
 * including creating new patient records and updating existing patient details.
 * It provides functionality to create patients, update patient attributes, and
 * view patient records.
 * @author Benjamin, Kee
 * @version 1.0
 * @since 2024-11-20
 */
public class PatientManager {

    public PatientManager() {
    }

    /**
     * Creates a new patient and adds it to the database.
     * @param name        The name of the patient.
     * @param dob         The date of birth of the patient.
     * @param gender      The gender of the patient.
     * @param phoneNumber The phone number of the patient.
     * @param email       The email address of the patient.
     * @param bloodType   The blood type of the patient.
     */
    public static void createPatient(String name, String dob, Gender gender, String phoneNumber, String email,
            BloodType bloodType) {
        int gid = Helper.generateUniqueId(Database.PATIENTS);
        String patientID = String.format("P%04d", gid);
        String password = "password";
        Role role = Role.PATIENT;
        String doctorID = null;

        if (Helper.EmailValidator(email) && Helper.PhoneNumValidator(phoneNumber)) {
            Patient newPatient = new Patient(patientID, doctorID, password, role, name, dob, gender, phoneNumber, email,
                    bloodType);

            // Saving to database
            Database.PATIENTS.put(patientID, newPatient);
            Database.saveFileIntoDatabase(FileType.PATIENTS);
            System.out.println("Patient Created! Patient Details: ");
            viewPatientRecords(newPatient);
        } else {
            System.out.println("Email or Phone Number is invalid. Please try again.");
        }
    }

    /**
     * Updates the details of an existing patient.
     * @param patientID     The ID of the patient whose details are to be updated.
     * @param attributeCode The attribute to be updated (1 for phone number, 2 for
     *                      email, 3 for doctor ID, 4 for name).
     * @param newvalue      The new value for the attribute to be updated.
     * @return true if the update was successful, false if the patient was not
     *         found.
     */
    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            // Patient not found
            return false;
        }

        switch (attributeCode) {
            case 1:
                patientToUpdate = Database.PATIENTS.get(patientID);
                if (Helper.PhoneNumValidator(newvalue)) {
                    patientToUpdate.setPhonenumber(newvalue);
                }
                break;
            case 2:
                patientToUpdate = Database.PATIENTS.get(patientID);
                if (Helper.EmailValidator(newvalue)) {
                    patientToUpdate.setEmail(newvalue);
                }
                break;
            case 3:
                patientToUpdate.setDoctorID(newvalue);
                break;
            case 4:
                patientToUpdate.setName(newvalue);
                break;
            default:
                break;
        }
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    /**
     * Updates the gender of an existing patient.
     * @param patientID     The ID of the patient whose gender is to be updated.
     * @param attributeCode The attribute code (5 for gender).
     * @param gender        The new gender to be assigned to the patient.
     * @return true if the update was successful, false if the patient was not
     *         found.
     */
    public static boolean updatePatientDetails(String patientID, int attributeCode, Gender gender) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            return false;
        }

        switch (attributeCode) {
            case 5:
                patientToUpdate.setGender(gender);
                break;
            default:
                break;
        }
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    /**
     * Updates the blood type of an existing patient.
     * @param patientID     The ID of the patient whose blood type is to be updated.
     * @param attributeCode The attribute code (6 for blood type).
     * @param bloodType     The new blood type to be assigned to the patient.
     * @return true if the update was successful, false if the patient was not
     *         found.
     */
    public static boolean updatePatientDetails(String patientID, int attributeCode, BloodType bloodType) {
        Patient patientToUpdate = searchPatientByID(patientID);
        if (patientToUpdate == null) {
            return false;
        }

        switch (attributeCode) {
            case 6:
                patientToUpdate.setBloodType(bloodType);
                break;
            default:
                break;
        }
        Database.saveFileIntoDatabase(FileType.PATIENTS);
        return true;
    }

    /**
     * Prints details of all patients in the database, sorted by either Patient ID
     * or Name.
     * @param byID if true, sort by Patient ID; if false, sort by Name.
     */
    public static void printAllPatients(boolean byID) {
        ArrayList<Patient> patientsList = new ArrayList<>(Database.PATIENTS.values());

        if (byID) {
            patientsList
                    .sort(Comparator.comparingInt(patient -> Integer.parseInt(patient.getPatientID().substring(1))));
        } else {
            patientsList.sort(Comparator.comparing(Patient::getName));
        }

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

    /**
     * Displays an overview of patients under a specific doctor, sorted by either
     * Patient ID
     * or Name.
     * @param byID     if true, sort by Patient ID; if false, sort by Name.
     * @param doctorId the ID of the doctor to filter patients by.
     */
    public static void showPatientOverview(boolean byID, String doctorId) {
        ArrayList<Patient> viewList = new ArrayList<>();

        for (Patient patient : Database.PATIENTS.values()) {
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

    /**
     * Validates if a given patient ID exists in the database.
     * @param patientID the ID of the patient to validate.
     * @return true if the patient ID exists; false otherwise.
     */
    public static boolean validatePatientID(String patientID) {
        return Database.PATIENTS.containsKey(patientID);
    }

    /**
     * Searches for a patient by their ID in the database.
     * @param patientID the ID of the patient to search for.
     * @return the Patient object if found; null otherwise.
     */
    public static Patient searchPatientByID(String patientID) {
        if (Database.PATIENTS.containsKey(patientID)) {
            return Database.PATIENTS.get(patientID);
        } else {
            return null;
        }
    }

    /**
     * Prints the complete details of the given patient.
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
        printPastOutcomeRecord();
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    /**
     * Prints past medical records of the patient.
     * If there are no records, it prints "None".
     */
    public static void printPastOutcomeRecord() {
        ArrayList<Appointment> appointmentList = new ArrayList<Appointment>();
        for (Appointment app : Database.APPOINTMENT.values()) {
            if (app.getAppointmentStatus() == AppointmentStatus.COMPLETED && app.getAppOutcomeRecord() != null) {
                appointmentList.add(app);
            }
        }

        if (!appointmentList.isEmpty()) {
            System.out.println("Previous Outcome Records:");
            for (Appointment app : appointmentList) {
                AppOutcomeRecord outcome = app.getAppOutcomeRecord();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                System.out.println(String.format("%-20s: %s", "Date", outcome.getEndDateTime().format(formatter)));
                System.out.println(String.format("%-20s: %s", "Diagnoses", outcome.getConsultationNotes()));
                System.out.println(String.format("%-20s: %s", "Treatments", outcome.getTypeOfService()));

                for (PrescribeMedication prescribeMedication : outcome.getPrescribeMedications()) {
                    System.out.println(
                            String.format("%-20s: %s", "Medication Name", prescribeMedication.getMedicationName()));
                    System.out.println(String.format("%-20s: %s", "Amount", prescribeMedication.getPrescriptionAmount()));
                    System.out.println(String.format("%-40s", "").replace(" ", "-"));
                }
            }
        } else {
            System.out.println("Previous Medical Records: None");
        }
    }

    /**
     * Initializes dummy patient records for testing purposes.
     */
    public static void initializeDummyPatients() {
        PatientManager.createPatient("Kee Lek Heng", "2005-07-28", Gender.MALE, "6585445065", "keel0004@e.ntu.edu.sg",
                BloodType.A);
        PatientManager.createPatient("Doe John", "1995-12-12", Gender.MALE, "6585650654", "endy@e.ntu.edu.sg",
                BloodType.B);
    }
}