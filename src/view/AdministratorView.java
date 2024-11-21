package src.view;

import java.util.ArrayList;
import java.util.List;
import src.controller.AppointmentManager;
import src.controller.InventoryManager;//
import src.controller.LoginManager;//
import src.controller.PrescriptionManager;//
import src.controller.StaffManager;//
import src.database.Database;//
import src.helper.*;//
import src.model.ReplenishRequest;//
import src.model.Staff;
import src.model.enums.*;//

/**
 * This class represents the administrator view, extending the main view.
 * It provides methods for administrators to manage staff, appointments,
 * medication inventory, and other related operations.
 * @author Seann
 * @version 1.0
 * @since 2024-11-20
 */
public class AdministratorView extends MainView {

    /**
     * Constructs an AdministratorView and initializes the main view.
     */
    public AdministratorView() {
        super();
    }

    /**
     * Displays the main menu for the administrator.
     */
    @Override
    public void printMenu() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View and Manage Hospital Staff");
        System.out.println("(2) View Appointments details");
        System.out.println("(3) View and Manage Medication Inventory");
        System.out.println("(4) Approve Replenishment Requests");
        System.out.println("(5) Change Password");
        System.out.println("(6) Logout");
    }

    /**
     * Handles the administrator's interactions within the application.
     * 
     * @param hospitalID The unique ID of the hospital.
     */
    @Override
    public void viewApp(String hospitalID) {
        int mainOpt;
        do {
            printMenu();
            mainOpt = Helper.readInt(1, 6);
            switch (mainOpt) {
                case 1:
                    viewAndManageStaff(hospitalID);
                    break;
                case 2:
                    viewAppointmentDetails(hospitalID);
                    break;
                case 3:
                    viewAndManageMedicationInventory();
                    break;
                case 4:
                    approveReplenishmentRequest();
                    break;
                case 5:
                    LoginManager.createNewPassword(hospitalID);
                    break;
                case 6:
                    return;
            }
        } while (mainOpt != 6);
    }

    /**
     * Displays options for viewing, creating, updating, and managing hospital
     * staff.
     * 
     * @param hospitalID The unique ID of the hospital.
     */
    public void viewAndManageStaff(String hospitalID) {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View and Manage Hospital Staff");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Staff Details");
        System.out.println("(2) Create Staff");
        System.out.println("(3) Update Staff Details");
        System.out.println("(4) Remove Staff");
        System.out.println("(5) Initialize Dummy Staff");
        System.out.println("(6) Initialize Dummy Patients");
        System.out.println("(7) Initialize Dummy Medications");
        System.out.println("(8) Clear Database");
        System.out.println("(9) Back");
        int subOpt;
        do {
            subOpt = Helper.readInt(1, 9);
            switch (subOpt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > View Staff Details");
                    System.out.println("What would you like to do ?");
                    System.out.println("(1) View Staff by ID");
                    System.out.println("(2) View Staff by Name");
                    System.out.println("(3) View Staff by Age");
                    System.out.println("(4) View Staff by Gender");
                    System.out.println("(5) View Staff by Role");
                    System.out.println("(6) View Staff by Employment Status");
                    System.out.println("(7) Back");
                    int choice = Helper.readInt(1, 7);
                    switch (choice) {
                        case 1:
                            StaffManager.viewStaff(choice);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 2:
                            StaffManager.viewStaff(choice);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 3:
                            StaffManager.viewStaff(choice);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 4:
                            StaffManager.viewStaff(choice);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 5:
                            StaffManager.viewStaff(choice);
                            Helper.pressAnyKeyToContinue();
                        case 6:
                            StaffManager.viewStaff(choice);
                            Helper.pressAnyKeyToContinue();
                            return;
                        case 7:
                            return;
                        default:
                            System.out.println("Unexpected error occurred. Please try again.");
                    }
                    break;
                case 2:
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
                    if (opt == 1) {
                        role = Role.DOCTOR;
                    } else if (opt == 2) {
                        role = Role.ADMINISTRATOR;
                    } else if (opt == 3) {
                        role = Role.PHARMACIST;
                    } else {
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
                    if (opt == 1) {
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
                    do {
                        Helper.clearScreen();
                        printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Update Staff Details");
                        System.out.println("Enter Staff ID to Update: ");
                        String updateID = Helper.readString();
                        ArrayList<Staff> staff = StaffManager.searchStaffById(updateID);
                        if (staff.isEmpty()) {
                            System.out.println("Staff with ID " + updateID + " not found");
                            Helper.pressAnyKeyToContinue();
                            opt = 5;
                            break;
                        }
                        StaffManager.printStaffDetails(updateID);
                        System.out.println("What would you like to update: ");
                        System.out.println("(1) Name");
                        System.out.println("(2) Age");
                        System.out.println("(3) Gender");
                        System.out.println("(4) Role");
                        System.out.println("(5) Back");
                        opt = Helper.readInt(1, 5);
                        switch (opt) {
                            case 1:
                                System.out.println("Enter New Name: ");
                                String newName = Helper.readString();
                                if (StaffManager.updateStaff(updateID, 1, newName)) {
                                    System.out.println("Staff Updated");
                                } else {
                                    System.out.println("Staff not found");
                                }
                                Helper.pressAnyKeyToContinue();
                                return;
                            case 2:
                                System.out.println("Enter New Age: ");
                                int newAge = Helper.readInt(1, 120);
                                if (StaffManager.updateStaff(updateID, 2, newAge)) {
                                    System.out.println("Staff Updated");
                                } else {
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
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Hospital Staff > Remove Staff");
                    StaffManager.viewStaff(1);
                    System.out.println("Enter Staff ID to Remove: ");
                    String removeID = Helper.readString();
                    if (StaffManager.removeStaff(removeID)) {
                        System.out.println("Staff Removed");
                    } else {
                        System.out.println("Staff not found");
                    }
                    return;
                case 5:
                    Helper.clearScreen();
                    if (initializeStaff()) {
                        System.out.println("Dummy Staff Initialized");
                    } else {
                        System.out.println("Dummy Staff not Initialized");
                    }
                    Helper.pressAnyKeyToContinue();
                    return;
                case 6:
                    Helper.clearScreen();
                    if (initializeDummyPatients()) {
                        System.out.println("Dummy Patients Initialized");
                    } else {
                        System.out.println("Dummy Patients not Initialized");
                    }
                    Helper.pressAnyKeyToContinue();
                    return;
                case 7:
                    Helper.clearScreen();
                    Database.initializeDummyMedication();
                    System.out.println("Dummy Medications Initialized");
                    Helper.pressAnyKeyToContinue();
                    return;
                case 8:
                    Helper.clearScreen();
                    Database.clearDatabase();
                    Database.initializeStartingAdmin();
                    System.out.println("Database Cleared and Starting Admin initialized ");
                    Helper.pressAnyKeyToContinue();
                    return;
                case 9:
                    break;
            }
        } while (subOpt != 9);
    }

    /**
     * Initializes dummy staff data in the database.
     * @return true if dummy staff data is successfully initialized, false otherwise.
     */
    private boolean initializeStaff() {
        return Database.initializeDummyStaff();
    }

    /**
     * Initializes dummy patient data in the database.
     * @return true if dummy patient data is successfully initialized, false otherwise.
     */
    private boolean initializeDummyPatients() {
        return Database.initializeDummyPatients();
    }

    /**
     * Prompts the user to select a gender and returns the selection.
     * @return true if a valid gender is selected, false if the selection is invalid.
     */
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

    /**
     * Prompts the user to select a role and returns the selection.
     * @return true if a valid role is selected, false if the selection is invalid.
     */
    private boolean selectRole() {
        System.out.println("Select Role:");
        System.out.println("(1) Doctor");
        System.out.println("(2) Administrator");
        System.out.println("(3) Pharmacist");
        int roleOpt = Helper.readInt(1, 3);
        if (roleOpt >= 1 && roleOpt <= 3) {
            Role selectedRole = (roleOpt == 1) ? Role.DOCTOR : (roleOpt == 2) ? Role.ADMINISTRATOR : Role.PHARMACIST;
            System.out.println("Selected Role: " + selectedRole);
            return true;
        } else {
            System.out.println("Invalid role selection.");
            return false;
        }
    }

    /**
     * Approves or rejects replenishment requests.
     * Loops through the pending replenish requests and processes user input for approval or rejection.
     */
    public void approveReplenishmentRequest() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > Approve Replenishment Requests");
        List <ReplenishRequest> replenishRequests = InventoryManager.getPendingRequests();
        if(replenishRequests == null) {
            System.out.println("No replenish requests currently.");
            Helper.pressAnyKeyToContinue();
            return;
        }
        for (ReplenishRequest request : replenishRequests) {
            String requestID = request.getRequestID();
            InventoryManager.printReplenishRequest(request);
            System.out.println("Do you want to approve this request?");
            System.out.println("(1) Approve");
            System.out.println("(2) Reject");
            System.out.println("(3) Back");
            int opt = Helper.readInt(1, 3);
            switch (opt) {
                case 1:
                    InventoryManager.updateReplenishRequests(requestID, opt);
                    break;
                case 2:
                    InventoryManager.updateReplenishRequests(requestID, opt);
                    break;
                case 3:
                    return;
            }
        }
        System.out.println("All request processed");
        Helper.pressAnyKeyToContinue();
    }

    /**
     * Allows viewing and managing the medication inventory.
     * Includes options to view inventory, update stock, or set low stock limits.
     */
    public void viewAndManageMedicationInventory() {
        Helper.clearScreen();
        printBreadCrumbs("Main Menu > View and Manage Medication Inventory");
        System.out.println("What would you like to do ?");
        System.out.println("(1) View Medication Inventory");
        System.out.println("(2) Update Medication Stock");
        System.out.println("(3) Back");
        int opt;
        do {
            opt = Helper.readInt(1, 3);
            switch (opt) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Medication Inventory > View Medication Inventory");
                    PrescriptionManager.viewMedicationInventory();
                    Helper.pressAnyKeyToContinue();
                    opt = 3;
                    break;
                case 2:
                    boolean found;
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View and Manage Medication Inventory > Update Medication Stock");
                    PrescriptionManager.viewMedicationInventory();
                    String medicationID = Helper.readMedicineID();
                    if (InventoryManager.searchMedicineByID(medicationID) == null) {
                        System.out.println("Medication does not exist!");
                        Helper.pressAnyKeyToContinue();
                        return;
                    } else {
                        found = true;
                    }
                    int subOpt;
                    if (found) {
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
                                    if (InventoryManager.updateMedication(medicationID, 1, quantity)) {
                                        System.out.println("Stock Updated");
                                    } else {
                                        System.out.println("Failed to add stock");
                                    }
                                    Helper.pressAnyKeyToContinue();
                                    return;
                                case 2:
                                    System.out.println("Enter Quantity to Remove: ");
                                    quantity = Helper.readInt(1, 100);
                                    if (InventoryManager.updateMedication(medicationID, 2, quantity)) {
                                        System.out.println("Stock Updated");
                                    } else {
                                        System.out.println("Failed to remove stock");
                                    }
                                    Helper.pressAnyKeyToContinue();
                                    return;
                                case 3:
                                    System.out.println("Enter New Stock: ");
                                    quantity = Helper.readInt(1, 100);
                                    if (InventoryManager.updateMedication(medicationID, 3, quantity)) {
                                        System.out.println("Stock Updated");
                                    } else {
                                        System.out.println("Failed to update stock");
                                    }
                                    Helper.pressAnyKeyToContinue();
                                    return;
                                case 4:
                                    System.out.println("Enter New Low Stock Limit: ");
                                    quantity = Helper.readInt(1, 100);
                                    if (InventoryManager.updateMedication(medicationID, 4, quantity)) {
                                        System.out.println("Low Stock Limit Updated");
                                    } else {
                                        System.out.println("Failed to update low stock limit");
                                    }
                                    Helper.pressAnyKeyToContinue();
                                    return;
                                case 5:
                                    return;
                            }
                        } while (subOpt != 5);
                    } else {
                        System.out.println("Medication not found");
                        Helper.pressAnyKeyToContinue();
                        return;
                    }
                    break;
                case 3:
                    break;
            }
        } while (opt != 3);
    }

    /**
     * Views appointment details based on the provided hospital ID.
     * Allows the user to view upcoming or all appointments.
     * @param hospitalID the ID of the hospital to view appointments for
     */
    public void viewAppointmentDetails(String hospitalID) {
        int choice;
        do {
            Helper.clearScreen();
            printBreadCrumbs("Main Menu > View Appointment Details");
            System.out.println("What would you like to do ?");
            System.out.println("(1) View Upcoming Appointments");
            System.out.println("(2) View All Appointments");
            System.out.println("(3) Back ");
            choice = Helper.readInt(1, 3);
            switch (choice) {
                case 1:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Appointment Details");
                    AppointmentManager.viewScheduledAppointments(hospitalID, choice);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 2:
                    Helper.clearScreen();
                    printBreadCrumbs("Main Menu > View Appointment Details");
                    AppointmentManager.viewScheduledAppointments(hospitalID, choice);
                    Helper.pressAnyKeyToContinue();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Invalid Choice");
            }
        } while (choice != 3);
    }
}