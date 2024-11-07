//before appointment is completed, the "Outcome Record" values are just left default

package src.model;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import src.model.enums.AppointmentStatus;


public class Appointment implements Serializable {
    private int doctorID;
    private String date;
    private String time;
    private AppointmentStatus appointmentStatus;

    //Outcome Record
    private String typeOfService;
    private List<PrescribeMedication> medications;   //can leave undefined first?
    private String consultationNotes;


    public Appointment (int doctorID, String date, String time) {
        this.doctorID = doctorID;
        this.date = date;
        this.time = time;
        this.appointmentStatus = AppointmentStatus.PENDING;
        this.typeOfService = "N/A";
        this.consultationNotes = "N/A";
    }

    public int getDoctorID () {
        return this.doctorID;
    }

    public void setDoctorID (int id) {
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

    //add medication to list
    public void addMedication(PrescribeMedication prescribeMedication) {
        this.medications.add(prescribeMedication);   
    }
}