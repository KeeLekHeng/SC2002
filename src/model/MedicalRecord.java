package src.model;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import src.model.enums.BloodType;
import src.model.enums.Gender;


public class MedicalRecord implements Serializable {

    public final int id;
    public String name;
    public final String dob;
    public final Gender gender;
    public String phonenumber;
    public String email;
    private final BloodType bloodType;

    private List<MedicalHistoryEntry> history;
    //past diagnosis and treatment

    public MedicalRecord(int id, String name, String dob, Gender gender, 
            String phonenumber, String email, BloodType bloodType) {

            this.id = id;
            this.name = name;
            this.dob = dob;
            this.gender = gender;
            setPhonenumber(phonenumber);
            setEmail(email);
            this.bloodType = bloodType;
            this.history = new ArrayList<>();
            }

    public int getId () {
        return this.id;
    }

    public String getName () {
        return this.name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDob () {
        return this.dob;
    }

    public Gender getGender () {
        return this.gender;
    }

    public String getPhonenumber () {
        return this.phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail () {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BloodType getBloodType () {
        return this.bloodType;
    }

    public void viewMedicalRecord()
    {
        System.out.printf("Medical Record\nID: %d\nName: %s\nDOB: %s\nGender: %s\nPhone number:%s\nEmail:%s\nBloodType: %s\n", id, name, dob, gender, phonenumber,email,bloodType);
        for (MedicalHistoryEntry entry : history) 
            System.out.println(entry.toString());
    }

    //only doctor can add
    public void addHistoryEntry(User user, String diagnosis, String prescription, String treatment)
    {
        if (user.isDoctor())
        {
            MedicalHistoryEntry entry = new MedicalHistoryEntry(diagnosis, prescription, treatment);
            this.history.add(entry);            
        }
    }
}