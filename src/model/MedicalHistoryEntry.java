package src.model;
import java.io.Serializable;
import java.util.Date;


public class MedicalHistoryEntry implements Serializable {
    private Date date;
    private String diagnosis;
    private String prescription;
    private String treatment;

    public MedicalHistoryEntry (String diagnosis, String prescription, String treatment)
    {
        this.date = new Date();
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.treatment = treatment;
    }

    public Date getDate () {
        return this.date;
    }

    public void setDate (Date date) {
        this.date = date;
    }

    public String getDiagnosis () {
        return this.diagnosis;
    }

    public void setDiagnosis (String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getPrescription () {
        return this.prescription;
    }

    public void setPrescription (String prescription) {
        this.prescription = prescription;
    }

    public String getTreatment () {
        return this.treatment;
    }

    public void setTreatment (String treatment) {
        this.treatment = treatment;
    }

    public String toString() 
    {
        return "Date:" + date + ", Diagnosis: " + diagnosis + ", Prescription: " + prescription + ", Treatment: " + treatment;
    }
}