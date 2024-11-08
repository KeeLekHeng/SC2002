package src.model;

import src.model.enums.Role;
import src.model.enums.BloodType;
import src.model.enums.Gender;

public class Patient extends User {

    MedicalRecord medicalRecord;

    public Patient(int patientID, Role role, String name, String dob, Gender gender, String phonenumber, String email, BloodType bloodType) {
        super(patientID,role);

        //doctorID in medical record set to default 0
        this.medicalRecord = new MedicalRecord(0,patientID, name, dob, gender, phonenumber, email,bloodType); 
    }

    public MedicalRecord getMedicalRecord () {
        return this.medicalRecord;
    }
}