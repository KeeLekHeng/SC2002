package src.view;
import src.controller.InventoryManager;
import src.controller.LoginManager;
import src.controller.PrescriptionManager;
import src.controller.StaffManager;
import src.model.enums.*;
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
                    viewAndManageStaff();
                    ;
                    break;
                case 2:
                    //View appointment details


                    ;
                    break;
                case 3:
                    //View and manage medication inventory
                    //can view or manage or back
                    viewAndManageMedicationInventory();
                    ;
                    break;
                case 4:
                    //Approve replenishment requests
                    //approveReplenishmentRequest();
                    //check with kee
                    PrescriptionManager.getPendingRequests(); //this is a loop
                    PrescriptionManager.printReplenishRequest(null); //this is just one
                    //PrescriptionManager.approveReplenishRequest();
                    break;
                case 5:
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 6:
                    //Logout
                    break;
            }
        } while (opt != 6);
    }
////////////////////////////// View and Manage Staff //////////////////////////////
    public void viewAndManageStaff() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View and Manage Hospital Staff");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Staff Details");
        System.out.println("(2) Create Staff");
        System.out.println("(3) Update Staff Details");
        System.out.println("(4) Remove Staff");
        System.out.println("(5) Back");
        int opt;
        do {
            opt = Helper.readInt(1, 5);
            switch (opt) {
                case 1:
                    //View Staff Details
                    //print all staff details
                    //can select role and gender, then print all staff of that role and gender
                    //can filter by age
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > View Staff Details");

                    break;
                case 2:
                    //Create Staff
                    //createStaff(String name, Gender gender, int age, Role role, String password) 
                    Role role = null;
                    Gender gender = null;
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Create Staff");
                    System.out.println("Select New Staff Role: ");
                    System.out.println("(1) Doctor");
                    System.out.println("(2) Administrator");
                    System.out.println("(3) Pharmacist");
                    opt = Helper.readInt(1, 3);
                    if(opt == 1) {
                        role = Role.DOCTOR;
                    } else if(opt == 2) {
                        role = Role.ADMINISTRATOR;
                    } else {
                        role = Role.PHARMACIST;
                    }
                
                    System.out.println("Enter Staff Name: ");
                    String name = Helper.readString();
                    System.out.println("Select Gender");
                    System.out.println("(1) Male");
                    System.out.println("(2) Female");
                    opt = Helper.readInt(1, 2);
                    if(opt == 1) {
                        gender = Gender.MALE;
                    } else {
                        gender = Gender.FEMALE;
                    }
                    
                    System.out.println("Enter Staff Age: ");
                    int age = Helper.readInt(1, 100);
                    StaffManager.createStaff(name, gender, age, role, "password");
                    break;
                case 3:
                    //Update Staff Details
                    //enter staff id den will display staff details, can select which attribute to update
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Update Staff Details");

                    break;
                case 4:
                    //Remove Staff
                    //enter staff id to remove
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Remove Staff");
                    break;
                case 5:
                    //Back
                    break;
            }
        } while (opt != 5);
    }

    //////////////////////approveReplenishmentRequest()/////////////////////
    public void approveReplenishmentRequest() {
        PrescriptionManager.printPrescriptionRequest(null);
    }


    //////////////////////View and Manage Medication Inventory/////////////////////

    public void viewAndManageMedicationInventory() {
        Helper.clearScreen();
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Medication Inventory");
        System.out.println("(2) Update Medication Stock");
        System.out.println("(3) Back");
        int opt;
        do {
            opt = Helper.readInt(1,3);
            switch (opt) {
                case 1:
                    //View Medication Inventory
                    //print all medication inventory
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Medication Inventory > View Medication Inventory");
                    PrescriptionManager.viewMedicationInventory();
                    break;
                case 2:
                    //Update Medication Stock
                    //enter medication name and quantity to update
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Medication Inventory > Update Medication Stock");
                    System.out.println("Enter Medication ID: ");
                    String medicationID = Helper.readString();
                    boolean found = InventoryManager.updateMedication(medicationID, 0, 0);
                    if(found) {
                        do {
                            System.out.println("What would you like to update: ");
                            System.out.println("(1) Add Stock");
                            System.out.println("(2) Remove Stock");
                            System.out.println("(3) Set Stock");
                            System.out.println("(4) Set Low Stock Limit");
                            System.out.println("(5) Back");
                            int choice = Helper.readInt(1, 4);
                            switch(choice) {
                                case 1:
                                    System.out.println("Enter Quantity to Add: ");
                                    int quantity = Helper.readInt(1, 100);
                                    InventoryManager.updateMedication(medicationID, 1, quantity);
                                    break;
                                case 2:
                                    System.out.println("Enter Quantity to Remove: ");
                                    quantity = Helper.readInt(1, 100);
                                    InventoryManager.updateMedication(medicationID, 2, quantity);
                                    break;
                                case 3:
                                    System.out.println("Enter New Stock: ");
                                    quantity = Helper.readInt(1, 100);
                                    InventoryManager.updateMedication(medicationID, 3, quantity);
                                    break;
                                case 4:
                                    System.out.println("Enter New Low Stock Limit: ");
                                    quantity = Helper.readInt(1, 100);
                                    InventoryManager.updateMedication(medicationID, 4, quantity);
                                    break;
                                case 5:
                                    break;
                            }
                        } while (opt != 5);
                    } else {
                        System.out.println("Medication not found");
                    }
                    

                    break;
                case 3:
                    //Back
                    break;
            }
        } while (opt != 3);
    }
}


