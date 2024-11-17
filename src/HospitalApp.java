package src;

import src.database.Database;
import src.helper.Helper;
import src.view.HospitalAppView;

/**
 * Main entry point for the Hospital Management System (HMS) application.
 * This class handles the login flow and the display of the HMS title screen.
 * It runs a loop that continuously displays the app's title screen, prompts for user login, 
 * and allows the user to access the system. It also ensures that all data is saved before exiting.
 * @author Sean
 * @version 1.0
 * @since 2024-11-17
 */
public class HospitalApp {
    
    /**
     * Main method that runs the application.
     * The method loops indefinitely to handle user login and system interaction.
     * It clears the screen, displays the hospital management system title screen, 
     * and provides the login interface. After the user logs in, the app saves all files 
     * and thanks the user before prompting them to press any key to continue or exit.x
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
      
       while(true){
            Helper.clearScreen();
            printHMSTitle();
            Helper.pressAnyKeyToContinue();  // Prompt for any key to continue before login.

            HospitalAppView hospitalAppView = new HospitalAppView();
            String hospitalID = hospitalAppView.userLogin();  // Handle user login.
            hospitalAppView.viewApp(hospitalID);  // Display the app interface.

            Database.saveAllFiles();  // Save all data to the database.
            System.out.println("Thank you for using Hospital Management System");
            Helper.pressAnyKeyToContinue();  // Prompt for any key to continue or exit.
        }
    }

    /**
     * Prints the title screen for the Hospital Management System.
     * The title includes a decorated header and a welcome message to the user.
     * This method is called before user login to introduce the system.
     */
    private static void printHMSTitle() {
        System.out.println();
        System.out.println(
                "╔═════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.println(
                "║                          __    __    __   ______   ______    __       __                            ║");
        System.out.println(
                "║                         /  |  /  |  /  | /      \\ /      \\  /  \\     /  |                           ║");
        System.out.println(
                "║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐  ▐▐▐▐▐▐▐  ▐▐▐▐▐▐  ▐▐  \\   /▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐ |__▐▐ |  ▐▐▐|▐▐ |  ▐▐ |▐▐ |__▐▐ |▐▐▐▐▐▐\\ /▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐    ▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐    ▐▐ |▐▐ |▐▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐▐▐▐▐▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐▐▐▐▐▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐ |  ▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐ |  ▐▐ |  ▐▐ |▐▐ |__▐▐ |▐▐ |__▐▐ |▐▐ | ▐▐ |▐▐ |                           ║");
        System.out.println(
                "║                         ▐▐/   ▐▐/   ▐▐/  ▐▐▐▐▐▐▐/ ▐▐▐▐▐▐▐/ ▐▐/  ▐▐/ ▐▐/                            ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "║                            Welcome to Hospital Management System                                     ║");
        System.out.println(
                "║                                                                                                     ║");
        System.out.println(
                "╚═════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }
}
