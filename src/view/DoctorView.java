package src.view;
import src.model.Patient;
import src.controller.AppointmentManager;
import src.controller.LoginManager;
import src.controller.PatientManager;
import src.helper.Helper;


public class DoctorView extends MainView{
    public DoctorView () {
        super();
    }

    @Override
    public void printMenu() {
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
                    Patient patient = PatientManager.searchPatientByID(hospitalID);
                    PatientManager.viewPatientRecords(patient);
                    ;
                    break;
                case 2:
                    //Update patient medical record
                    //recordAppointmentOutcome(String appointmentID, String doctorID, String typeOfService, String consultationNotes, List<PrescribeMedication> medications)
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Update Patient Medical Records");
                    System.out.println("Enter appointment ID: ");
                    String appointmentID = Helper.readString();
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
                    ;
                    break;
                case 4:
                    //Set availability for appointments
                    break;
                case 5:
                    //Accept or decline appointment requests
                    break;
                case 6:
                    //View upcoming appointments
                    break;
                case 7:
                    //Record appointment outcome
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
