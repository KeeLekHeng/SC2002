//view appointment outcome record is bugged

package src.view;
import src.controller.LoginManager;
import src.controller.PrescriptionManager;
import src.helper.Helper;

public class PharmacistView extends MainView {
    PrescriptionManager prescriptionManager = new PrescriptionManager();
    public PharmacistView () {
        super();
    }

    @Override
    public void printMenu() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu");
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
                    Helper.clearScreen();
                    //View appointment outcome record
                    if(!PrescriptionManager.viewRecentAppointmentOutcomeRecord()){
                        System.out.println("There is no recent pending appointment outcome records");
                    }
                    Helper.pressAnyKeyToContinue();
                    ;
                    break;
                case 2:
                    Helper.clearScreen();
                    //Update prescription status
                    updatePrescriptionStatus();
                    break;
                case 3:
                    Helper.clearScreen();
                    //View medication inventory
                    PrescriptionManager.viewMedicationInventory();
                    Helper.pressAnyKeyToContinue()
                    ;
                    break;
                case 4:
                    //Submit replenishment request
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Submit Replenishment Request");
                    System.out.println("Enter the medication name: ");
                    String medicationName = Helper.readString();
                    System.out.println("Enter the quantity: ");
                    int quantity = Helper.readInt(1, 100);
                    PrescriptionManager.submitReplenishRequest(hospitalID, medicationName, quantity);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 5:
                    //Change password
                    Helper.clearScreen();
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 6:
                    Helper.clearScreen();
                    //Logout
                    break;
            }
        } while (opt != 6);
    }



///////////Updte Prescription Status/////////////////////
public void updatePrescriptionStatus() {
    Helper.clearScreen();
    printBreadCrumbs("Main Menu > Update Prescription Status");
    System.out.print("Enter the prescription ID (type 'back to return to main menu'): ");
                    String prescriptionID = Helper.readString();
                    if(prescriptionID.equals("back")){
                        return;
                    }
                    if(PrescriptionManager.searchPrescriptionById(prescriptionID) == null){
                        System.out.println("Prescription not found.");
                        Helper.pressAnyKeyToContinue();
                        return;
                    }
                    System.out.println("Select an action:");
                    System.out.println("1. Dispense");
                    System.out.println("2. Back");
                    int action = Helper.readInt();
                    boolean success;
                    if (action == 1) {
                        // Attempt to dispense
                        success = prescriptionManager.updatePrescriptionStatus(prescriptionID, 1);
                        if (success) {
                            System.out.println("Prescription dispensed successfully.");
                        }else{
                            System.out.println("Error: Prescription could not be updated.");
                            Helper.pressAnyKeyToContinue();
                        }
                    } else if (action == 2) {
                        // Skip the prescription
                        prescriptionManager.updatePrescriptionStatus(prescriptionID, 2);
                    } else {
                        System.out.println("Invalid action.");
                    }
    }
}