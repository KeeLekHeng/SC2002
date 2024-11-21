package src;

import src.database.Database;
import src.helper.Helper;
import src.view.HospitalAppView;

/**
 * Main entry point for the Hospital Management System (HMS) application.
 * Handles the login flow, displays the HMS title screen, 
 * and ensures data persistence before exiting.
 * @author Sean
 * @version 1.0
 * @since 2024-11-20
 */
public class HospitalApp {

    /**
     * Main method to run the application.
     * Clears the screen, displays the title screen, and prompts for login. 
     * After login, it initializes the app interface and saves all data before exiting.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        Helper.clearScreen();
        printHMSTitle();
        Helper.pressAnyKeyToContinue();
        
        Database.loadAllFiles();

        HospitalAppView hospitalAppView = new HospitalAppView();
        String hospitalID = hospitalAppView.userLogin();
        hospitalAppView.viewApp(hospitalID);

        Database.saveAllFiles();
        System.out.println("Thank you for using Hospital Management System");
        Helper.pressAnyKeyToContinue();
    }

    /**
     * Prints the title screen of the Hospital Management System.
     * Includes a decorative header and a welcome message.
     */
    private static void printHMSTitle() {
        System.out.println();
        System.out.println(
                "╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
                "║                                 ██   ██ ███    ███ ███████                                          ║");
        System.out.println(
                "║                                 ██   ██ ████  ████ ██                                               ║");
        System.out.println(
                "║                                 ███████ ██ ████ ██ ███████                                          ║");
        System.out.println(
                "║                                 ██   ██ ██  ██  ██      ██                                          ║");
        System.out.println(
                "║                                 ██   ██ ██      ██ ███████                                          ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "║                            Welcome to Hospital Management System                                    ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
