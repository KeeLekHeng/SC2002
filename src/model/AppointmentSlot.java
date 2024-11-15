package src.model;


public class AppointmentSlot {


    public final String doctorID;
    public TimeSlot timeSlot;

    public AppointmentSlot (String doctorID, TimeSlot timeSlot) {
        this.doctorID = doctorID;
        this.timeSlot = timeSlot;
    }

    public String getDoctorID () {
        return this.doctorID;
    }

    public TimeSlot getTimeSlot () {
        return this.timeSlot;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }
}