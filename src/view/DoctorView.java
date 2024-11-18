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


public class DoctorView extends MainView{
    public DoctorView () {
        super();
    }

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

    @Override
    public void viewApp(String hospitalID) {
        int opt;
        do {
            printMenu();
            opt = Helper.readInt(1, 9);
            switch (opt) {
                case 1:
                    //View patient medical record
                    Helper.clearScreen();
                    System.out.println("Enter patient ID: ");
                    String patientID = Helper.readString();
                    Patient patient = PatientManager.searchPatientByID(patientID);
                    if(patient==null){
                        System.out.println("Patient does not exist!");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    PatientManager.viewPatientRecords(patient);
                    Helper.pressAnyKeyToContinue()
                    ;
                    break;
                case 2:
                    //Update patient medical record
                    //recordAppointmentOutcome(String appointmentID, String doctorID, String typeOfService, String consultationNotes, List<PrescribeMedication> medications)
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Update Patient Medical Records");
                    System.out.println("Enter appointment ID (AXXXX): ");
                    String appointmentID = Helper.readString();

                    if(AppointmentManager.searchAppointmentByID(appointmentID) == null){
                        System.out.println("Appointment does not exist!");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Enter type of service: ");
                    String typeOfService = Helper.readString();
                    System.out.println("Enter consultation notes: ");
                    String consultationNotes = Helper.readString();
                    //prescribe medication
                    AppointmentManager.recordAppointmentOutcome(appointmentID, hospitalID, typeOfService, consultationNotes, null);
                    ;
                    break;
                case 3:
                    //View personal schedule
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Personal Schedule");
                    PatientView patientView = new PatientView();
                    patientView.viewScheduledAppointments(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    ;
                    break;
                case 4:
                    //Set availability for appointments
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Set Availability for Appointments");
                    System.out.println("\"On what date and time would you like to block availability? (Format: 'yyyy-MM-dd HH:MM' )\")");
                    String newDateInput = Helper.setDate(false);
                    DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime newDateTime = Helper.getDate(newDateInput, format);
                    TimeSlot newTimeSlot = new TimeSlot(newDateTime);       
                    if(AppointmentManager.setAvailability(hospitalID, newTimeSlot)){
                        System.out.println("Availability set successfully!");
                    } else {
                        System.out.println("Availability set failed!");
                    }   
                    Helper.pressAnyKeyToContinue();
                    break;
                case 5:
                    //Accept or decline appointment requests
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Accept or Decline Appointment Requests");

                    //print pending appointment requests
                    if(!AppointmentManager.viewPendingAppointmentRequeest(hospitalID)){
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Enter appointment ID to update request: ");
                    String appointmentID2 = Helper.readString();
                    if(AppointmentManager.searchAppointmentByID(appointmentID2)==null){
                        System.out.println("Appointment does not exist!");
                        Helper.pressAnyKeyToContinue();
                        break;
                    }
                    System.out.println("Accept or Decline? (1: Accept, 2: Decline)");
                    int decision = Helper.readInt(1, 2);
                    if(AppointmentManager.updateAppointmentRequest(appointmentID2, hospitalID, decision)){
                        System.out.println("Appointment request updated successfully!");
                    } else {
                        System.out.println("Appointment request update failed!");
                    }   
                    Helper.pressAnyKeyToContinue();
                    break;
                case 6:
                    //View upcoming appointments
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Upcoming Appointments");
                    AppointmentManager.viewScheduledAppointments(hospitalID, 1);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 7:
                    //Record appointment outcome
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
                            break; // Exit the loop when the user types "done"
                        }
                    
                        System.out.println("Enter prescription amount (or type 'done' to cancel): ");
                        String amountInput = Helper.readString();
                    
                        if (amountInput.equalsIgnoreCase("done")) {
                            System.out.println("Prescription for this medication canceled.");
                            continue; // Skip to the next medication
                        }
                    
                        // Validate the amount input
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
                    //Change password
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 9:
                    //Logout
                    break;
            }
        } while (opt != 9);
    }
}
