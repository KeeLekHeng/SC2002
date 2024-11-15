package src.model;

import src.model.enums.BloodType;
import src.model.enums.Gender;
import src.model.enums.Role;

public class Patient extends User {

    public String doctorID;
    public final String patientID;
    public String name;
    public final String dob;
    public Gender gender;
    public String phonenumber;
    public String email;
    private BloodType bloodType;
    // private List<MedicalHistoryEntry> history;
    // Past diagnosis and treatment
    private static final long serialVersionUID = 4L;

    public Patient(String patientID, String doctorID, String password, Role role, String name, String dob,
            Gender gender,
            String phonenumber, String email, BloodType bloodType) {
        super(patientID, password, role);

        this.doctorID = doctorID;
        this.patientID = patientID;
        this.name = name;
        this.dob = dob;
        this.gender = gender;
        setPhonenumber(phonenumber);
        setEmail(email);
        this.bloodType = bloodType;
        // this.history = new ArrayList<>();
    }

    public String getDoctorID() {
        return this.doctorID;
    }

    public String getPatientID() {
        return this.patientID;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDob() {
        return this.dob;
    }

    public Gender getGender() {
        return this.gender;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BloodType getBloodType() {
        return this.bloodType;
    }

    public void setDoctorID(String doctorID) {
        this.doctorID = doctorID;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setBloodType(BloodType bloodType) {
        this.bloodType = bloodType;
    }

}