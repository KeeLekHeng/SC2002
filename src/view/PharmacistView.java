package src.view;
import src.helper.Helper;

public class PharmacistView extends MainView {
    public PharmacistView () {
        super();
    }

    @Override
    public void printMenu() {
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Appointment Outcome Record ");
        System.out.println("(2) Update Prescription Status ");
        System.out.println("(3) View Medication Inventory");
        
        System.out.println("(4) Submit Replenishment Request ");
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
                    //View appointment outcome record
                    ;
                    break;
                case 2:
                    //Update prescription status
                    ;
                    break;
                case 3:
                    //View medication inventory
                    ;
                    break;
                case 4:
                    //Submit replenishment request
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
