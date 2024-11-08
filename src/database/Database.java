package src.database;


import src.model.Patient;
import src.model.Staff;
import src.model.Medicine;
import src.model.Appointment;
import src.model.enums.FileType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A class for managing the database operations for the Hospital Management System.
 * This class handles reading from and writing to files.
 * Author: Abarna
 * Version: 1.0
 * Since: 2024-10-17
 */
public class Database {
    
    // Base directory for data files
    private static final String BASE_DIRECTORY = "src/database/data/";

    // Method to read patients from the file
    public List<Patient> readPatients() {
        List<Patient> patients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BASE_DIRECTORY + FileType.PATIENTS.fileName + ".csv"))) {
            String line;
            // Skip the header line if present
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming data format: Patient ID, Name, Date of Birth, Gender, Blood Type, Contact Information
                Patient patient = new Patient(data[0], data[1], data[2], data[3], data[4], data[5]);
                patients.add(patient);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework for production
        }
        return patients;
    }

    // Method to read staff from the file
    public List<Staff> readStaff() {
        List<Staff> staffList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BASE_DIRECTORY + FileType.STAFF.fileName + ".csv"))) {
            String line;
            // Skip the header line if present
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming data format: Staff ID, Name, Role, Gender, Age
                Staff staff = new Staff(data[0], data[1], data[2], data[3], Integer.parseInt(data[4]));
                staffList.add(staff);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework for production
        }
        return staffList;
    }

    // Method to read medicines from the file
    public List<Medicine> readMedicines() {
        List<Medicine> medicines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BASE_DIRECTORY + FileType.MEDICINES.fileName + ".csv"))) {
            String line;
            // Skip the header line if present
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                // Assuming data format: Medicine Name, Initial Stock, Low Stock Level Alert
                Medicine medicine = new Medicine(data[0], Integer.parseInt(data[1]), Integer.parseInt(data[2]));
                medicines.add(medicine);
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework for production
        }
        return medicines;
    }

    // Method to write appointments to the file
    public void writeAppointments(List<Appointment> appointments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BASE_DIRECTORY + FileType.APPOINTMENTS.fileName + ".csv"))) {
            for (Appointment appointment : appointments) {
                // Assuming appointment has a suitable toString() method
                writer.write(appointment.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework for production
        }
    }

    // Method to write patients to the file
    public void writePatients(List<Patient> patients) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BASE_DIRECTORY + FileType.PATIENTS.fileName + ".csv"))) {
            // Optional: Write header if needed
            writer.write("Patient ID,Name,Date of Birth,Gender,Blood Type,Contact Information");
            writer.newLine();
            for (Patient patient : patients) {
                writer.write(patient.toString()); // Ensure Patient has a suitable toString() method
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); // Consider using a logging framework for production
        }
    }

    // Additional methods for writing staff and medicines can be added similarly
}
