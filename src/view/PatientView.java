package src.view;
import src.model.AppointmentSlot;
import src.model.Patient;

import java.time.LocalDate;
import java.util.List;

import src.controller.AppointmentManager;
import src.controller.LoginManager;
import src.controller.PatientManager;
import src.helper.Helper;

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
                    PatientManager.updatePatientDetails(hospitalID, choice, newvalue);
                    ;
                    break;
                case 3:
                    //View available appointment slots
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Available Appointment Slots");
                    AppointmentManager appointmentManager = new AppointmentManager();
                    LocalDate date = LocalDate.now();
                    List<AppointmentSlot> availableSlots = appointmentManager.getAvailableSlotsByDoctor(date, hospitalID);
                    for (AppointmentSlot slot : availableSlots) {
                        System.out.println(slot);
                    }
                    ;
                    break;
                case 4:
                    //Schedule an appointment
                    //show available appointment slots
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Schedule an Appointment");
                    appointmentManager = new AppointmentManager();
                    date = LocalDate.now();
                    availableSlots = appointmentManager.getAvailableSlotsByDoctor(date, hospitalID);
                    for (AppointmentSlot slot : availableSlots) {
                        System.out.println(slot);
                    }
                    //choose a slot

                    //AppointmentManager.scheduleAppointment(hospitalID, newvalue, null);
                    break;
                case 5:
                    //Reschedule an appointment
                    break;
                case 6:
                    //Cancel an appointment
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Cancel Appointment");
                    System.out.println("Enter the appointment ID to cancel: ");
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
                    System.out.println("What would you like to view ?");
                    System.out.println("(1) Upcoming Appointments");
                    System.out.println("(2) All Appointments");
                    System.out.println("(3) Back");
                    int choice = Helper.readInt(1, 3);
                    do {
                        if (choice == 1) {
                            AppointmentManager.viewScheduledAppointments(hospitalID, 1);
                            break;
                        } else if (choice == 2) {
                            AppointmentManager.viewScheduledAppointments(hospitalID, 2);
                            break;
                        } else if (choice == 3) {
                            break;
                        } else {
                            System.out.println("Invalid Choice");
                        }
                    } while (choice != 3);
}
}



