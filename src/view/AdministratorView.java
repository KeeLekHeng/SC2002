package src.view;
import java.util.List;
import src.controller.AppointmentManager;
import src.controller.InventoryManager;
import src.controller.LoginManager;
import src.controller.PrescriptionManager;
import src.controller.StaffManager;
import src.database.Database;
import src.helper.*;
import src.model.ReplenishRequest;
import src.model.Staff;
import src.model.enums.*;

public class AdministratorView extends MainView {
    public AdministratorView () {
        super();
    }

    @Override
    public void printMenu() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View and Manage Hospital Staff"); //done
        System.out.println("(2) View Appointments details"); 
        System.out.println("(3) View and Manage Medication Inventory ");
        System.out.println("(4) Approve Replenishment Requests ");
        System.out.println("(5) Change Password");
        System.out.println("(6) Logout ");
    }

    @Override
    public void viewApp(String hospitalID) {
        int mainOpt;
        do {
            printMenu();
            mainOpt = Helper.readInt(1, 6);
            switch (mainOpt) {
                case 1:
                    //View and manage hospital staff
                    viewAndManageStaff(hospitalID);
                    ;
                    break;
                case 2:
                    //View appointment details
                    viewAppointmentDetails(hospitalID);
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
                    approveReplenishmentRequest();
                    break;
                case 5:
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 6:
                    //Logout
                    break;
            }
        } while (mainOpt != 6);
    }
////////////////////////////// View and Manage Staff //////////////////////////////
    public void viewAndManageStaff(String hospitalID) {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View and Manage Hospital Staff");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Staff Details");
        System.out.println("(2) Create Staff");
        System.out.println("(3) Update Staff Details"); //update staff parameter is staff instead of hospitalID
        System.out.println("(4) Remove Staff");
        System.out.println("(5) Initialize Dummy Staff");
        System.out.println("(6) Initialize Dummy Patients");
        System.out.println("(7) Back");
        int subOpt;
        do {
            subOpt = Helper.readInt(1, 7);
            switch (subOpt) {
                case 1:
                    //View Staff Details
                    //print all staff details
                    //can select role and gender, then print all staff of that role and gender
                    //can filter by age
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > View Staff Details");
                    System.out.println("What would you like to do ?");
                    System.out.println("(1) View Staff by ID");
                    System.out.println("(2) View Staff by Name");
                    System.out.println("(3) View Staff by Age");
                    System.out.println("(4) View Staff by Gender");
                    System.out.println("(5) View Staff by Role");
                    System.out.println("(6) Back");
                    int choice = Helper.readInt(1, 6);
                    switch(choice) {
                        case 1:
                            StaffManager.printAllStaff(true);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 2:
                            StaffManager.printAllStaff(false);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 3:
                            StaffManager.viewStaff(choice-2);                            
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 4:
                            StaffManager.viewStaff(choice-2);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 5:
                            StaffManager.viewStaff(choice-2);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 6:
                            return;
                        default: System.out.println("Unexpected error occurred. Please try again.");
                    }
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
                    System.out.println("(4) Back");
                    int opt = Helper.readInt(1, 4);
                    if(opt == 1) {
                        role = Role.DOCTOR;
                    } else if(opt == 2) {
                        role = Role.ADMINISTRATOR;
                    } else if(opt == 3) {
                        role = Role.PHARMACIST;
                    }else{
                        break;
                    }
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Create Staff");
                    System.out.println("Enter Staff Name: ");
                    String name = Helper.readString();
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Create Staff");
                    System.out.println("Select Gender");
                    System.out.println("(1) Male");
                    System.out.println("(2) Female");
                    opt = Helper.readInt(1, 2);
                    if(opt == 1) {
                        gender = Gender.MALE;
                    } else {
                        gender = Gender.FEMALE;
                    }
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Create Staff");
                    System.out.println("Enter Staff Age: ");
                    int age = Helper.readInt(1, 100);
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Create Staff");
                    StaffManager.createStaff(name, gender, age, role);
                    Helper.pressAnyKeyToContinue();
                    return;
                case 3:
                    //Update Staff Details
                    //enter staff id den will display staff details, can select which attribute to update
                    do {
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Update Staff Details");
                    System.out.println("Enter Staff ID to Update: ");
                    String updateID = Helper.readString();
                    Staff staff = StaffManager.searchStaffById(updateID);
                    if(staff == null) {
                        System.out.println("Staff not found");
                        Helper.pressAnyKeyToContinue();
                        opt = 5;
                        return;
                    }
                    StaffManager.printStaffDetails(updateID);
                    System.out.println("What would you like to update: ");
                    System.out.println("(1) Name");
                    System.out.println("(2) Age");
                    System.out.println("(3) Gender");
                    System.out.println("(4) Role");
                    System.out.println("(5) Back");
                    opt = Helper.readInt(1, 5);
                        switch(opt) {
                            case 1:
                                System.out.println("Enter New Name: ");
                                String newName = Helper.readString();
                                if(StaffManager.updateStaff(updateID, 1, newName)){
                                    System.out.println("Staff Updated");
                                }else{
                                    System.out.println("Staff not found");
                                }
                                Helper.pressAnyKeyToContinue();
                                return;
                            case 2:
                                System.out.println("Enter New Age: ");
                                int newAge = Helper.readInt(1, 120);
                                if(StaffManager.updateStaff(updateID, 2, newAge)){
                                    System.out.println("Staff Updated");
                                }else{
                                    System.out.println("Staff not found");
                                }
                                Helper.pressAnyKeyToContinue();
                                return;
                            case 3:
                                selectGender();
                                Helper.pressAnyKeyToContinue();
                                return;
                            case 4:
                                selectRole();
                                Helper.pressAnyKeyToContinue();
                                return;
                            case 5:
                                break;
                        }
                    } while (opt != 5);
                    return;
                case 4:
                    //Remove Staff
                    //enter staff id to remove
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Remove Staff");
                    System.out.println("Enter Staff ID to Remove: ");
                    String removeID = Helper.readString();
                    if(StaffManager.removeStaff(removeID)){
                        System.out.println("Staff Removed");
                    }else{
                        System.out.println("Staff not found");
                    }
                    return;
                case 5: 
                    Helper.clearScreen();
                    if(initializeStaff()) {
                        System.out.println("Dummy Staff Initialized");
                    }else{
                        System.out.println("Dummy Staff not Initialized");
                    }
                    Helper.pressAnyKeyToContinue();
                    return;
                case 6:
                    Helper.clearScreen();
                    if(initializeDummyPatients()) {
                        System.out.println("Dummy Patients Initialized");
                    }else{
                        System.out.println("Dummy Patients not Initialized");
                    }
                    Helper.pressAnyKeyToContinue();
                    return;
                case 7:
                    break;
            }
        } while (subOpt != 7);
    }

    private boolean initializeStaff() {
        return Database.initializeDummyStaff();
    }

    private boolean initializeDummyPatients() {
        return Database.initializeDummyPatients();
    }

    private boolean selectGender() {
        System.out.println("Select Gender:");
        System.out.println("(1) Male");
        System.out.println("(2) Female");
        int genderOpt = Helper.readInt(1, 2); 
        if (genderOpt == 1 || genderOpt == 2) {
            Gender selectedGender = (genderOpt == 1) ? Gender.MALE : Gender.FEMALE;
            System.out.println("Selected Gender: " + selectedGender);
            return true; 
        } else {
            System.out.println("Invalid gender selection.");
            return false; 
        }
    }
    
    private boolean selectRole() {
        System.out.println("Select Role:");
        System.out.println("(1) Doctor");
        System.out.println("(2) Administrator");
        System.out.println("(3) Pharmacist");
        int roleOpt = Helper.readInt(1, 3);
        if (roleOpt >= 1 && roleOpt <= 3) {
            Role selectedRole = (roleOpt == 1) ? Role.DOCTOR :
                               (roleOpt == 2) ? Role.ADMINISTRATOR : Role.PHARMACIST;
            System.out.println("Selected Role: " + selectedRole);
            return true;
        } else {
            System.out.println("Invalid role selection.");
            return false;
        }
    }


    //////////////////////approveReplenishmentRequest()/////////////////////
    public void approveReplenishmentRequest() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > Approve Replenishment Requests");
        //loop below
        List <ReplenishRequest> replenishRequests = InventoryManager.getPendingRequests();
        for (ReplenishRequest request : replenishRequests) {
            String requestID = request.getRequestID();
            InventoryManager.printReplenishRequest(request); 
            System.out.println("Do you want to approve this request?");
            System.out.println("(1) Approve");
            System.out.println("(2) Reject");
            System.out.println("(3) Back");
            int opt = Helper.readInt(1, 3);
            switch(opt) {
                case 1:
                    //Approve
                    InventoryManager.updateReplenishRequests(requestID, opt);
                    break;
                case 2:
                    //Reject
                    InventoryManager.updateReplenishRequests(requestID, opt);
                    break;
                case 3:
                    break;
            }
            System.out.println("All request processed");
        } 

    }

    /*
    PatientManager.printAllPatients(true);
    Helper.pressAnyKeyToContinue();
    return;*/ 
    
    //////////////////////View and Manage Medication Inventory/////////////////////

    public void viewAndManageMedicationInventory() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View and Manage Medication Inventory");
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
                    Helper.pressAnyKeyToContinue();
                    opt = 3;
                    break;
                case 2:
                    //Update Medication Stock
                    //enter medication name and quantity to update
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Medication Inventory > Update Medication Stock");
                    System.out.println("Enter Medication ID: ");
                    String medicationID = Helper.readString();
                    boolean found = InventoryManager.updateMedication(medicationID, 0, 0);
                    int subOpt;
                    if(found) {
                        do {
                            System.out.println("What would you like to update: ");
                            System.out.println("(1) Add Stock");
                            System.out.println("(2) Remove Stock");
                            System.out.println("(3) Set Stock");
                            System.out.println("(4) Set Low Stock Limit");
                            System.out.println("(5) Back");
                            subOpt = Helper.readInt(1, 5);
                            switch (subOpt) {
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
                                    break; // Exit the submenu
                            }
                        } while (subOpt != 5); // Correctly check submenu exit condition
                    } else {
                        System.out.println("Medication not found");
                        Helper.pressAnyKeyToContinue();
                        return;
                    }
                    break;
                case 3:
                    //Back
                    break;
            }
        } while (opt != 3);
    }
    //////////////////viewAppointmentDetails()////////////////////
public void viewAppointmentDetails(String hospitalID){
    int choice;
    do {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View Appointment Details");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Upcoming Appointments");
        System.out.println("(2) View All Appointments");
        System.out.println("(3) Back ");
        choice = Helper.readInt(1, 3);
        switch(choice) {
            case 1:
                AppointmentManager.viewScheduledAppointments(hospitalID, choice);
                break;
            case 2:
                AppointmentManager.viewScheduledAppointments(hospitalID, choice);
                break;
            case 3:
                break;
            default: System.out.println("Invalid Choice");
        }
    } while (choice != 3);
    }
}

