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

public class PatientView extends MainView {
    public PatientView() {
        super();
    }

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
    public void viewApp(String hospitalID) {
        int opt;
        do {
            Helper.clearScreen();
            printBreadCrumbs("Main Menu");
            printMenu();
            opt = Helper.readInt(1, 10);
            switch (opt) {
                case 1:
                    // View medical record
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
                    ;
                    break;
                case 2:
                    // Update personal information
                    // public static boolean updatePatientDetails(String patientID, int
                    // attributeCode, String newvalue) {
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
                    ;
                    break;
                case 3:
                    // View available appointment slots
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
                    // not printing no doctor found
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
                    // Schedule an appointment
                    // show available appointment slots
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
                    // Cancel an appointment
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
                    // View scheduled appointments
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Scheduled Appointments");
                    viewScheduledAppointments(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 8:
                    // View past appointment outcome records
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
                    // Change password
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 10:
                    // Logout
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (opt != 10);
    }

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

    //////////// view Scheduled Appointment///////////////
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

    ////////////////////// reschedule appointment//////////////////////
    private void rescheduleAppointment(String hospitalID, String appointmentID, String newDateInput) {
        // Show scheduled appointments for the hospitalID
        if (AppointmentManager.viewScheduledAppointments(hospitalID, 1)) {
            
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate newDate = Helper.getDateOnly(newDateInput, format);
    
            // Get available slots for the doctor on the chosen date
            Appointment appointment = AppointmentManager.searchAppointmentByID(appointmentID);
            String doctorID = appointment.getDoctorID();
            List<AppointmentSlot> availableSlots = AppointmentManager.getAvailableSlotsByDoctor(newDate, doctorID);
    
            if (availableSlots.isEmpty()) {
                System.out.println("No available slots for the selected doctor on " + newDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                Helper.pressAnyKeyToContinue();
                return;
            }
    
            // Display available slots
            System.out.println("Available Slots:");
            for (int i = 0; i < availableSlots.size(); i++) {
                System.out.println((i + 1) + ". " + availableSlots.get(i).getTimeSlot().getFormattedTime());
            }
    
            // Prompt for the slot choice
            System.out.println("Enter the number corresponding to the Time Slot you wish to schedule:");
            int slotChoice = Helper.readInt() - 1;
    
            if (slotChoice < 0 || slotChoice >= availableSlots.size()) {
                System.out.println("Invalid choice. Returning to the main menu...");
                Helper.pressAnyKeyToContinue();
                return;
            }
    
            TimeSlot selectedSlot = availableSlots.get(slotChoice).getTimeSlot();
    
            // Reschedule the appointment
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
    

    ////////////////////// schedule appointment//////////////////////
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
