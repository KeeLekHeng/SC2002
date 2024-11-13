package src.database;

/**
 * An Enum that corresponds to the different file types that the Hospital Management System will read from and write to.
 * Author: Abarna
 * Version: 1.0
 * Since: 2024-10-17
 */
public enum FileType {
    /**
     * File type corresponding to the Patient data file.
     */
    PATIENTS("Patients"),

    /**
     * File type corresponding to the Staff data file.
     */
    STAFF("Staff"),

    /**
     * File type corresponding to the Medicine inventory file.
     */
    MEDICATION("Medications"),

    /**
     * File type corresponding to the Appointment data file.
     */
    APPOINTMENTS("Appointments"),

    /**
     * File type corresponding to the PrescribeMedication data file.
     */
    PRESCRIPTIONS("Prescriptions"),

    /**
     * File type corresponding to the Request data file.
     */
    REQUESTS("Requests"), MEDICATION, MEDICATION;

    /**
     * A String value for the FileType for retrieval purposes.
     */
    public final String fileName;

    /**
     * Constructor for the FileType Enum.
     * @param fileName The name of the file type
     */
    private FileType(String fileName) {
        this.fileName = fileName;
    }
}