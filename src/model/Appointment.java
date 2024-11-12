//before appointment is completed, the "Outcome Record" values are just left default

package src.model;
import java.io.Serializable;
import java.util.List;
import src.model.enums.AppointmentStatus;


public class Appointment implements Serializable {
    private String doctorID;
    private String patientID;
    private String date;
    private String time;
    private AppointmentStatus appointmentStatus;

    //Outcome Record
    private String typeOfService;
    private List<PrescribeMedication> medications;   //can leave undefined first?
    private String consultationNotes;



    private static final long serialVersionUID = 2L;



    public Appointment (String doctorID, String patientID, String date, String time) {
        this.doctorID = doctorID;
        this.patientID = patientID;
        this.date = date;
        this.time = time;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.typeOfService = "N/A";
        this.consultationNotes = "N/A";
    }

    public String getDoctorID () {
        return this.doctorID;
    }

    public String getPatientID () {
        return this.patientID;
    }

    public void setDoctorID (String id) {
        this.doctorID = id;
    }

    public String getDate () {
        return this.date;
    }

    public void setDate (String time) {
        this.time = time;
    }

    public String getTime () {
        return this.time;
    }

    public void setTime (String time) {
        this.time = time;
    }

    public AppointmentStatus getAppointmentStatus () {
        return this.appointmentStatus;
    }

    public void setAppointmentStatus (AppointmentStatus status) {
        this.appointmentStatus = status;
    }

    public String getTypeOfService () {
        return this.typeOfService;
    }

    public void setTypeOfService (String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public List<PrescribeMedication> getPrescribeMedications () {
        return this.medications;
    }

    public void setPrescribeMedications (List<PrescribeMedication> medications) {
        this.medications = medications;
    }

    public String getConsultationNotes () {
        return this.consultationNotes;
    }

    public void setConsultationNotes (String consultationNotes) {
        this.consultationNotes = consultationNotes;
    }



    //add medication to list
    public void addMedication(PrescribeMedication prescribeMedication) {
        this.medications.add(prescribeMedication);   
    }
}