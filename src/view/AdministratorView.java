package src.view;
import src.helper.*;

public class AdministratorView extends MainView {
    public AdministratorView () {
        super();
    }

    @Override
    public void printMenu() {
        System.out.println("What would you like to do ?");
        System.out.println("(1) View and Manage Hospital Staff");
        System.out.println("(2) View Appointments details");
        System.out.println("(3) View and Manage Medication Inventory ");
        //1 is update stock, 2 is update low stock limit
        System.out.println("(4) Approve Replenishment Requests ");
        System.out.println("(5) Change Password");
        System.out.println("(6) Logout ");
    }

    @Override
    public void viewApp(String hospitalID) {
        int opt;
        do {
            printMenu();
            opt = Helper.readInt(1, 6);
            switch (opt) {
                case 1:
                    //View and manage hospital staff
                    ;
                    break;
                case 2:
                    //View appointment details
                    ;
                    break;
                case 3:
                    //View and manage medication inventory
                    ;
                    break;
                case 4:
                    //Approve replenishment requests
                    break;
                case 5:
                    //Change password
                    break;
                case 6:
                    //Logout
                    break;
            }
        } while (opt != 6);
    }
}
