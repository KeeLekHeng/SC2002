package src.view;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import src.controller.AppointmentManager;
import src.controller.LoginManager;
import src.controller.PatientManager;
import src.helper.Helper;
import src.model.AppointmentSlot;
import src.model.Patient;
import src.model.TimeSlot;
import src.controller.StaffManager;

public class PatientView extends MainView {
    public PatientView () {
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
                    //View medical record
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Medical Record");
                    Patient patient = PatientManager.searchPatientByID(hospitalID);
                    PatientManager.viewPatientRecords(patient);
                    ;
                    break;
                case 2:
                    //Update personal information
                    //    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue) {
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Update Personal Information");
                    System.out.println("What would you like to update ?");
                    System.out.println("(1) Phone Number");
                    System.out.println("(2) Email");
                    int choice = Helper.readInt(1, 2);
                    String newvalue = chooseUpdateAttribute(choice);
                    if(PatientManager.updatePatientDetails(hospitalID, choice, newvalue)){
                        System.out.println("Update successful");
                    } else {
                        System.out.println("Invalid input");
                    }
                    Helper.pressAnyKeyToContinue();
                    ;
                    break;
                case 3:
                    //View available appointment slots
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
                    String doctorID = Helper.readString();
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate newDate = Helper.getDateOnly(newDateInput, format);
                    List<AppointmentSlot> availableSlots = AppointmentManager.getAvailableSlotsByDoctor(newDate, doctorID);
                    for (AppointmentSlot slot : availableSlots) {
                        System.out.println(slot);
                    }
                    Helper.pressAnyKeyToContinue();
                    break;
                case 4:
                    //Schedule an appointment
                    //show available appointment slots
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Schedule an Appointment");
                    System.out.println("Enter the DoctorID of the doctor you wish to meet (DXXX):");
                    doctorID = Helper.readString();
                    if(StaffManager.searchStaffById(doctorID) == null){
                        System.out.println("Doctor not found. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("What date would you like to reschedule to?");
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
                    //Reschedule an appointment(maybe can display available slots)
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Rechedule an Appointment");
                    //true false
                    if(AppointmentManager.viewScheduledAppointments(hospitalID, 1)){
                        
                    System.out.println("Enter the Appointment ID to reschedule: AppointmentID format (AXXXXX)");
                    String appointmentID = Helper.readString();
                    if(AppointmentManager.searchAppointmentByID(appointmentID) == null){
                        System.out.println("Appointment not found. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("\"What date and time would you like to schedule an appointment? (Format: 'yyyy-MM-dd HH:MM' )\")");
                    newDateInput = Helper.setDate(false);
                    
                    if (newDateInput.isEmpty()) {
                        System.out.println("Failed to parse the date. Returning to the main menu...");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }

                    if(!rescheduleAppointment(hospitalID, appointmentID, newDateInput)){
                        System.out.println("Unable to reschedule appointment.");
                    } else {
                    }

                    Helper.pressAnyKeyToContinue();
                    break;
                case 6:
                    //Cancel an appointment
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Cancel Appointment");
                    System.out.println("Enter the appointment ID to cancel (AXXXXX): ");
                    String appointmentID = Helper.readString();
                    AppointmentManager.cancelAppointment(appointmentID, hospitalID);
                    break;
                case 7:
                    //View scheduled appointments
                    printBreadCrumbs("Main Menu > View Scheduled Appointments");
                    viewScheduledAppointments(hospitalID);
                    break;
                case 8:
                    //View past appointment outcome records
                    AppointmentManager.viewPastAppointmentOutcomeRecords(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 9:
                    //Change password
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 10:
                    //Logout
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (opt != 10);
    }
    public String chooseUpdateAttribute(int attributeCode) {
        switch(attributeCode){
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
    


    ////////////view Scheduled Appointment///////////////
public void viewScheduledAppointments(String hospitalID){
    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Scheduled Appointments");
                    System.out.println("What would you like to view ?");
                    System.out.println("(1) Upcoming Appointments");
                    System.out.println("(2) All Appointments");
                    System.out.println("(3) Back");
                    int choice = Helper.readInt(1, 3);
                    do {
                        if (choice == 1) {
                            AppointmentManager.viewScheduledAppointments(hospitalID, 1);
                            Helper.pressAnyKeyToContinue();
                            break;
                        } else if (choice == 2) {
                            AppointmentManager.viewScheduledAppointments(hospitalID, 2);
                            Helper.pressAnyKeyToContinue();
                            break;
                        } else if (choice == 3) {
                            break;
                        } else {
                            System.out.println("Invalid Choice");
                        }
                    } while (choice != 3);
}

public boolean rescheduleAppointment(String appointmentID, String patientID, String newDateInput){
    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime newDateTime = Helper.getDate(newDateInput, format);
    TimeSlot newTimeSlot = new TimeSlot(newDateTime);          
    return AppointmentManager.rescheduleAppointment(appointmentID, patientID, newTimeSlot);
}

public boolean scheduleAppointment(String doctorID, String patientID, String newDateInput){

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
    
    if(!AppointmentManager.scheduleAppointment(patientID, doctorID, selectedSlot)){
        System.out.println("Failed to schedule the appointment. Please try again.");
        return false;
    }
    return true;
}

}



