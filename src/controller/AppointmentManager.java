package src.controller;

import java.sql.DatabaseMetaData;
import java.util.HashMap;
import java.util.Map;

import src.database.Database;
import src.database.FileType;

import src.model.Appointment;

public class AppointmentManager {
    
    //Date String format:        2024-11-12
    private static HashMap<String, HashMap<String, Appointment>> appointmentSchedule;

    public AppointmentManager() 
    {
        appointmentSchedule = Database.APPOINTMENT.isEmpty() ? new HashMap<>() : Database.APPOINTMENT;
    }


    public static void initalizeSlotsForDate(String date){
        Map<String, Appointment> slots = new HashMap<>();
        slots.put("12pm", null);
        slots.put("1pm", null);
        slots.put("2pm", null);
        slots.put("3pm", null);
        slots.put("4pm", null);
        appointmentSchedule.put(date, slots);
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
    }

    //FOR PATIENTS

    //what if type wrongly not in that form (NEED TO ENSURE DATE ENTERED IS CORRECT, IF NOT USE HELPER FUNCTION TO HELP U VALIDATE)
    public static void viewAvailableAppointmentSlots(String patientID, String date){
        if(!appointmentSchedule.containsKey(date)){
            initalizeSlotsForDate(date);
        }

        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println("Available Appointment Slots for" + date + ":");
        
        for (Map.Entry<String, Appointment> entry : appointmentSchedule.get(date).entrySet()) {
                
            //check slot availability (false/booked then we don't show)
            if (entry.getValue() == null){
                    System.out.println(entry.getKey());
                }
            }
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    public static void viewScheduledAppointments(String patientID){

    }

    //See if date and time is needed or not
    public static boolean scheduleAppointment(String patientID, String doctorID, String date, String time){

        if(!Database.APPOINTMENT.containsKey(date)){
            initalizeSlotsForDate(date);
        }

        //double check to see if appointment slot already booked
        if (appointmentSchedule.get(date).get(time) != null){
            System.out.println("Time slot " + time + " on " + date + " is not available.");
            return false;
        }

        Appointment appointment;
        appointment = new Appointment(doctorID, patientID, date, time);
        appointmentSchedule.get(date).put(time, appointment);
        System.out.println("Appointment scheduled for" + date + "at" + time);

        Database.APPOINTMENT = appointmentSchedule;
        Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
        return true;

    }



    

    public static boolean rescheduleAppointment(String PatientID){

    }

    public static boolean cancelAppointment(String PatientID){

    }

    public static void viewPastAppointmentOutcomeRecords(String PatientID){

    }

    //FOR DOCTOR


    public static void viewScheduledAppointments(String StaffID){

    }

    public static boolean updateAppointmentRequest(String StaffID, String AppointmentID){

    }

    public static boolean setAvailability(String StaffID){

    }

    public static boolean recordAppointmentOutcome(){

    }

    public static void printAllAppointments(String StaffID, boolean bytime){
        
    }
    public static void printAppointmentOutcomeRecord(Appointment appointment){

    }

    


}
