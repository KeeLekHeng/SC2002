package src.view;
import src.model.Patient;
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
            printMenu();
            opt = Helper.readInt(1, 10);
            switch (opt) {
                case 1:
                    //View medical record
                    Patient patient = PatientManager.searchPatientByID(hospitalID);
                    PatientManager.printPatientDetails(patient);
                    //dont have valid user yet so cant test
                    ;
                    break;
                case 2:
                    //Update personal information
                    //    public static boolean updatePatientDetails(String patientID, int attributeCode, String newvalue) {
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
                    ;
                    break;
                case 4:
                    //Schedule an appointment
                    break;
                case 5:
                    //Reschedule an appointment
                    break;
                case 6:
                    //Cancel an appointment
                    break;
                case 7:
                    //View scheduled appointments
                    break;
                case 8:
                    //View past appointment outcome records
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
}

