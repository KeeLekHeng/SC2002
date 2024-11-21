package src.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import src.controller.AppointmentManager;
import src.controller.LoginManager;
import src.controller.PatientManager;
import src.helper.Helper;
import src.model.AppointmentSlot;
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
                    AppointmentManager.viewPatientsUnderCare(hospitalID);
                    System.out.println("Enter patient ID: ");
                    String patientID = Helper.readPatientID();
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
                    System.out.println("Enter appointment ID");
                    String appointmentID = Helper.readAppointmentID();

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
                    // Set unavailability for a doctor
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Set Unavailability for Appointments");
                    handleDoctorUnavailability(hospitalID);
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
                    System.out.println("Enter appointment ID to record outcome");
                    String outcomeID = Helper.readAppointmentID();

                    if(!AppointmentManager.validateAppointmentOwnership(outcomeID, hospitalID)){
                        Helper.pressAnyKeyToContinue();
                        break;
                    }

                    System.out.println("Enter type of service: ");
                    String outcomeService = Helper.readString();
                    System.out.println("Enter consultation notes: ");
                    String outcomeNotes = Helper.readString();
                    System.out.println("Enter list of medications to prescribe");
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
                    Helper.pressAnyKeyToContinue();
                    break;
                case 8:                
                    LoginManager.createNewPassword(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 9:
                    break;
            }
        } while (opt != 9);
    }

    private void handleDoctorUnavailability(String doctorID) {
        
        System.out.println("Enter the date for which you want to block availability (Format: 'yyyy-MM-dd'):");
        String newDateInput = Helper.setDateOnly();
        if (newDateInput.isEmpty()) {
            System.out.println("Invalid date entered. Returning to main menu...");
            return;
        }
    
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate selectedDate = Helper.getDateOnly(newDateInput, dateFormat);
    
        List<AppointmentSlot> availableSlots = AppointmentManager.getAvailableSlotsByDoctor(selectedDate, doctorID);
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots found for the selected date.");
            return;
        }

        System.out.println("Available Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println((i + 1) + ". " + availableSlots.get(i).getTimeSlot().getFormattedTime());
        }
    
        System.out.println("Enter the number corresponding to the Time Slot you want to block:");
        int slotChoice = Helper.readInt() - 1;
    
        if (slotChoice < 0 || slotChoice >= availableSlots.size()) {
            System.out.println("Invalid choice. Returning to main menu...");
            return;
        }
    
        TimeSlot selectedSlot = availableSlots.get(slotChoice).getTimeSlot();
        if (AppointmentManager.setAvailability(doctorID, selectedSlot)) {
            System.out.println("The selected slot has been marked as unavailable.");
        } else {
            System.out.println("Failed to block the selected slot. Please try again.");
        }
    }
}