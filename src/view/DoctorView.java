package src.view;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.controller.AppointmentManager;
import src.controller.LoginManager;
import src.controller.PatientManager;
import src.helper.Helper;
import src.model.Patient;
import src.model.PrescribeMedication;
import src.model.TimeSlot;

/**
 * DoctorView class represents the view for doctor-specific functionalities in the hospital management system.
 * It allows the doctor to manage patient medical records, schedule appointments, update patient information,
 * and handle appointment requests, among other tasks.
 * @author Seann
 * @version 1.0
 * @since 2024-11-20
 */
public class DoctorView extends MainView {

    /**
     * Constructs a DoctorView object, initializing the view with the main menu.
     */
    public DoctorView() {
        super();
    }

    /**
     * Displays the main menu for the doctor with options to view and update patient records, manage appointments,
     * change password, and log out.
     */
    @Override
    public void printMenu() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Patient Medical Records");
        System.out.println("(2) Update Patient Medical Records");
        System.out.println("(3) View Personal Schedule");
        System.out.println("(4) Set Availability for Appointments ");
        System.out.println("(5) Accept or Decline Appointment Requests");
        System.out.println("(6) View Upcoming Appointments ");
        System.out.println("(7) Record Appointment Outcome");
        System.out.println("(8) Change Password");
        System.out.println("(9) Logout ");
    }

    /**
     * Handles the user input and allows the doctor to perform actions based on the selected menu option.
     * @param hospitalID The unique identifier for the hospital.
     */
    @Override
    public void viewApp(String hospitalID) {
        int opt;
        do {
            printMenu();
            opt = Helper.readInt(1, 9);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    System.out.println("Enter patient ID: ");
                    String patientID = Helper.readString();
                    Patient patient = PatientManager.searchPatientByID(patientID);
                    if (patient == null) {
                        System.out.println("Patient does not exist!");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    PatientManager.viewPatientRecords(patient);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Update Patient Medical Records");
                    System.out.println("Enter appointment ID (AXXXX): ");
                    String appointmentID = Helper.readString();

                    if (AppointmentManager.searchAppointmentByID(appointmentID) == null) {
                        System.out.println("Appointment does not exist!");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Enter patient's diagnosis: ");
                    String diagnosis = Helper.readString();
                    System.out.println("Enter patient's treatment: ");
                    String treatment = Helper.readString();
                    System.out.println("Enter list of medications to prescribe (type 'done' to finish): ");
                    List<PrescribeMedication> prescriptions = new ArrayList<>();
                    while (true) {
                        System.out.println("Enter medication name (or 'done' to finish): ");
                        String medicationName = Helper.readString();
                        if (medicationName.equalsIgnoreCase("done")) {
                            break;
                        }
                        System.out.println("Enter prescription amount (or type 'done' to cancel): ");
                        String amountInput = Helper.readString();
                        if (amountInput.equalsIgnoreCase("done")) {
                            System.out.println("Prescription for this medication canceled.");
                            continue;
                        }
                        try {
                            int amount = Integer.parseInt(amountInput);
                            prescriptions.add(new PrescribeMedication(medicationName, amount));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input, Enter a valid integer!");
                        }
                    }
                    AppointmentManager.recordAppointmentOutcome(appointmentID, hospitalID, treatment, diagnosis, prescriptions);
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Personal Schedule");
                    PatientView patientView = new PatientView();
                    patientView.viewScheduledAppointments(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Set Availability for Appointments");
                    System.out.println("On what date and time would you like to block availability? (Format: 'yyyy-MM-dd HH:MM' )");
                    System.out.println("Type 'back' to return to main menu");
                    String newDateInput = Helper.setDate(false);            
                    if (newDateInput.equalsIgnoreCase("back")) {
                        return;
                    }       
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime newDateTime = Helper.getDate(newDateInput, format);
                    TimeSlot newTimeSlot = new TimeSlot(newDateTime);       
                    if (AppointmentManager.setAvailability(hospitalID, newTimeSlot)) {
                        System.out.println("Availability set successfully!");
                    } else {
                        System.out.println("Availability set failed!");
                    }   
                    Helper.pressAnyKeyToContinue();
                    break;
                case 5:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Accept or Decline Appointment Requests");

                    if (!AppointmentManager.viewPendingAppointmentRequeest(hospitalID)) {
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Enter appointment ID to update request: ");
                    String appointmentID2 = Helper.readString();
                    if (AppointmentManager.searchAppointmentByID(appointmentID2) == null) {
                        System.out.println("Appointment does not exist!");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Accept or Decline? (1: Accept, 2: Decline)");
                    int decision = Helper.readInt(1, 2);
                    if (AppointmentManager.updateAppointmentRequest(appointmentID2, hospitalID, decision)) {
                        System.out.println("Appointment request updated successfully!");
                    } else {
                        System.out.println("Appointment request update failed!");
                    }   
                    Helper.pressAnyKeyToContinue();
                    break;
                case 6:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Upcoming Appointments");
                    AppointmentManager.viewScheduledAppointments(hospitalID, 1);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 7:
                    System.out.println("Enter appointment ID to record outcome (AXXXX): ");
                    String outcomeID = Helper.readString();
                    System.out.println("Enter type of service: ");
                    String outcomeService = Helper.readString();
                    System.out.println("Enter consultation notes: ");
                    String outcomeNotes = Helper.readString();
                    System.out.println("Enter list of medications to prescribe (type 'done' to finish): ");
                    List<PrescribeMedication> medications = new ArrayList<>();

                    while (true) {
                        System.out.println("Enter medication name (or 'done' to finish): ");
                        String medicationName = Helper.readString();
                    
                        if (medicationName.equalsIgnoreCase("done")) {
                            break; 
                        }
                    
                        System.out.println("Enter prescription amount (or type 'done' to cancel): ");
                        String amountInput = Helper.readString();
                    
                        if (amountInput.equalsIgnoreCase("done")) {
                            System.out.println("Prescription for this medication canceled.");
                            continue; 
                        }
                    
                        try {
                            int amount = Integer.parseInt(amountInput);
                            medications.add(new PrescribeMedication(medicationName, amount));
                        } catch (NumberFormatException e) {
                            System.out.println("Invalid Input, Enter a valid integer!");
                        }
                    }
                    AppointmentManager.recordAppointmentOutcome(outcomeID, hospitalID, outcomeService, outcomeNotes, medications);
                    break;
                case 8:                
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 9:
                    break;
            }
        } while (opt != 9);
    }
}