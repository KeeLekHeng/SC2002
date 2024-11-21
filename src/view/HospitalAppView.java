package src.view;

import java.io.Console;
import src.controller.LoginManager;
import src.helper.*;

/**
 * The HospitalAppView class provides the user interface for logging in to the
 * hospital system and accessing different views based on the user's role.
 * It handles the user login process and directs users to the appropriate
 * role-based view.
 * 
 * @author Seann
 * @version 1.0
 * @since 2024-11-20
 */
public class HospitalAppView extends MainView {
    private String currentUserRole = "";
    String hospitalID = "";

    /**
     * Constructs a new instance of the HospitalAppView.
     * Calls the superclass constructor to initialize the view.
     */
    public HospitalAppView() {
        super();
    }

    /**
     * Manages the login process for the user.
     * Prompts the user for hospital ID and password, and validates login
     * credentials.
     * If successful, the user's role is stored and used to provide the correct
     * view.
     * 
     * @return the hospital ID of the logged-in user.
     */
    public String userLogin() {
        // Initialize console
        Console console = System.console();
        Helper.clearScreen();
        printBreadCrumbs("User Login");
        boolean isLoginSuccessful = false;
        String role = "";
        int tries = 0;

        System.out.println("Please enter your hospital ID and password to login");
        Helper.readString();
        while (!isLoginSuccessful && tries < 5) {
            System.out.println("Hospital ID: ");
            hospitalID = Helper.readString();

            if (hospitalID.equalsIgnoreCase("fake")) {
                System.out.println("Enter preset fake role (admin/patient/doctor/pharmacist): ");
                role = Helper.readString().toLowerCase();
                if (role.equals("admin") || role.equals("patient") || role.equals("doctor")
                        || role.equals("pharmacist")) {
                    isLoginSuccessful = true;
                    currentUserRole = role;
                    System.out.println("Logged in as: " + currentUserRole);
                    break;
                } else {
                    System.out.println("Invalid fake role. Valid options: admin, patient, doctor, pharmacist.");
                    continue;
                }
            }

            char[] passwordArray = console.readPassword("Password: ");
            String password = new String(passwordArray);

            currentUserRole = LoginManager.LoginUser(hospitalID, password);
            if (currentUserRole.equals("unsuccessful")) {
                tries++;
                if (tries == 5) {
                    System.out.println("Max attempts exceeded. ");
                    break;
                }
                System.out.println(
                        "Invalid username or password. " + (5 - tries) + " attempts remaining. Please try again.");

            } else {
                isLoginSuccessful = true;
                System.out.println("Logged in as: " + currentUserRole);
            }
        }
        Helper.clearScreen();
        return hospitalID;
    }

    /**
     * Displays the hospital app view for the logged-in user.
     * Directs the user to the appropriate role-based view.
     * 
     * @param hospitalID the ID of the hospital.
     */
    @Override
    public void viewApp(String hospitalID) {
        Helper.clearScreen();
        printBreadCrumbs("Hospital App View");
        printMenu();
    }

    /**
     * Prints the main menu for the hospital app based on the current user's role.
     * Directs the user to their respective view: admin, patient, doctor, or
     * pharmacist.
     */
    @Override
    public void printMenu() {
        switch (currentUserRole) {
            case "admin":
                AdministratorView adminView = new AdministratorView();
                adminView.viewApp(hospitalID);
                break;
            case "patient":
                PatientView patientView = new PatientView();
                patientView.viewApp(hospitalID);
                break;
            case "doctor":
                DoctorView doctorView = new DoctorView();
                doctorView.viewApp(hospitalID);
                break;
            case "pharmacist":
                PharmacistView pharmacistView = new PharmacistView();
                pharmacistView.viewApp(hospitalID);
                break;
            default:
                System.out.println("Invalid role or access not permitted.");
        }
    }
}