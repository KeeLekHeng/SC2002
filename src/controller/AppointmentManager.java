package src.controller;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.AppOutcomeRecord;
import src.model.Appointment;
import src.model.AppointmentSlot;
import src.model.Patient;
import src.model.PrescribeMedication;
import src.model.Staff;
import src.model.TimeSlot;
import src.model.enums.AppointmentStatus;
import src.model.enums.PrescribeStatus;

/**
 * AppointmentManager is a controller class responsible for managing appointment operations.
 * It allows the user to schedule, view, and cancel appointments, check the availability of doctors, 
 * and validate appointment ownership.
 * @author Benjamin, Kee
 * @version 1.0
 * @since 2024-11-20
 */
public class AppointmentManager {

    /**
     * Constructor for AppointmentManager.
     */
    public AppointmentManager() {

    }

    /**
     * Fetch the list of employed doctors from the database and print out their details.
     */
    public static void getDoctorList() {
        List<Staff> doctorList = new ArrayList<Staff>();
        for (Staff staff : Database.STAFF.values()) {
            String staffID = staff.getId();
            if (staffID.startsWith("D") && staffID.substring(1).matches("\\d{3}")
                    && staff.getEmploymentStatus().equals("EMPLOYED")) {
                doctorList.add(staff);
            }
        }
        int numberOfDoctors = doctorList.size();

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println("Our hospital has " + numberOfDoctors + " doctors. Doctors List:");
        for (Staff staff : doctorList) {
            if (staff.getRatingCount() == 0) {

            }
            System.out.println(
                    "Name: " + staff.getName() + " | DoctorID: " + staff.getId() + " | Rating: "
                            + ((staff.getRatingCount() == 0) ? "no ratings" : staff.getRating()));
        }
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    /**
     * Generate available time slots for appointments based on the given date.
     * @param date The date for which time slots need to be generated.
     * @return A list of available time slots.
     */
    public static List<TimeSlot> generateTimeSlots(LocalDate date) {
        final int startHour = 9;
        final int endHour = 17;
        final int slotDuration = 60;

        List<TimeSlot> timeSlots = new ArrayList<>();

        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);

        while (!startTime.isAfter(endTime)) {
            timeSlots.add(new TimeSlot(date.atTime(startTime)));
            startTime = startTime.plusMinutes(slotDuration); // Increment by slot duration
        }

        return timeSlots;
    }

    /**
     * Get the available appointment slots for a specific doctor on a given date.
     * @param newDateTime The date to check available slots.
     * @param doctorID    The ID of the doctor.
     * @return A list of available appointment slots.
     */
    public static List<AppointmentSlot> getAvailableSlotsByDoctor(LocalDate newDateTime, String doctorID) {
        List<AppointmentSlot> availableSlots = new ArrayList<AppointmentSlot>();

        for (TimeSlot slot : generateTimeSlots(newDateTime)) {
            if (isAppointmentSlotAvailable(slot, doctorID)) {
                availableSlots.add(new AppointmentSlot(doctorID, slot));
            }
        }
        return availableSlots;
    }

    /**
     * Check if a specific appointment slot is available for the given doctor.
     * @param timeSlot The time slot to check.
     * @param doctorID The ID of the doctor.
     * @return true if the slot is available, false otherwise.
     */
    public static boolean isAppointmentSlotAvailable(TimeSlot timeSlot, String doctorID) {
        List<Appointment> slots = new ArrayList<Appointment>();

        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getDoctorID().equals(doctorID)) {
                slots.add(appointment);
            }
        }

        for (Appointment appointment : slots) {
            if (appointment.getTimeSlot().equals(timeSlot)
                    && appointment.getAppointmentStatus() != AppointmentStatus.CANCELED) {
                return false;
            }
        }
        return true;
    }

    /**
     * Validate the ownership of an appointment based on the given appointment ID and hospital ID.
     * @param appointmentID The ID of the appointment to validate.
     * @param hospitalID    The hospital ID of the user (either patient or doctor).
     * @return true if the user owns the appointment, false otherwise.
     */
    public static boolean validateAppointmentOwnership(String appointmentID, String hospitalID) {

        if (Database.APPOINTMENT.containsKey(appointmentID)) {
            Appointment appointment = Database.APPOINTMENT.get(appointmentID);
            TimeSlot timeSlot = appointment.getTimeSlot();
            if (hospitalID.startsWith("P") && hospitalID.substring(1).matches("\\d{4}")) {
                if (!appointment.getPatientID().equals(hospitalID)) {
                    System.out.println("You have no appointment scheduled for" + timeSlot.getFormattedDate() + "at"
                            + timeSlot.getFormattedTime());
                    return false;
                }
            } else if (hospitalID.startsWith("D") && hospitalID.substring(1).matches("\\d{3}")) {
                if (!appointment.getDoctorID().equals(hospitalID)) {
                    System.out.println("You have no patient appointment scheduled for" + timeSlot.getFormattedDate()
                            + "at" + timeSlot.getFormattedTime());
                    return false;
                }
            } else {
                System.out.println("Appointment Not Found!");
                return false;
            }
        }
        return true;
    }

    /**
     * View the available appointment slots for a patient with a specific doctor on a specific date.
     * @param patientID The patient ID.
     * @param doctorID  The doctor ID.
     * @param date      The date to check for available slots.
     */
    public static void viewAvailableAppointmentSlots(String patientID, String doctorID, LocalDate date) {

        List<TimeSlot> timeSlots = AppointmentManager.generateTimeSlots(date);

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println("Available Appointment Slots for " + date + ":");

        // print out all available slots for that doctor
        for (TimeSlot slot : timeSlots) {
            if (isAppointmentSlotAvailable(slot, doctorID)) {
                System.out.println(slot.getFormattedTime());
            }
        }
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    /**
     * Schedule an appointment for a patient with a specific doctor at a specified time slot.
     * @param patientID The ID of the patient scheduling the appointment.
     * @param doctorID  The ID of the doctor for the appointment.
     * @param timeSlot  The time slot for the appointment.
     * @return true if the appointment was successfully scheduled, false otherwise.
     */
    public static boolean scheduleAppointment(String patientID, String doctorID, TimeSlot timeSlot) {

        if (!isAppointmentSlotAvailable(timeSlot, doctorID)) {
            System.out.println("Time slot " + timeSlot.getFormattedTime() + " on " + timeSlot.getFormattedDate()
                    + " is not available.");
            return false;
        }

        Appointment appointment;
        int aid = Helper.generateUniqueId(Database.APPOINTMENT);
        String appointmentID = String.format("A%05d", aid);
        appointment = new Appointment(appointmentID, doctorID, patientID, timeSlot);
        System.out.println("Appointment scheduled for " + timeSlot.getFormattedDate() + " at "
                + timeSlot.getFormattedTime() + ". Appointment Details:");
        printAppointmentDetails(appointment);
        Database.APPOINTMENT.put(appointmentID, appointment);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
        return true;
    }

    /**
     * Cancel an appointment for a patient based on the given appointment ID and patient ID.
     * @param appointmentID The ID of the appointment to cancel.
     * @param patientID     The ID of the patient who is canceling the appointment.
     * @return true if the appointment was successfully canceled, false otherwise.
     */
    public static boolean cancelAppointment(String appointmentID, String patientID) {

        if (!validateAppointmentOwnership(appointmentID, patientID)) {
            System.out.println("You do not have an appointment with this AppointmentID:" + appointmentID);
            return false;
        }

        Appointment appointment = Database.APPOINTMENT.get(appointmentID);
        TimeSlot timeSlot = appointment.getTimeSlot();

        if (Helper.promptConfirmation("cancel this appointment")) {
            appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
            System.out.println("You have cancelled the appointment on " + timeSlot.getFormattedDate() + " at "
                    + timeSlot.getFormattedTime());

            Database.APPOINTMENT.put(appointmentID, appointment);
            Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
            return true;
        } else {
            System.out.println("Appointment is not cancelled");
            return false;
        }
    }


    /**
     * Fetch appointment Outcome Records for patients based on the provided code.
     * @param code          1 for fetching by appointment ID, 2 for fetching all
     *                      past appointments for a patient.
     * @param patientID     The patient ID (required if code is 2).
     * @param appointmentID The appointment ID (required if code is 1).
     * @return true if the appointment(s) were found and printed successfully, false otherwise.
     */
    public static boolean fetchAppointmentOutcomeRecords(int code, String patientID, String appointmentID) {
        switch (code) {
            case 1:
                if (Database.APPOINTMENT.isEmpty()) {
                    System.out.println("Appointment with ID " + appointmentID + " not found.");
                    return false;
                }

                Appointment appointment = Database.APPOINTMENT.get(appointmentID);
                if (appointment != null) {
                    System.out.println("Appointment Details:");
                    printAppointmentOutcomeRecord(appointment);
                    return true;
                } else {
                    System.out.println("Appointment with ID " + appointmentID + " not found.");
                    return false;
                }
            case 2:
                List<Appointment> pastAppointments = new ArrayList<>();
                if (Database.APPOINTMENT.isEmpty()) {
                    System.out.println("No past appointments found for patient ID " + patientID);
                    return false;
                }

                for (Appointment app : Database.APPOINTMENT.values()) {
                    if (app.getPatientID() != null && app.getPatientID().equals(patientID)) {
                        pastAppointments.add(app);
                    }
                }
                if (pastAppointments.isEmpty()) {
                    System.out.println("No past appointments found for patient ID " + patientID);
                    return false;
                } else {
                    System.out.println("Past Appointments for Patient ID " + patientID + ":");
                    for (Appointment pastAppointment : pastAppointments) {
                        printAppointmentOutcomeRecord(pastAppointment);
                    }
                    return false;
                }

            default:
                System.out.println(
                        "Invalid code! Use 1 for fetching by ID, or 2 for fetching all past appointments for a patient.");
                return false;
        }
    }

    /**
     * View scheduled appointments for a specific hospital (patient or doctor).
     * @param hospitalID The hospital ID (patient or doctor ID).
     * @param attributeCode The type of appointments to fetch (1 for scheduled, 2 for all available).
     * @return true if appointments were found and printed, false otherwise.
     */
    public static boolean viewScheduledAppointments(String hospitalID, int attributeCode) {
        List<Appointment> appointmentList = new ArrayList<Appointment>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (Database.APPOINTMENT.isEmpty()) {
            System.out.println("No appointments in the system.");
            return false;
        }

        switch (attributeCode) {
            case 1:
                for (Appointment appointment : Database.APPOINTMENT.values()) {
                    if (hospitalID.startsWith("P") && hospitalID.substring(1).matches("\\d{4}")) {
                        if (appointment.getPatientID() != null &&
                                appointment.getTimeSlot().getDateTime().isAfter(currentDateTime) &&
                                appointment.getPatientID().equals(hospitalID)) {
                            appointmentList.add(appointment);
                        }
                    } else if (hospitalID.startsWith("D") && hospitalID.substring(1).matches("\\d{3}")) {
                        if (appointment.getDoctorID() != null &&
                                appointment.getTimeSlot().getDateTime().isAfter(currentDateTime) &&
                                appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                                appointment.getDoctorID().equals(hospitalID)) {
                            appointmentList.add(appointment);
                        }
                    } else {
                        if (appointment.getPatientID() != null &&
                                appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                                appointment.getTimeSlot().getDateTime().isAfter(currentDateTime)) {
                            appointmentList.add(appointment);
                        }
                    }
                }
                break;
            case 2:
                for (Appointment appointment : Database.APPOINTMENT.values()) {
                    if (hospitalID.startsWith("P") && hospitalID.substring(1).matches("\\d{4}")) {
                        if (appointment.getPatientID() != null &&
                                appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                                appointment.getPatientID().equals(hospitalID)) {
                            appointmentList.add(appointment);
                        }
                    } else if (hospitalID.startsWith("D") && hospitalID.substring(1).matches("\\d{3}")) {
                        if (appointment.getDoctorID() != null &&
                                appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                                appointment.getDoctorID().equals(hospitalID)) {
                            appointmentList.add(appointment);
                        }
                    } else {
                        if (appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE)
                            appointmentList.add(appointment);
                    }
                }
                break;
            default:
                return false;
        }
        if (!appointmentList.isEmpty()) {
            System.out.println("Here are your scheduled appointments:");
            for (Appointment appointment : appointmentList) {
                printAppointmentDetails(appointment);
            }
            return true;
        } else {
            System.out.println("No appointments scheduled");
            return false;
        }
    }

    /**
     * Reschedule an existing appointment to a new time slot.
     * @param appointmentID The ID of the appointment to be rescheduled.
     * @param patientID The ID of the patient who owns the appointment.
     * @param newTimeSlot The new time slot for the appointment.
     * @return true if the appointment was rescheduled successfully, false otherwise.
     */
    public static boolean rescheduleAppointment(String appointmentID, String patientID, TimeSlot newTimeSlot) {

        if (!validateAppointmentOwnership(appointmentID, patientID)) {
            return false;
        }

        Appointment appointment = Database.APPOINTMENT.get(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }

        if (isAppointmentSlotAvailable(newTimeSlot, appointment.getDoctorID())) {
            if (!cancelAppointment(appointmentID, patientID)) {
                return false;
            }

            int aid = Helper.generateUniqueId(Database.APPOINTMENT);
            String newAppointmentID = String.format("A%05d", aid);
            Appointment newAppointment = new Appointment(newAppointmentID, appointment.getDoctorID(), patientID,
                    newTimeSlot);

            Database.APPOINTMENT.put(newAppointmentID, newAppointment);
            Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
            return true;
        } else {
            System.out.println("Time slot " + newTimeSlot.getFormattedDateTime() + " is not available.");
            return false;
        }
    }

    /**
     * Fetch the past appointment outcome records for a specific patient.
     * @param patientID The ID of the patient whose past appointments will be fetched.
     */
    public static void viewPastAppointmentOutcomeRecords(String patientID) {
        LocalDateTime currentDateTime = LocalDateTime.now();

        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getPatientID().equals(patientID)) {

                if (appointment.getAppOutcomeRecord() != null &&
                        appointment.getTimeSlot().getDateTime().isBefore(currentDateTime)) {
                    System.out.println("Past Appointment ID: " + appointment.getAppointmentID());
                    printAppointmentOutcomeRecord(appointment);
                } else {
                    System.out.println("No past appointment outcome records found.");
                }
            }
        }
    }

    /**
     * View pending appointment requests for a doctor.
     * @param doctorID The ID of the doctor whose pending appointments are to be viewed.
     * @return True if there are pending appointment requests, false otherwise.
     */
    public static boolean viewPendingAppointmentRequeest(String doctorID) {
        ArrayList<Appointment> pendingAppointmentsList = new ArrayList<Appointment>();

        for (Appointment app : Database.APPOINTMENT.values()) {
            if (app.getDoctorID().equals(doctorID) && app.getAppointmentStatus() == AppointmentStatus.PENDING) {
                pendingAppointmentsList.add(app);
            }
        }

        if (pendingAppointmentsList.isEmpty()) {
            System.out.println("You have no pending appointment requests.");
            return false;
        } else {
            for (Appointment app : pendingAppointmentsList) {
                printAppointmentDetails(app);
            }
            return true;
        }
    }

    /**
     * Update the status of an appointment request.
     * @param appointmentID The appointment ID whose request is to be updated.
     * @param staffID       The staff ID who is updating the appointment.
     * @param attributeCode The code specifying the update: 1 for confirmation, 2 for cancellation.
     * @return True if the appointment was successfully updated, false otherwise.
     */
    public static boolean updateAppointmentRequest(String appointmentID, String staffID, int attributeCode) {
        if (validateAppointmentOwnership(appointmentID, staffID)) {
            Appointment appointment = Database.APPOINTMENT.get(appointmentID);
            switch (attributeCode) {
                case 1:
                    appointment.setAppointmentStatus(AppointmentStatus.CONFIRMED);
                    break;
                case 2:
                    appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
                    break;
                default:
                    break;
            }
            Database.APPOINTMENT.put(appointmentID, appointment);
            Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Set a doctor's availability for a specific time slot.
     * @param doctorID The ID of the doctor marking availability.
     * @param timeSlot The time slot the doctor is marking as available or unavailable.
     * @return True if the availability was successfully set, false otherwise.
     */
    public static boolean setAvailability(String doctorID, TimeSlot timeSlot) {
        if (!isAppointmentSlotAvailable(timeSlot, doctorID)) {
            System.out.println("There is a Pending or Confirmed Appointment at " + timeSlot.getFormattedDateTime());
            return false;
        }
        String appointmentID = "UNAVAILABLE-" + doctorID + "-" + timeSlot.getFormattedDateTime();
        Appointment unavailableAppointment = new Appointment(appointmentID, doctorID, null, timeSlot);
        unavailableAppointment.setAppointmentStatus(AppointmentStatus.UNAVAILABLE);

        System.out.println("Doctor " + doctorID + " has marked " + timeSlot.getFormattedDateTime() + " as unavailable.");
        Database.APPOINTMENT.put(appointmentID, unavailableAppointment);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
        return true;
    }

    /**
     * Search for an appointment by its ID.
     * @param appointmentID The ID of the appointment to be searched.
     * @return The Appointment object if found, null otherwise.
     */
    public static Appointment searchAppointmentByID(String appointmentID) {
        if (Database.APPOINTMENT.containsKey(appointmentID)) {
            return Database.APPOINTMENT.get(appointmentID);
        } else {
            return null;
        }
    }

    /**
     * View the list of patients under a specific doctor's care.
     * @param doctorID The ID of the doctor whose patients are to be viewed.
     */
    public static void viewPatientsUnderCare(String doctorID) {
        List<String> patientsUnderCare = new ArrayList<>();

        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getDoctorID().equals(doctorID)) {
                if (!patientsUnderCare.contains(appointment.getPatientID())) {
                    patientsUnderCare.add(appointment.getPatientID());
                }
            }
        }
        if (patientsUnderCare.isEmpty()) {
            System.out.println("You currently have no patients under your care.");
        } else {
            System.out.println("Patients under your care:");
            for (String patientID : patientsUnderCare) {
                Patient patient = Database.PATIENTS.get(patientID);
                if (patient != null) {
                    System.out.println("Patient ID: " + patient.getId() + " | Patient Name: " + patient.getName());
                } else {
                    System.out.println("Patient ID: " + patientID + " | Patient details not found.");
                }
            }
        }
    }

    /**
     * Record the outcome of an appointment, including service type, consultation notes, and prescribed medications.
     * @param appointmentID The ID of the appointment.
     * @param doctorID      The ID of the doctor.
     * @param typeOfService The type of service provided during the appointment.
     * @param consultationNotes The consultation notes recorded by the doctor.
     * @param medications The list of medications prescribed during the appointment.
     * @return True if the appointment outcome was successfully recorded, false otherwise.
     */
    public static boolean recordAppointmentOutcome(String appointmentID, String doctorID, String typeOfService, String consultationNotes, List<PrescribeMedication> medications) {
        if (!validateAppointmentOwnership(appointmentID, doctorID)) {
            return false;
        }

        Appointment appointment = Database.APPOINTMENT.get(appointmentID);

        AppOutcomeRecord outcomeRecord = new AppOutcomeRecord();
        outcomeRecord.setConsultationNotes(consultationNotes);
        outcomeRecord.setTypeOfService(typeOfService);

        if (!medications.isEmpty()) {
            int pid = Helper.generateUniqueId(Database.APPOINTMENT);
            String prescriptionID = String.format("P%05d", pid);

            outcomeRecord.setPrescriptionID(prescriptionID);
            outcomeRecord.setPrescribeMedications(medications);
            Database.PRESCRIPTION.put(prescriptionID, medications);
            Database.saveFileIntoDatabase(FileType.PRESCRIPTIONS);
        } else {
            outcomeRecord.setPrescribeMedications(null);
            outcomeRecord.setPrescribeStatus(PrescribeStatus.NA);
        }

        appointment.setAppOutcomeRecord(outcomeRecord);
        appointment.setAppointmentStatus(AppointmentStatus.COMPLETED);

        System.out.println("Appointment Outcome Record Set!");
        printAppointmentOutcomeRecord(appointment);
        Database.APPOINTMENT.put(appointmentID, appointment);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);

        return true;
    }


    /**
     * Prints all confirmed appointment requests for a specific doctor.
     * This method retrieves all appointments from the database that are associated
     * with the specified doctor's ID and have a status of "CONFIRMED". It then 
     * displays the details of these appointments in a formatted manner. If there 
     * are no confirmed appointments, a message indicating that is displayed.
     * @param doctorID the unique identifier of the doctor for whom the confirmed 
     *                 appointment requests are to be printed
     */
    public static void printConfirmedAppointmentRequest(String doctorID){
        ArrayList<Appointment> pendingAppointmentsList = new ArrayList<Appointment>();
        for (Appointment app : Database.APPOINTMENT.values()) {
            if (app.getDoctorID().equals(doctorID) && app.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
                pendingAppointmentsList.add(app);
            }
        }

        if (pendingAppointmentsList.isEmpty()) {
            System.out.println("You have no pending appointment requests.");
            return;
        } else {
            for (Appointment app : pendingAppointmentsList) {
                    System.out.println("Appointment ID: " + app.getAppointmentID() + " | Patient Name: " + Database.PATIENTS.get(app.getPatientID()).getName() + 
                    " | Appointment Time Slot: " + app.getTimeSlot().getFormattedDateTime());
                } 
            }
        }

    /**
     * Prints the details of a given appointment including the appointment ID,
     * patient ID, doctor ID, doctor's name, appointment status, time slot, 
     * and any available outcome record.
     * @param appointment The appointment object containing the details to print.
     */
    public static void printAppointmentDetails(Appointment appointment) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Appointment ID", appointment.getAppointmentID()));
        System.out.println(String.format("%-20s: %s", "Patient ID", appointment.getPatientID()));
        System.out.println(String.format("%-20s: %s", "Doctor ID", appointment.getDoctorID()));
        System.out.println(
                String.format("%-20s: %s", "Doctor's Name", Database.STAFF.get(appointment.getDoctorID()).getName()));
        System.out.println(String.format("%-20s: %s", "Appointment Status", appointment.getAppointmentStatus()));
        System.out.println(String.format("%-20s: %s", "Time Slot", appointment.getTimeSlot().getFormattedDateTime()));

        if (appointment.getAppOutcomeRecord() != null) {
            System.out.println(String.format("%-20s: %s", "Outcome Record", "Present"));
            printAppointmentOutcomeRecord(appointment);
        } else {
            System.out.println(String.format("%-20s: %s", "Outcome Record", "None"));
        }

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    /**
     * Prints the outcome record of an appointment, including the record uploaded time,
     * type of service, consultation notes, and details of prescribed medications.
     * @param appointment The appointment object whose outcome record is to be printed.
     */
    public static void printAppointmentOutcomeRecord(Appointment appointment) {
        AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
        if (outcomeRecord == null) {
            return;
        }

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Record Uploaded Time", outcomeRecord.getEndDateTime()));
        System.out.println(String.format("%-20s: %s", "Type of Service", outcomeRecord.getTypeOfService()));
        System.out.println(String.format("%-20s: %s", "Consultation Notes", outcomeRecord.getConsultationNotes()));

        List<PrescribeMedication> medications = outcomeRecord.getPrescribeMedications();
        if (medications.isEmpty()) {
            System.out.println("  No medications prescribed.");
        } else {
            System.out.println(String.format("%-20s: %s", "Prescription ID", outcomeRecord.getPrescriptionID()));
            System.out.println(String.format("%-20s: %s", "Prescription Status", outcomeRecord.getPrescribeStatus()));
            System.out.println(String.format("%-20s:", "Prescribed Medications:"));
            for (PrescribeMedication med : medications) {
                System.out.println(String.format("  %-20s: %s", "Medication Name", med.getMedicationName()));
                System.out.println(String.format("  %-20s: %d", "Amount", med.getPrescriptionAmount()));
            }
        }
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
}