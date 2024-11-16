package src.view;
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
        System.out.println("Please enter your username and password to login");
        String password = "";

        while (!isLoginSuccessful) {
            System.out.println("Hospital ID: ");
            hospitalID = Helper.readString();
            System.out.println("Password: ");
            password = Helper.readString();

            // Validate login credentials
            currentUserRole = authenticateRole(hospitalID, password);
            if (currentUserRole.equals("admin") ||
                currentUserRole.equals("patient") ||
                currentUserRole.equals("doctor") ||
                currentUserRole.equals("pharmacist")) {
                isLoginSuccessful = true;
            }

            if (!isLoginSuccessful) {
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        Helper.clearScreen();
        System.out.println("Logged in as: " + currentUserRole);
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

    // Dummy Function
    private String authenticateRole(String hospitalID, String password) {
        if (hospitalID.equals("admin")) {
            return "admin";
        } else if (hospitalID.equals("patient")) {
            return "patient";
        } else if (hospitalID.equals("doctor")) {
            return "doctor";
        } else if (hospitalID.equals("pharmacist")) {
            return "pharmacist";
        }
        return ""; 
    }
}
