package src.view;
import src.controller.InventoryManager;
import src.controller.LoginManager;
import src.controller.PrescriptionManager;
import src.helper.Helper;

/**
 * The PharmacistView class represents the view layer for the pharmacist in the hospital management system.
 * This class extends the MainView class and provides functionality for pharmacists to interact with the system.
 * It allows the pharmacist to view and manage appointment records, prescriptions, and inventory.
 * @author Seann
 * @version 1.0
 * @since 2024-11-20
 */
public class PharmacistView extends MainView {

    PrescriptionManager prescriptionManager = new PrescriptionManager();

    /**
     * Constructor for the PharmacistView class. Initializes the view for the pharmacist.
     */
    public PharmacistView () {
        super();
    }

    /**
     * Prints the main menu for the pharmacist to choose actions.
     * The menu includes options like viewing appointment outcomes, updating prescriptions,
     * viewing medication inventory, submitting replenishment requests, changing the password, and logging out.
     */
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

    /**
     * Provides functionality for the pharmacist to view and manage various options in the system,
     * such as viewing appointment outcomes, updating prescriptions, and handling inventory and requests.
     * @param hospitalID The hospital ID used for interacting with the system.
     */
    @Override
    public void viewApp(String hospitalID) {
        int opt;
        do {
            printMenu();
            opt = Helper.readInt(1, 6);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    PrescriptionManager prescriptionManager = new PrescriptionManager(); 
                    if (!PrescriptionManager.viewRecentAppointmentOutcomeRecord()) {
                        System.out.println("There is no recent pending appointment outcome records");
                    }
                    Helper.pressAnyKeyToContinue();
                    break;
                case 2:
                    Helper.clearScreen();
                    updatePrescriptionStatus();
                    Helper.pressAnyKeyToContinue();
                    break;
                case 3:
                    Helper.clearScreen();
                    PrescriptionManager.viewMedicationInventory();
                    Helper.pressAnyKeyToContinue();
                    break;
                case 4:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > Submit Replenishment Request");
                    InventoryManager.printMedicationStockLevels();
                    System.out.println("Enter the medication name to replenish: ");
                    String medicationName = Helper.readString();
                    System.out.println("Enter the quantity: ");
                    int quantity = Helper.readInt(1, 100);
                    PrescriptionManager.submitReplenishRequest(hospitalID, medicationName, quantity);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 5:
                    Helper.clearScreen();
                    LoginManager.createNewPassword(hospitalID);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 6:
                    Helper.clearScreen();
                    break;
            }
        } while (opt != 6);
    }

    /**
     * Updates the status of a prescription. The pharmacist can dispense or skip updating a prescription.
     * @param prescriptionID The ID of the prescription to be updated.
     */
    public void updatePrescriptionStatus() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > Update Prescription Status");
        
        if(!PrescriptionManager.printAllPrescriptions()){
            return;
        }
        String prescriptionID = Helper.readPrescriptionID();
        if (prescriptionID.equals("back")) {
            return;
        }
        if (PrescriptionManager.searchPrescriptionById(prescriptionID) == null) {
            System.out.println("Prescription not found.");
            return;
        }
        System.out.println("Select an action:");
        System.out.println("1. Dispense");
        System.out.println("2. Back");
        int action = Helper.readInt();
        boolean success;
        if (action == 1) {
            success = prescriptionManager.updatePrescriptionStatus(prescriptionID, 1);
            if (success) {
                System.out.println("Prescription dispensed successfully.");
            } else {
                System.out.println("Error: Prescription could not be updated.");
            }
        } else if (action == 2) {
            prescriptionManager.updatePrescriptionStatus(prescriptionID, 2);
        } else {
            System.out.println("Invalid action.");
        }
    }
}