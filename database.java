package src.database;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import src.controller.PatientManager;
import src.controller.StaffManager;
import src.controller.InventoryManager;
import src.controller.PrescriptiontManager;
import src.controller.AppointmentManager;
import src.controller.MedicalRecordManager;
import src.controller.LoginManager;
import src.model.*;

/**
 * A class for managing the database operations for the Hospital Management System.
 * This class handles reading from and writing to files.
 * Author: Abarna
 * Version: 1.0
 * Since: 2024-10-17
 */
public class Database {
    
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    
    public static HashMap<String, Staff> STAFF = new HashMap<>();

    public static HashMap<String, Medication> MEDICATION = new HashMap<>();

    public static HashMap<String, Prescription> PRESCRIPTION = new HashMap<>();

    public static HashMap<String, Appointment> APPOINTMENT = new HashMap<>();

    public static HashMap<String, MedicalRecord> MEDICALRECORD = new HashMap<>();

    public static HashMap<String, Login> LOGIN = new HashMap<>();

     public Database() {
        if (!readSerializedObject(FileType.PATIENTS)) {
            System.out.println("Read into Patients failed!");
        }
        if (!readSerializedObject(FileType.STAFF)) {
            System.out.println("Read into Staff failed!")
        }
        if (!readSerializedObject(FileType.MEDICATION)) {
            System.out.println("Read into Medication failed!")
        }
        if (!readSerializedObject(FileType.PRESCRIPTION)) {
            System.out.println("Read into Prescription failed!")
        }
        if (!readSerializedObject(FileType.APPOINTMENT)) {
            System.out.println("Read into Appointment failed!")
        }
        if (!readSerializedObject(FileType.MEDICALRECORD)) {
            System.out.println("Read into MedicalRecord failed!")
        }
        if (!readSerializedObject(FileType.LOGIN)) {
            System.out.println("Read into login failed!")
        }

    public static void saveFileIntoDatabase(FileType fileType) {
        writeSerializedObject(fileType);
    }

    public static void saveAllFiles() {
        saveFileIntoDatabase(FileType.PATIENTS);
        saveFileIntoDatabase(FileType.STAFF);
        saveFileIntoDatabase(FileType.MEDICATION);
        saveFileIntoDatabase(FileType.PRESCRIPTION);
        saveFileIntoDatabase(FileType.APPOINTMENT);
        saveFileIntoDatabase(FileType.MEDICALRECORD);
        saveFileIntoDatabase(FileType.LOGIN);
    }

   /**
     * A method to read serialized object from a particular {@link FileType}.
     * @param fileType file type to be read.
     * @return {@code true} if read from file is successful. Otherwise, {@code false}.
     */
     private static boolean readSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/database/" + folder + "/" + fileType.fileName + fileExtension;
        
         try(FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            if (fileType == FileType.PATIENTS) {
                PATIENTS = (HashMap<String, Patient>) objectInputStream.readObject();
            } else if (fileType == FileType.STAFF) {
                STAFF = (HashMap<String, Staff>) objectInputStream.readObject();
            } else if (fileType == FileType.MEDICATION) {
                MEDICATION = (HashMap<String, Medication>) objectInputStream.readObject();
            }else if (fileType == FileType.PRESCRIPTION) {
                PRESCRIPTION = (HashMap<String, Prescription>) objectInputStream.readObject();
            } else if (fileType == FileType.APPOINTMENT) {
                APPOINTMENT = (HashMap<String, Appointment>) objectInputStream.readObject();
            }else if (fileType == FileType.MEDICALRECORD) {
                MEDICALRECORD = (HashMap<String, MedicalRecord>) objectInputStream.readObject();
            }else if (fileType == FileType.LOGIN) {
                LOGIN = (HashMap<String, Login>) objectInputStream.readObject();
            }

        } catch (EOFException err) {
            System.out.println("Warning: " + err.getMessage());
            return false;
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
            return false;
        }
        return true;
    }
    /**
     * A method to write serialized object to file.
     * @param fileType file type to write into.
     * @return {@code true} if write to file is successful. Otherwise, {@code false}.
     */
    private static boolean writeSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/database/" + fileType.getFolder() + "/" + fileType.getFileName() + fileExtension;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            if (fileType == FileType.PATIENTS) {
                objectOutputStream.writeObject(PATIENTS);
            } else if (fileType == FileType.STAFF) {
                objectOutputStream.writeObject(STAFF);
            }} else if (fileType == FileType.MEDICATION) {
                objectOutputStream.writeObject(MEDICATION);
            }

        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
            return false;
        }
        return true;
    }

    public static boolean initializeDummyPatients() {
        if (PATIENTS.size() != 0) {
            System.out.println("The database already has patients. Reset database first to initialize patients");
            return false;
        }
        Manager.initializeDummyPatients();
        return true;
    }

    public static boolean initializeDummyStaff() {
        if (STAFF.size() != 0) {
            System.out.println("The database already has staff. Reset database first to initialize staff");
            return false;
        }
        Manager.initializeDummyStaff();
        return true;
    }

    /**
     * A method to clear out all the data in database.
     * @return {@code true} if data is cleared successfully.
     */
    // public static boolean clearDatabase() {
    //     PATIENT = new HashMap<Integer, Patient>();
    //     writeSerializedObject(FileType.PATIENTS);

    //     STAFF = new HashMap<Integer, Staff>();
    //     writeSerializedObject(FileType.STAFFS);
    // }

}
