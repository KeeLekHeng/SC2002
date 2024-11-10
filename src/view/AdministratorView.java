package src.view;
import src.controller.LoginManager;
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
        //1 is update stock, 2 is update low stock limit
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
                    ;
                    break;
                case 2:
                    //View appointment details
                    ;
                    break;
                case 3:
                    //View and manage medication inventory
                    ;
                    break;
                case 4:
                    //Approve replenishment requests
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

    public void viewAndManageStaff() {
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

                    break;
                case 2:
                    //Create Staff
                    //createStaff(String name, Gender gender, int age, Role role, String password) 
                    Role role = null;
                    Gender gender = null;
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

                    break;
                case 4:
                    //Remove Staff
                    break;
                case 5:
                    //Back
                    break;
            }
        } while (opt != 5);
    }
}

