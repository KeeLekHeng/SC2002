package src.view;

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
    public void viewApp() {
        int opt;
        do {
            printMenu();
            opt = Helper.readInt(1, 10);
            switch (opt) {
                case 1:
                    //View medical record
                    ;
                    break;
                case 2:
                    //Update personal information
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
                    break;
                case 10:
                    //Logout
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (opt != 10);
    }
}
