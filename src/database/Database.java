package src.database;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

<<<<<<< HEAD
import src.controller.PatientManager;
import src.controller.StaffManager;
import src.controller.InventoryManager;
import src.controller.PrescriptionManager;
import src.controller.AppointmentManager;
import src.controller.MedicalRecordManager;
import src.controller.LoginManager;
import src.model.*;
=======
import src.model.Patient;
import src.model.Staff;
import src.model.enums.FileType;
>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3

/**
 * A class for managing the database operations for the Hospital Management System.
 * This class handles reading from and writing to files.
 * Author: Abarna
 * Version: 1.0
 * Since: 2024-10-17
 */
public class Database {
    
<<<<<<< HEAD
    public static HashMap<String, Patient> PATIENTS = new HashMap<>();
    
    public static HashMap<String, Staff> STAFF = new HashMap<>();

    public static HashMap<String, Medication> MEDICATION = new HashMap<>();

    public static HashMap<String, PrescribeMedication> PRESCRIPTION = new HashMap<>();         //PRESCRIPTUON AND LOGIN NOT SURE

    public static HashMap<String, ReplenishRequest> REQUESTS = new HashMap<>();

    public static HashMap<String, Appointment> APPOINTMENT = new HashMap<>();

    public static HashMap<String, MedicalRecord> MEDICALRECORD = new HashMap<>();

    public static HashMap<String, Login> LOGIN = new HashMap<>();                       //PRESCRIPTUON AND LOGIN NOT SURE
=======
    public static HashMap<Integer, Patient> patientMap = new HashMap<>();
    
    public static HashMap<Integer, Staff> staffMap = new HashMap<>();
>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3

     public Database() {
        if (!readSerializedObject(FileType.PATIENTS)) {
            System.out.println("Read into Patients failed!");
        }
        if (!readSerializedObject(FileType.STAFF)) {
<<<<<<< HEAD
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
        if (!readSerializedObject(FileType.LOGIN)) {
            System.out.println("Read into login failed!");
        }
=======
            System.out.println("Read into Staff failed!")
>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3
    }

    public static void saveFileIntoDatabase(FileType fileType) {
        writeSerializedObject(fileType);
    }

    public static void saveAllFiles() {
        saveFileIntoDatabase(FileType.PATIENTS);
        saveFileIntoDatabase(FileType.STAFF);
<<<<<<< HEAD
        saveFileIntoDatabase(FileType.MEDICATION);
        saveFileIntoDatabase(FileType.REQUESTS);
        saveFileIntoDatabase(FileType.APPOINTMENTS);
        saveFileIntoDatabase(FileType.LOGIN);
        saveFileIntoDatabase(FileType.APPOINTMENTS);

=======
>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3
    }

   /**
     * A method to read serialized object from a particular {@link FileType}.
     * @param fileType file type to be read.
     * @return {@code true} if read from file is successful. Otherwise, {@code false}.
     */
<<<<<<< HEAD
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
            }else if (fileType == FileType.REQUESTS) {
                REQUESTS = (HashMap<String, ReplenishRequest>) objectInputStream.readObject();
            } else if (fileType == FileType.APPOINTMENTS) {
                APPOINTMENT = (HashMap<String, Appointment>) objectInputStream.readObject();
            } else if (fileType == FileType.APPOINTMENTS) {
                PRESCRIPTION = (HashMap<String, PrescribeMedication>) objectInputStream.readObject();
            }else if (fileType == FileType.LOGIN) {
                LOGIN = (HashMap<String, Login>) objectInputStream.readObject();
            }
            //LOGIN IDK HOW IT WORKS YET
=======
    private static boolean readSerializedObject(FileType fileType) {
        String fileExtension = ".dat";
        String filePath = "./src/database/" + fileType.getFolder() + "/" + fileType.getFileName() + fileExtension;
        
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {

            if (fileType == FileType.PATIENTS) {
                patientMap = (HashMap<Integer, Patient>) objectInputStream.readObject();
            } else if (fileType == FileType.STAFF) {
                staffMap = (HashMap<Integer, Staff>) objectInputStream.readObject();
            }
>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3

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
<<<<<<< HEAD
        String filePath = "./src/database/" + folder + "/" + fileType.fileName + fileExtension;
        
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            if (fileType == FileType.PATIENTS) {
                objectOutputStream.writeObject(PATIENTS);
            } else if (fileType == FileType.STAFF) {
                objectOutputStream.writeObject(STAFF);
            } else if (fileType == FileType.MEDICATION) {
                objectOutputStream.writeObject(MEDICATION);
            } else if (fileType == FileType.APPOINTMENTS) {
                objectOutputStream.writeObject(PRESCRIPTION);
            } else if (fileType == FileType.APPOINTMENTS) {
                objectOutputStream.writeObject(APPOINTMENT);
            } else if (fileType == FileType.REQUESTS) {
                objectOutputStream.writeObject(REQUEST);
            } else if (fileType == FileType.LOGIN) {
                objectOutputStream.writeObject(LOGIN);
            }

            objectOutputStream.close();
            fileOutputStream.close();
            return true;
=======
        String filePath = "./src/database/" + fileType.getFolder() + "/" + fileType.getFileName() + fileExtension;

        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            if (fileType == FileType.PATIENTS) {
                objectOutputStream.writeObject(patientMap);
            } else if (fileType == FileType.STAFF) {
                objectOutputStream.writeObject(staffMap);
            }

>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3
        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
            return false;
        }
<<<<<<< HEAD
    }


        /**
     * A method to clear out all the data in database.
     * @return {@code true} if data is cleared successfully.
     */
     public static boolean clearDatabase() {
         PATIENTS = new HashMap<String, Patient>();
         writeSerializedObject(FileType.PATIENTS);

         STAFF = new HashMap<String, Staff>();
         writeSerializedObject(FileType.STAFF);

         MEDICATION = new HashMap<String, Medication>();
         writeSerializedObject(FileType.MEDICATION);

         REQUESTS = new HashMap<String, ReplenishRequest>();
         writeSerializedObject(FileType.REQUESTS);

         PRESCRIPTION = new HashMap<String, PrescribeMedication>();
         writeSerializedObject(FileType.APPOINTMENTS);

         APPOINTMENT = new HashMap<String, Appointment>();
         writeSerializedObject(FileType.APPOINTMENTS);

         LOGIN = new HashMap<String, Login>();
         writeSerializedObject(FileType.LOGIN);

         return true;
         
     }

    
     //Update the initialize everything here (Managers appropriately yea)
    
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


=======
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
>>>>>>> 2a9b861d868347e01ac53686f12ea24ab1af36e3

}