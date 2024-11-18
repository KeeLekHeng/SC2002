package src.view;

import src.controller.LoginManager;
import src.helper.*;

public class HospitalAppView extends MainView {
    private String currentUserRole = "";
    String hospitalID = "";

    public HospitalAppView() {
        super();
    }

    public String userLogin() {
        Helper.clearScreen();
        printBreadCrumbs("User Login");
        boolean isLoginSuccessful = false;
        String password = "";
        String role = "";

        System.out.println("Please enter your hospital ID and password to login");
        Helper.readString();

        while (!isLoginSuccessful) {
            System.out.println("Hospital ID: ");
            hospitalID = Helper.readString();

            // Check for fake login
            if (hospitalID.equalsIgnoreCase("fake")) {
                System.out.println("Enter preset fake role (admin/patient/doctor/pharmacist): ");
                role = Helper.readString().toLowerCase(); // Get fake role directly
                if (role.equals("admin") || role.equals("patient") || role.equals("doctor")
                        || role.equals("pharmacist")) {
                    isLoginSuccessful = true; // Fake login successful
                    currentUserRole = role;
                    System.out.println("Logged in as: " + currentUserRole);
                    break; // Exit the loop for fake login
                } else {
                    System.out.println("Invalid fake role. Valid options: admin, patient, doctor, pharmacist.");
                    continue; // Retry for fake login
                }
            }

            // Prompt for real login
            System.out.println("Password: ");
            password = Helper.readString();

            // Validate login credentials
            currentUserRole = LoginManager.LoginUser(hospitalID, password); // currentuserrole = Doctor
            if (currentUserRole.equals("unsuccessful")) {
                System.out.println("Invalid username or password. Please try again.");
            } else {
                isLoginSuccessful = true; // Real login successful
                System.out.println("Logged in as: " + currentUserRole);
            }
        }

        Helper.clearScreen();
        return hospitalID;
    }

    @Override
    public void viewApp(String hospitalID) {
        Helper.clearScreen();
        printBreadCrumbs("Hospital App View");
        printMenu();
    }

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
