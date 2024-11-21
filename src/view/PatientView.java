package src.view;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import src.controller.AppointmentManager;
import src.controller.LoginManager;
import src.controller.PatientManager;
import src.controller.StaffManager;
import src.helper.Helper;
import src.model.Appointment;
import src.model.AppointmentSlot;
import src.model.Patient;
import src.model.TimeSlot;

/**
 * The PatientView class is responsible for providing the user interface for patient-related actions
 * such as viewing medical records, scheduling and rescheduling appointments, updating personal information, and more.
 * It extends the MainView class and implements its abstract methods to interact with the user.
 * @author Seann
 * @version 1.0
 * @since 2024-11-20
 */
public class PatientView extends MainView {
    
    /**
     * Constructor for PatientView.
     */
    public PatientView() {
        super();
    }

    /**
     * Prints the main menu options for the patient view.
     */
    @Override
    public void printMenu() {
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Medical Record");
        System.out.println("(2) Update Personal Information");
        System.out.println("(3) View Available Appointment Slots");
        System.out.println("(4) Schedule an Appointment ");
        System.out.println("(5) Reschedule an Appointment ");
        System.out.println("(6) Cancel an Appointment");
        System.out.println("(7) View Scheduled Appointments");
        System.out.println("(8) View Past Appointment Outcome Records ");
        System.out.println("(9) Change Password");
        System.out.println("(10) Logout ");
    }



    @Override
    /**
     * Handles the patient's main menu operations, such as viewing medical records,
     * updating personal information, scheduling appointments, and managing past 
     * appointment records.
     * @param hospitalID The unique ID of the hospital for which the operations are carried out.
     * @since 2024-11-20
     */
    public void viewApp(String hospitalID) {
        int opt;
        do {
            Helper.clearScreen();
            printBreadCrumbs("Main Menu");
            printMenu();
            opt = Helper.readInt(1, 10);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Medical Record");
                    Patient patient = PatientManager.searchPatientByID(hospitalID);
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
                    printBreadCrumbs("Main Menu > Update Personal Information");
                    System.out.println("What would you like to update ?");
                    System.out.println("(1) Phone Number");
                    System.out.println("(2) Email");
                    int choice = Helper.readInt(1, 2);
                    String newvalue = chooseUpdateAttribute(choice);
                    if (PatientManager.updatePatientDetails(hospitalID, choice, newvalue)) {
                        System.out.println("Update successful");
                    } else {
                        System.out.println("Invalid input");
                    }
                    Helper.pressAnyKeyToContinue();
                    break;
                case 3:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Available Appointment Slots");
                    System.out.println("Enter the date you wish to view available slots for (Format: 'yyyy-MM-dd' )");
                    String newDateInput = Helper.setDateOnly();
                    if (newDateInput.isEmpty()) {
                        System.out.println("Failed to parse the date. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Enter doctor ID (DXXX): ");
                    String doctorID = Helper.readStaffID();
                    if (StaffManager.searchStaffById(doctorID).isEmpty()) {
                        System.out.println("Doctor not found. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    System.out.println("Doctor's name:");
                    System.out.println(StaffManager.searchStaffById(doctorID).get(0).getName());
                    LocalDate newDate = Helper.getDateOnly(newDateInput, format);
                    AppointmentManager.viewAvailableAppointmentSlots(hospitalID, doctorID, newDate);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Schedule an Appointment");
                    System.out.println("Enter the DoctorID of the doctor you wish to meet (DXXX):");
                    System.out.println("Type 'back' to return to main menu");     
                    doctorID = Helper.readStaffID();
                    if(doctorID.equalsIgnoreCase("back")){
                        return;
                    }  
                    if (StaffManager.searchStaffById(doctorID).isEmpty()) {
                        System.out.println("Doctor not found. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("What date would you like to schedule to?");
                    newDateInput = Helper.setDateOnly();
                    if (newDateInput.isEmpty()) {
                        System.out.println("Failed to parse the date. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    
                    scheduleAppointment(hospitalID, doctorID, newDateInput);
                    Helper.pressAnyKeyToContinue();
    
                    break;
    
                case 5:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Reschedule an Appointment");
    
                    if(!AppointmentManager.viewScheduledAppointments(hospitalID, 1)){
                        return;
                    }
    
                    System.out.println("Enter the Appointment ID to reschedule:");
                    String appointmentID = Helper.readAppointmentID(); 
                    if (AppointmentManager.searchAppointmentByID(appointmentID) == null) {
                        System.out.println("Appointment not found. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        return;
                    }
    
                    System.out.println("What date would you like to schedule to?");
                    newDateInput = Helper.setDateOnly();
                    if (newDateInput.isEmpty()) {
                        System.out.println("Failed to parse the date. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    rescheduleAppointment(hospitalID, appointmentID, newDateInput);
                    Helper.pressAnyKeyToContinue();
                    break;
                    
                case 6:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Cancel Appointment");
                    if(!AppointmentManager.viewScheduledAppointments(hospitalID, 1)){
                        break;
                    }
                    System.out.println("Enter the appointment ID to cancel: ");
                    appointmentID = Helper.readAppointmentID();
                    
                    if (AppointmentManager.searchAppointmentByID(appointmentID) == null) {
                        System.out.println("Appointment not found. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    Helper.clearScreen();
                    AppointmentManager.cancelAppointment(appointmentID, hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 7:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Scheduled Appointments");
                    viewScheduledAppointments(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 8:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Past Appointment Outcome Records");
                    System.out.println("What would you like to view ?");
                    System.out.println("(1) View Specific Past Appointment Outcome Record");
                    System.out.println("(2) View All Past Appointment Outcome Records");
                    System.out.println("(3) Back");
                    int choices = Helper.readInt(1, 3);
                    if (choices == 1) {
                        System.out.println("Enter the appointment ID to view the outcome record: ");
                        String appID = Helper.readAppointmentID();
                        AppointmentManager.fetchAppointmentOutcomeRecords(choices, hospitalID, appID);
                    } else if (choices == 2) {
                        AppointmentManager.fetchAppointmentOutcomeRecords(choices, hospitalID, null);
                    }
                    Helper.pressAnyKeyToContinue();
                    break;
                case 9:
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 10:
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (opt != 10);
    }
    

    /**
     * Prompts the user to enter a new phone number or email based on the provided attribute code.
     * @param attributeCode The code corresponding to the attribute to be updated (1 for phone number, 2 for email).
     * @return The new value for the chosen attribute.
     */
    public String chooseUpdateAttribute(int attributeCode) {
        switch (attributeCode) {
            case 1:
                System.out.println("Enter new phone number: ");
                return Helper.readString();
            case 2:
                System.out.println("Enter new email: ");
                return Helper.readString();
            default:
                return "Invalid Choice";
        }
    }

    /**
     * Displays the scheduled appointments for a specific hospital and allows the user to choose between upcoming or all appointments.
     * @param hospitalID The unique ID of the hospital whose appointments are to be viewed.
     */
    public void viewScheduledAppointments(String hospitalID) {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View Scheduled Appointments");
        System.out.println("What would you like to view ?");
        System.out.println("(1) Upcoming Appointments");
        System.out.println("(2) All Appointments");
        System.out.println("(3) Back");
        int choice = Helper.readInt(1, 3);
        do {
            if (choice == 1) {
                Helper.clearScreen();
                AppointmentManager.viewScheduledAppointments(hospitalID, 1);
                break;
            } else if (choice == 2) {
                Helper.clearScreen();
                AppointmentManager.viewScheduledAppointments(hospitalID, 2);
                break;
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid Choice");
            }
        } while (choice != 3);
    }

    /**
     * Reschedules an existing appointment by providing available time slots for the specified doctor on a new date.
     * @param hospitalID The unique ID of the hospital where the appointment is scheduled.
     * @param appointmentID The unique ID of the appointment to be rescheduled.
     * @param newDateInput The new date for the appointment in 'yyyy-MM-dd' format.
     */
    private void rescheduleAppointment(String hospitalID, String appointmentID, String newDateInput) {
        if (AppointmentManager.viewScheduledAppointments(hospitalID, 1)) {
            
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = Helper.getDateOnly(newDateInput, format);

            Appointment appointment = AppointmentManager.searchAppointmentByID(appointmentID);
            String doctorID = appointment.getDoctorID();
            List<AppointmentSlot> availableSlots = AppointmentManager.getAvailableSlotsByDoctor(newDate, doctorID);

            if (availableSlots.isEmpty()) {
                System.out.println("No available slots for the selected doctor on " + newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                Helper.pressAnyKeyToContinue();
                return;
            }

            System.out.println("Available Slots:");
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.println((i + 1) + ". " + availableSlots.get(i).getTimeSlot().getFormattedTime());
            }

            System.out.println("Enter the number corresponding to the Time Slot you wish to schedule:");
            int slotChoice = Helper.readInt() - 1;

            if (slotChoice < 0 || slotChoice >= availableSlots.size()) {
                System.out.println("Invalid choice. Returning to the main menu...");
                Helper.pressAnyKeyToContinue();
                return;
            }

            TimeSlot selectedSlot = availableSlots.get(slotChoice).getTimeSlot();

            if (!AppointmentManager.rescheduleAppointment(appointmentID, hospitalID, selectedSlot)) {
                System.out.println("Unable to reschedule appointment.");
            } else {
                System.out.println("Appointment successfully rescheduled to " +
                    selectedSlot.getFormattedDate() + " at " + selectedSlot.getFormattedTime());
            }

        } else {
            System.out.println("No scheduled appointments found for rescheduling.");
            Helper.pressAnyKeyToContinue();
        }
    }

    /**
     * Schedules a new appointment for a patient with a specified doctor on a selected date.
     * Displays available time slots for the doctor and allows the user to choose one.
     * @param patientID The unique ID of the patient scheduling the appointment.
     * @param doctorID The unique ID of the doctor with whom the appointment is to be scheduled.
     * @param newDateInput The desired date for the appointment in 'yyyy-MM-dd' format.
     * @return true if the appointment was successfully scheduled, false otherwise.
     */
    public boolean scheduleAppointment(String patientID, String doctorID,  String newDateInput) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate newDate = Helper.getDateOnly(newDateInput, format);

        List<AppointmentSlot> availableSlots = AppointmentManager.getAvailableSlotsByDoctor(newDate, doctorID);
        if (availableSlots.isEmpty()) {
            System.out.println("No available slots for the selected doctor on " + newDate.format(format));
            return false;
        }

        System.out.println("Available Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println((i + 1) + ". " + availableSlots.get(i).getTimeSlot().getFormattedTime());
        }

        System.out.println("Enter the number corresponding to the Time Slot you wish to schedule your appointment:");
        int slotChoice = Helper.readInt() - 1;

        if (slotChoice < 0 || slotChoice >= availableSlots.size()) {
            System.out.println("Invalid choice. Returning to the main menu...");
            return false;
        }

        TimeSlot selectedSlot = availableSlots.get(slotChoice).getTimeSlot();

        if (!AppointmentManager.scheduleAppointment(patientID, doctorID, selectedSlot)) {
            System.out.println("Failed to schedule the appointment. Please try again.");
            return false;
        }
        return true;
    }
}