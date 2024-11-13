package src.model;

import src.model.enums.BloodType;
import src.model.enums.Gender;
import src.model.enums.Role;

public class Patient extends User {

    MedicalRecord medicalRecord;

    public Patient(String patientID, String password, Role role, String name, String dob, Gender gender,
            String phonenumber, String email, BloodType bloodType) {
        super(patientID, password, role);

        this.medicalRecord = new MedicalRecord("Default", patientID, name, dob, gender, phonenumber, email, bloodType);
    }

    public MedicalRecord getMedicalRecord() {
        return this.medicalRecord;
    }
}