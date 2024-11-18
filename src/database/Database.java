package src.database;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import src.controller.InventoryManager;
import src.controller.PatientManager;
import src.controller.StaffManager;
import src.model.*;

/**
 * A class for managing the database operations for the Hospital Management System.
 * This class handles reading from and writing to files.
 * @author Abarna
 * @version 1.0
 * @since 2024-10-17
 */
public class Database {

    /**
     * The folder name containing the .dat files.
     */
    private static final String folder = "data";
    
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    public static HashMap<String, Staff> STAFF = new HashMap<>();
    public static HashMap<String, Medication> MEDICATION = new HashMap<>();
    public static HashMap<String, List<PrescribeMedication>> PRESCRIPTION = new HashMap<>();
    public static HashMap<String, ReplenishRequest> REQUESTS = new HashMap<>();
    public static HashMap<String, Appointment> APPOINTMENT = new HashMap<>();

    /**
     * Constructs a Database instance and attempts to read serialized objects
     * into the corresponding fields for various data types.
     */
    public Database() {
        if (!readSerializedObject(FileType.PATIENTS)) {
            System.out.println("Read into Patients failed!");
        }
        if (!readSerializedObject(FileType.STAFF)) {
            System.out.println("Read into Staff failed!");
        }
        if (!readSerializedObject(FileType.MEDICATION)) {
            System.out.println("Read into Medication failed!");
        }
        if (!readSerializedObject(FileType.REQUESTS)) {
            System.out.println("Read into Replenish Request failed!");
        }
        if (!readSerializedObject(FileType.PRESCRIPTIONS)) {
            System.out.println("Read into Prescription failed!");
        }
        if (!readSerializedObject(FileType.APPOINTMENTS)) {
            System.out.println("Read into Appointment failed!");
        }
    }

    /**
     * Saves a specific file type into the database.
     * @param fileType The type of file to save.
     */
    public static void saveFileIntoDatabase(FileType fileType) {
        writeSerializedObject(fileType);
    }

    /**
     * Saves all file types into the database.
     */
    public static void saveAllFiles() {
        saveFileIntoDatabase(FileType.PATIENTS);
        saveFileIntoDatabase(FileType.STAFF);
        saveFileIntoDatabase(FileType.MEDICATION);
        saveFileIntoDatabase(FileType.REQUESTS);
        saveFileIntoDatabase(FileType.APPOINTMENTS);
        saveFileIntoDatabase(FileType.PRESCRIPTIONS);
    }

    ////////////
    public static void loadFileIntoDatabase(FileType fileType) {
        readSerializedObject(fileType);
    }

    public static void loadAllFiles() {
        loadFileIntoDatabase(FileType.PATIENTS);
        loadFileIntoDatabase(FileType.STAFF);
        loadFileIntoDatabase(FileType.MEDICATION);
        loadFileIntoDatabase(FileType.REQUESTS);
        loadFileIntoDatabase(FileType.APPOINTMENTS);
        loadFileIntoDatabase(FileType.PRESCRIPTIONS);
    }
    /////////////

    /**
     * Reads a serialized object from the specified {@link FileType}.
     * @param fileType The type of file to read.
     * @return {@code true} if the file is read successfully, otherwise {@code false}.
     */
    
    private static boolean readSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/database/" + folder + "/" + fileType.fileName + fileExtension;
        

         try{
            FileInputStream fileInputStream = new FileInputStream(filePath);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            Object object = objectInputStream.readObject();
            
            if (!(object instanceof HashMap)) {
                System.out.println(fileType.fileName);
                objectInputStream.close();
                return false;
            }

            //read into database
            if (fileType == FileType.PATIENTS) {
                PATIENTS = (HashMap<String, Patient>) object;
            } else if (fileType == FileType.STAFF) {
                STAFF = (HashMap<String, Staff>) object;
            } else if (fileType == FileType.MEDICATION) {
                MEDICATION = (HashMap<String, Medication>) object;
            } else if (fileType == FileType.REQUESTS) {
                REQUESTS = (HashMap<String, ReplenishRequest>) object;
            } else if (fileType == FileType.APPOINTMENTS) {
                APPOINTMENT = (HashMap<String, Appointment>) object;
            } else if (fileType == FileType.PRESCRIPTIONS) {
                PRESCRIPTION = (HashMap<String, List<PrescribeMedication>>) object;
            }

            objectInputStream.close();
            fileInputStream.close();
        } catch (EOFException err) {
            System.out.println("Warning: " + err.getMessage());
            if (fileType == FileType.PATIENTS) {
                PATIENTS = new HashMap<String, Patient>();
            } else if (fileType == FileType.STAFF) {
                STAFF = new HashMap<String, Staff>();
            } else if (fileType == FileType.APPOINTMENTS) {
                APPOINTMENT = new HashMap<String, Appointment>();
            } else if (fileType == FileType.PRESCRIPTIONS) {
                PRESCRIPTION = new HashMap<String, List<PrescribeMedication>>();
            } else if (fileType == FileType.REQUESTS) {
                REQUESTS = new HashMap<String, ReplenishRequest>();
            } else if (fileType == FileType.MEDICATION) {
                MEDICATION = new HashMap<String, Medication>();
            } 
        } catch (IOException err) {
            err.printStackTrace();
            return false;
        } catch (ClassNotFoundException err) {
            err.printStackTrace();
            return false;
        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Writes a serialized object to the specified file.
     * @param fileType The type of file to write.
     * @return {@code true} if the file is written successfully, otherwise {@code false}.
     */
    private static boolean writeSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/database/" + folder + "/" + fileType.fileName + fileExtension;
        
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            if (fileType == FileType.PATIENTS) {
                objectOutputStream.writeObject(PATIENTS);
            } else if (fileType == FileType.STAFF) {
                objectOutputStream.writeObject(STAFF);
            } else if (fileType == FileType.MEDICATION) {
                objectOutputStream.writeObject(MEDICATION);
            } else if (fileType == FileType.PRESCRIPTIONS) {
                objectOutputStream.writeObject(PRESCRIPTION);
            } else if (fileType == FileType.APPOINTMENTS) {
                objectOutputStream.writeObject(APPOINTMENT);
            } else if (fileType == FileType.REQUESTS) {
                objectOutputStream.writeObject(REQUESTS);
            }
            objectOutputStream.close();
            fileOutputStream.close();
            return true;
        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
            return false;
        }
    }

    /**
     * Clears all the data in the database.
     * @return {@code true} if data is cleared successfully.
     */
    public static boolean clearDatabase() {
        PATIENTS = new HashMap<>();
        writeSerializedObject(FileType.PATIENTS);

        STAFF = new HashMap<>();
        writeSerializedObject(FileType.STAFF);

        MEDICATION = new HashMap<>();
        writeSerializedObject(FileType.MEDICATION);

        REQUESTS = new HashMap<>();
        writeSerializedObject(FileType.REQUESTS);

        PRESCRIPTION = new HashMap<>();
        writeSerializedObject(FileType.PRESCRIPTIONS);

        APPOINTMENT = new HashMap<>();
        writeSerializedObject(FileType.APPOINTMENTS);

        return true;
    }

    /**
     * Initializes dummy patient data if the database is empty.
     * @return {@code true} if dummy patients are initialized, otherwise {@code false}.
     */
    public static boolean initializeDummyPatients() {
        if (!Database.PATIENTS.isEmpty()) {
            System.out.println("The database already has patients. Reset database first to initialize patients.");
            return false;
        }
        PatientManager.initializeDummyPatients();
        return true;
    }

    /**
     * Initializes dummy staff data if the database is empty.
     * @return {@code true} if dummy staff are initialized, otherwise {@code false}.
     */
    public static boolean initializeDummyStaff() {
        if (!Database.STAFF.isEmpty()) {
            System.out.println("The database already has staff. Reset database first to initialize staff.");
            return false;
        }
<<<<<<< Updated upstream
        StaffManager.createDummyStaff();
=======
        StaffManager.initializeDummyStaff();
>>>>>>> Stashed changes
        return true;
    }

    /**
     * Initializes dummy medication data if the database is empty.
     * @return {@code true} if dummy medications are initialized, otherwise {@code false}.
     */
    public static boolean initializeDummyMedication() {
        if (!Database.MEDICATION.isEmpty()) {
            System.out.println("The database already has medications. Reset database first to initialize medications.");
            return false;
        }
        InventoryManager.initializeDummyMedication();
        return true;
    }
}
