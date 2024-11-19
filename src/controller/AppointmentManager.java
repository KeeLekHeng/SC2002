package src.controller;

import java.sql.DatabaseMetaData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

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

public class AppointmentManager {
    

    public AppointmentManager() 
    {

    }

    public static void getDoctorList(){
        List<Staff> doctorList = new ArrayList<Staff>();
        for(Staff staff : Database.STAFF.values()){
            String staffID = staff.getId();
            if (staffID.startsWith("D") && staffID.substring(1).matches("\\d{3}")){
                doctorList.add(staff);
            }
        }
        int numberOfDoctors = doctorList.size();

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println("Our hospital has " + numberOfDoctors + " doctors. Doctors List:");
        for (Staff staff : doctorList){
            System.out.println("Name: " + staff.getName() + " , DoctorID: " + staff.getId());
        }
        System.out.println(String.format("%-40s", "").replace(" ", "-")); 
    }
    
    //timeSlots in a day
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

    public static List<AppointmentSlot> getAvailableSlotsByDoctor(LocalDate newDateTime, String doctorID){
        List<AppointmentSlot> availableSlots = new ArrayList<AppointmentSlot>();

        for (TimeSlot slot: generateTimeSlots(newDateTime)){
            if (isAppointmentSlotAvailable(slot, doctorID)){
                availableSlots.add(new AppointmentSlot(doctorID, slot));
            }
        }
        return availableSlots;
    }

    public static boolean isAppointmentSlotAvailable(TimeSlot timeSlot, String doctorID){
        List<Appointment> slots = new ArrayList<Appointment>();

        //all scheduled appointments of doctor
        for (Appointment appointment : Database.APPOINTMENT.values()){
            if (appointment.getDoctorID().equals(doctorID)){
                    slots.add(appointment);
            }
        }

        for (Appointment appointment : slots){
            if (appointment.getTimeSlot().equals(timeSlot) && appointment.getAppointmentStatus() != AppointmentStatus.CANCELED) {
                return false; 
            }
        }
        return true;
    }

    public static boolean validateAppointmentOwnership(String appointmentID, String hospitalID){
        //check if got appointment then 

        if (Database.APPOINTMENT.containsKey(appointmentID)){
            Appointment appointment = Database.APPOINTMENT.get(appointmentID);
            TimeSlot timeSlot = appointment.getTimeSlot();
            
            //Validate Patient or Doctor
            if (hospitalID.startsWith("P") && hospitalID.substring(1).matches("\\d{4}")) {
                if(!appointment.getPatientID().equals(hospitalID)){
                    System.out.println("You have no appointment scheduled for" + timeSlot.getFormattedDate() + "at" + timeSlot.getFormattedTime());
                    return false;
                }
            } else if (hospitalID.startsWith("D") && hospitalID.substring(1).matches("\\d{3}")){
                if(!appointment.getDoctorID().equals(hospitalID)){
                    System.out.println("You have no patient appointment scheduled for" + timeSlot.getFormattedDate() + "at" + timeSlot.getFormattedTime());
                    return false;
            }
        } else {
            System.out.println("Appointment Not Found!");
            return false;
            }
        }
        //all goes well
        return true;
    }


    //FOR PATIENTS

    //what if type wrongly not in that form (NEED TO ENSURE DATE ENTERED IS CORRECT, IF NOT USE HELPER FUNCTION TO HELP U VALIDATE)
    public static void viewAvailableAppointmentSlots(String patientID, String doctorID, LocalDate date){
        
        List<TimeSlot> timeSlots = AppointmentManager.generateTimeSlots(date);

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println("Available Appointment Slots for " + date + ":");
        
        //print out all available slots for that doctor
        for (TimeSlot slot : timeSlots) {
            if(isAppointmentSlotAvailable(slot, doctorID)){
                System.out.println(slot.getFormattedTime());
            }
        }
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    //TimeSlot is both date and time (refer to timeslot to see how to get formatted time)           I used doctorID and timeslot seperately ###   I returning appointmentID    
    public static boolean scheduleAppointment(String patientID, String doctorID, TimeSlot timeSlot){

        //check if available for that doctor 
        if(!isAppointmentSlotAvailable(timeSlot, doctorID)){
            System.out.println("Time slot " + timeSlot.getFormattedTime() + " on " + timeSlot.getFormattedDate() + " is not available.");
            return false;
        }


        Appointment appointment;
        int aid = Helper.generateUniqueId(Database.APPOINTMENT);
        String appointmentID = String.format("A%05d", aid);
        appointment = new Appointment(appointmentID, doctorID, patientID, timeSlot);
        System.out.println("Appointment scheduled for " + timeSlot.getFormattedDate() + " at " + timeSlot.getFormattedTime() + ". Appointment Details:");
        printAppointmentDetails(appointment);
        Database.APPOINTMENT.put(appointmentID, appointment);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
        return true;
    }
    
    //jsut need appointment and patient id
    public static boolean cancelAppointment(String appointmentID, String patientID){

        //check if got appointment then or not and u are the patient
        if(!validateAppointmentOwnership(appointmentID, patientID)){
            System.out.println("You do not have an appointment with this AppointmentID:" + appointmentID);
            return false;
        }
        

        Appointment appointment = Database.APPOINTMENT.get(appointmentID);
        TimeSlot timeSlot = appointment.getTimeSlot();
            
        if (Helper.promptConfirmation("cancel this appointment")){
            appointment.setAppointmentStatus(AppointmentStatus.CANCELED);
            System.out.println("You have cancelled the appointment on " + timeSlot.getFormattedDate() + " at " + timeSlot.getFormattedTime());

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
     *
     * @param code          1 for fetching by appointment ID, 2 for fetching all past appointments for a patient.
     * @param patientID     The patient ID (required if code is 2).
     * @param appointmentID The appointment ID (required if code is 1).
     */
    public static boolean fetchAppointmentOutcomeRecords(int code, String patientID, String appointmentID) {
        switch (code) {
            case 1:
                if (Database.APPOINTMENT.isEmpty()){
                    System.out.println("Appointment with ID " + appointmentID + " not found.");
                    return false;
                }

                // Fetch appointment by ID
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
                // Fetch all appointments for a specific patient
                List<Appointment> pastAppointments = new ArrayList<>();
                if (Database.APPOINTMENT.isEmpty()){
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
                System.out.println("Invalid code! Use 1 for fetching by ID, or 2 for fetching all past appointments for a patient.");
                return false;
        }
    }

    //do a if for 3 roles. attribute code   [1. for upcoming, 2. for all]
    public static boolean viewScheduledAppointments(String hospitalID, int attributeCode){
        List<Appointment> appointmentList = new ArrayList<Appointment>();
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (Database.APPOINTMENT.isEmpty()) {
            System.out.println("No appointments in the system.");
            return false;
        }

            //patient or Doctor or Admin
        switch(attributeCode){
            case 1:
                for (Appointment appointment : Database.APPOINTMENT.values()){
                if (hospitalID.startsWith("P") && hospitalID.substring(1).matches("\\d{4}")){
                    if (appointment.getPatientID() != null &&  
                        appointment.getTimeSlot().getDateTime().isAfter(currentDateTime) &&
                        appointment.getPatientID().equals(hospitalID)){
                        appointmentList.add(appointment);
                    }
                } else if (hospitalID.startsWith("D") && hospitalID.substring(1).matches("\\d{3}")){
                    if (appointment.getDoctorID() != null &&  
                    appointment.getTimeSlot().getDateTime().isAfter(currentDateTime) &&
                    appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                    appointment.getDoctorID().equals(hospitalID)){
                        appointmentList.add(appointment);
                    }
                } else {
                    if (appointment.getPatientID() != null &&
                        appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE&&
                        appointment.getTimeSlot().getDateTime().isAfter(currentDateTime)){
                        appointmentList.add(appointment);
                        }
                    }
                }
                break;
            case 2:
            for (Appointment appointment : Database.APPOINTMENT.values()){
                if (hospitalID.startsWith("P") && hospitalID.substring(1).matches("\\d{4}")){
                    if (appointment.getPatientID() != null &&
                        appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                        appointment.getPatientID().equals(hospitalID)){
                        appointmentList.add(appointment);
                    }
                } else if (hospitalID.startsWith("D") && hospitalID.substring(1).matches("\\d{3}")){
                    if (appointment.getDoctorID() != null&&
                        appointment.getAppointmentStatus() != AppointmentStatus.UNAVAILABLE &&
                        appointment.getDoctorID().equals(hospitalID)){
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
        if (!appointmentList.isEmpty()){
            System.out.println("Here are your scheduled appointments:");
            for (Appointment appointment : appointmentList){
            printAppointmentDetails(appointment);
            }
            return true;
        } else {
            System.out.println("No appointments scheduled");
            return false;
        }
    }



    
    //want implement like cant reschedule a day before appointment??
    public static boolean rescheduleAppointment(String appointmentID, String patientID, TimeSlot newTimeSlot) {
        
        if (!validateAppointmentOwnership(appointmentID, patientID)) {
            return false;
        }
    
        
        Appointment appointment = Database.APPOINTMENT.get(appointmentID);
        if (appointment == null) {
            System.out.println("Appointment not found.");
            return false;
        }
    
        // Check whether the new time slot is available
        if (isAppointmentSlotAvailable(newTimeSlot, appointment.getDoctorID())) {
            if(!cancelAppointment(appointmentID, patientID)){
                return false;
            }
    
            int aid = Helper.generateUniqueId(Database.APPOINTMENT);
            String newAppointmentID = String.format("M%05d", aid);
            Appointment newAppointment = new Appointment(newAppointmentID, appointment.getDoctorID(), patientID, newTimeSlot);
    
            Database.APPOINTMENT.put(newAppointmentID, newAppointment);
            Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
            return true;
        } else {
            System.out.println("Time slot " + newTimeSlot.getFormattedDateTime() + " is not available.");
            return false;
        }
    }

    
    public static void viewPastAppointmentOutcomeRecords(String patientID) {
        LocalDateTime currentDateTime = LocalDateTime.now();
    
        // Iterate through all the appointments in the database
        for (Appointment appointment : Database.APPOINTMENT.values()) {
            if (appointment.getPatientID().equals(patientID)) {
    
                if (appointment.getAppOutcomeRecord() != null && 
                    appointment.getTimeSlot().getDateTime().isBefore(currentDateTime)) {
                    
                    // Print the appointment details including the outcome record
                    System.out.println("Past Appointment ID: " + appointment.getAppointmentID());
                    printAppointmentOutcomeRecord(appointment); 
                } else {
                    System.out.println("No past appointment outcome records found.");
                }
            }
        }
    }

    

    public static boolean viewPendingAppointmentRequeest(String doctorID){
        ArrayList<Appointment> pendingAppointmentsList = new ArrayList<Appointment>();

        for(Appointment app : Database.APPOINTMENT.values()){
            if(app.getDoctorID().equals(doctorID) && app.getAppointmentStatus() == AppointmentStatus.PENDING){
                pendingAppointmentsList.add(app);
            }
        }

        if(pendingAppointmentsList.isEmpty()){
            System.out.println("You have no pending appointment requests.");
            return false;
        } else {
            for (Appointment app : pendingAppointmentsList){
                printAppointmentDetails(app);
            }
            return true;
        }
    }


    public static boolean updateAppointmentRequest(String appointmentID, String staffID, int attributeCode){
        if(validateAppointmentOwnership(appointmentID, staffID)){
            Appointment appointment = Database.APPOINTMENT.get(appointmentID);

            //1 for approve, 2 for reject
            switch(attributeCode){
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

    public static boolean setAvailability(String doctorID, TimeSlot timeSlot){

        if (!isAppointmentSlotAvailable(timeSlot, doctorID)){
            System.out.println("There is a Pending or Confirmed Appointment at" + timeSlot.getFormattedDateTime());
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
    
    public static Appointment searchAppointmentByID(String appointmentID) {
            // Check if the appointmentID exists in the database
            if (Database.APPOINTMENT.containsKey(appointmentID)) {
                return Database.APPOINTMENT.get(appointmentID);
            } else {
                // Return null if appointment is not found
                return null;
            }
        }
    //need to make a list of Prescribed Medication before passing it into this function
    public static boolean recordAppointmentOutcome(String appointmentID, String doctorID, String typeOfService, String consultationNotes, List<PrescribeMedication> medications){
        
        //check if is doctor's appointment
        if(!validateAppointmentOwnership(appointmentID, doctorID)){
            return false;
        }

        Appointment appointment = Database.APPOINTMENT.get(appointmentID);

        AppOutcomeRecord outcomeRecord = new AppOutcomeRecord();
        outcomeRecord.setConsultationNotes(consultationNotes);
        outcomeRecord.setTypeOfService(typeOfService);

        if (!medications.isEmpty()){

            //Making prescription ID
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


    public static void printAppointmentDetails(Appointment appointment) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Appointment ID", appointment.getAppointmentID()));
        System.out.println(String.format("%-20s: %s", "Patient ID", appointment.getPatientID()));
        System.out.println(String.format("%-20s: %s", "Doctor ID", appointment.getDoctorID()));
        System.out.println(String.format("%-20s: %s", "Appointment Status", appointment.getAppointmentStatus()));
        System.out.println(String.format("%-20s: %s", "Time Slot", appointment.getTimeSlot().getFormattedDateTime()));
    
        // If appointment has an outcome record, call the printAppointmentOutcomeRecord method
        if (appointment.getAppOutcomeRecord() != null) {
            System.out.println(String.format("%-20s: %s", "Outcome Record", "Present"));
            printAppointmentOutcomeRecord(appointment);  
        } else {
            System.out.println(String.format("%-20s: %s", "Outcome Record", "None"));
        }
    
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    public static void printAppointmentOutcomeRecord(Appointment appointment) {
        // Check if appointment has an outcome record
        AppOutcomeRecord outcomeRecord = appointment.getAppOutcomeRecord();
        if (outcomeRecord == null) {
            return;
        }
    
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Record Uploaded Time", outcomeRecord.getEndDateTime()));
        System.out.println(String.format("%-20s: %s", "Type of Service", outcomeRecord.getTypeOfService()));
        System.out.println(String.format("%-20s: %s", "Consultation Notes", outcomeRecord.getConsultationNotes()));
    
        // Print Prescribed Medications
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
                System.out.println(String.format("%-40s", "").replace(" ", "-"));
            }
        }
    
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
    
    

    


}
