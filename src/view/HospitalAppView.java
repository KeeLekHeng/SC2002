package src.view;
import src.helper.*;

public class HospitalAppView extends MainView {
    private String currentUserRole = "";
    public HospitalAppView(){
        super();
    }

    public void startApplication() {
        
    }


    public void userLogin() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital App View");
        boolean isLoginSuccessful = false;
        System.out.println("Please enter your username and password to login");
        String hospitalID = "";
        String password = "";

        while(!isLoginSuccessful){
            System.out.println("Hospital ID: ");
            hospitalID = Helper.readString();
            System.out.println("Password: ");
            password = Helper.readString();


            //validate login credentials
            currentUserRole = authenticateRole(hospitalID, password);
            if (currentUserRole.equals("admin") ||
            currentUserRole.equals("patient") ||
            currentUserRole.equals("doctor") ||
            currentUserRole.equals("pharmacist")) {
                isLoginSuccessful= true;
            }
    
            if(!isLoginSuccessful){
                System.out.println("Invalid username or password. Please try again.");
            }
        }
        Helper.clearScreen();
        System.out.println("Logged in as: " + currentUserRole);

    }




      @Override
    public void printMenu() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital App View");
            switch (currentUserRole) {
                case "admin":
                    AdministratorView adminView = new AdministratorView();
                    adminView.viewApp();
                    break;
                case "patient":
                    PatientView patientView = new PatientView();
                    patientView.viewApp(); // Load only the guest-specific options
                    break;
                case "doctor":
                    DoctorView doctorView = new DoctorView();
                    doctorView.viewApp();
                    break;
                case "pharmacist":
                    PharmacistView pharmacistView = new PharmacistView();
                    pharmacistView.viewApp();
                    break;
                default:
                    System.out.println("Invalid role or access not permitted.");
            }
        }   

        @Override
    public void viewApp() {
        Helper.clearScreen();
        printBreadCrumbs("Hospital App View");
        userLogin();
        printMenu();
    }



  //Dummy Function
private String authenticateRole(String hospitalID, String password) {
    if (hospitalID.equals("admin")) {
        return "administrator";
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
