package src.model;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

//basically just list of past appointments;
//list need to initialise or not?

public class AppOutcomeRecord implements Serializable {
    private List<Appointment> pastAppointments;

    private static final long serialVersionUID = 3L;


    public void addAppointment(Appointment appointment)
    {
        this.pastAppointments.add(appointment);
    }
}
