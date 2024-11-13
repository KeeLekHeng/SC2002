package src.model;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents an outcome record, containing a list of past appointments.
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class AppOutcomeRecord implements Serializable {

    /** List of past appointments. */
    private List<Appointment> pastAppointments;

    /** Serialization identifier for the AppOutcomeRecord class. */
    private static final long serialVersionUID = 3L;

    /**
     * Adds an appointment to the list of past appointments.
     * @param appointment Appointment to add to the list.
     */
    public void addAppointment(Appointment appointment) {
        if (this.pastAppointments == null) {
            this.pastAppointments = new ArrayList<>(); // Initialize if not already initialized
        }
        this.pastAppointments.add(appointment);
    }
}

