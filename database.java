package src.database;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

import src.model.Patient;
import src.model.Staff;
import src.model.enums.FileType;

/**
 * A class for managing the database operations for the Hospital Management System.
 * This class handles reading from and writing to files.
 * Author: Abarna
 * Version: 1.0
 * Since: 2024-10-17
 */
public class Database {
    
    public static HashMap<Integer, Patient> patientMap = new HashMap<>();
    
    public static HashMap<Integer, Staff> staffMap = new HashMap<>();

     public Database() {
        if (!readSerializedObject(FileType.PATIENTS)) {
            System.out.println("Read into Patients failed!");
        }
        if (!readSerializedObject(FileType.STAFF)) {
            System.out.println("Read into Staff failed!")
    }

    public static void saveFileIntoDatabase(FileType fileType) {
        writeSerializedObject(fileType);
    }

    public static void saveAllFiles() {
        saveFileIntoDatabase(FileType.PATIENTS);
        saveFileIntoDatabase(FileType.STAFF);
    }

   /**
     * A method to read serialized object from a particular {@link FileType}.
     * @param fileType file type to be read.
     * @return {@code true} if read from file is successful. Otherwise, {@code false}.
     */
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
                objectOutputStream.writeObject(patientMap);
            } else if (fileType == FileType.STAFF) {
                objectOutputStream.writeObject(staffMap);
            }

        } catch (Exception err) {
            System.out.println("Error: " + err.getMessage());
            return false;
        }
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
