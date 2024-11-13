package src.controller;

import java.sql.DatabaseMetaData;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

import java.time.LocalDate;

import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.Appointment;
import src.model.TimeSlot;

public class AppointmentManager {
    
    //Date String format:        2024-11-12
    private static HashMap<String, HashMap<String, Appointment>> appointmentSchedule;

    public AppointmentManager() 
    {
        appointmentSchedule = Database.APPOINTMENT.isEmpty() ? new HashMap<>() : Database.APPOINTMENT;
    }


    //timeSlots in a day
    public static List<TimeSlot> generateTimeSlots(LocalDate date){
        final int startHour = 12;
        final int endHour = 5;
        final int slotDuration = 30;

        List<TimeSlot> timeSlots = new ArrayList<TimeSlot>();
        for (int hour = startHour; hour <= endHour; hour ++){
            for (int minute = 0; minute < 60; minute += slotDuration){
                timeSlots.add(new TimeSlot(date.atTime(hour,minute)));
            }
        }
        return timeSlots;
    }

    public List<Appointment> getAvailableSlotsByDoctor(LocalDate date, Doctor doctor){
        
    }

    public static void initalizeSlotsForDate(String date){
        HashMap<String, Appointment> slots = new HashMap<>();
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
        
        List<String> timeSlotsOrder = Arrays.asList("1pm", "2pm", "3pm", "4pm", "5pm");

        for (String time : timeSlotsOrder) {
            Appointment appointment = appointmentSchedule.get(date).get(time);

            //Print out only if it is free
            if (appointment == null) {
                System.out.println(time); 
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

    public static boolean cancelAppointment(String patientID,String date, String time){
        if (appointmentSchedule.get(date).get(time) != null){
            Appointment appointment = appointmentSchedule.get(date).get(time);

            if (appointment.getPatientID().equals(patientID)){
                if (Helper.promptConfirmation("cancel this appointment")){
                    HashMap<String, Appointment> timeSlots = appointmentSchedule.get(date);

                    timeSlots.remove(time);
                    timeSlots.put(time, null);
                    Database.APPOINTMENT = appointmentSchedule;
                    Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
                    return true;
                }
            } else {
            System.out.println("You have no appointment scheduled for" + date + "at" + time);
            return false;
            }
        } else {
        System.out.println("You have no appointment scheduled for" + date + "at" + time);
        return false;
        }
    }


    
    //want implement like cant reschedule a day before appointment??
    public static boolean rescheduleAppointment(String patientID, String oldDate, String newDate, String oldTime, String newTime){
        
        Appointment oldAppointment = appointmentSchedule.get(oldDate).get(oldTime);

        //check see if got patient appointment
        if (oldAppointment == null){
            System.out.println("You have no appointment scheduled for" + oldDate + "at" + oldTime);
            return false;
        }
        if (!oldAppointment.getPatientID().equals(patientID)){
            System.out.println("You have no appointment scheduled for" + oldDate + "at" + oldTime);
            return false;
        }

        if(appointmentSchedule.get(newDate).get(newTime) == null){
            Appointment newAppointment = oldAppointment;
            AppointmentManager.scheduleAppointment(patientID, newAppointment.getDoctorID(), newDate, newTime);
            AppointmentManager.cancelAppointment(patientID, oldDate, oldTime);
            Database.APPOINTMENT = appointmentSchedule;
            Database.saveFileIntoDatabase(FileType.APPOINTMENTS);
            return true;
        } else {
            System.out.println("Time slot " + newTime + " on " + newDate + " is not available.");
            return false;
        }


    }

    
    public static void viewPastAppointmentOutcomeRecords(String patientID){

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
