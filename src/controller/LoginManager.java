package src.controller;

import java.util.Scanner;
import java.util.ArrayList;
import src.database.Database;
import src.database.FileType;
import src.model.Patient;
import src.model.Staff;
import src.model.User;

// For javadocs

/**
 * LoginManager allows the user to Login to the hospital system
 * User is to input their hospitalID
 * and their password
 * The login credentials will be validated with the database
 * and the user will be given role-specific access to the system
 * 
 * The user is also able to change their password if needed
 * The password must meet certain criterias to be parsed as the new password
 * 
 * @author Benjamin Kam
 * @version 1.0
 * @since 2024-11-8
 */

public class LoginManager {
    static Scanner scanner = new Scanner(System.in);

    // Default constructor of LoginManager
    public LoginManager() {
    }

    public static String LoginUser(String hospitalID, String password) {
        String pw;

        // checks whether hospitalID is in any one of the databases
        if (Database.STAFF.containsKey(hospitalID) || Database.PATIENTS.containsKey(hospitalID)) {
            // checks if hospitalID is in STAFF database and continue
            if (Database.STAFF.containsKey(hospitalID)) {
                Staff staff = Database.STAFF.get(hospitalID);
                pw = staff.getPassword();
                if (password.equals(pw)) { // when input password is correct
                    // check for role then return role
                    String item = checkRoleAndReturn(hospitalID);
                    return item;
                }
                // checks if hospitalID is in PATIENT database and continue
            } else if (Database.PATIENTS.containsKey(hospitalID)) {
                Patient patient = Database.PATIENTS.get(hospitalID);
                pw = patient.getPassword();
                if (password.equals(pw)) { // when input password is correct
                    // check for role then return role
                    String item = checkRoleAndReturn(hospitalID);
                    return item;
                }
            }
        } else {
            // hospitalId cannot be found in database
            return "unsuccessful";
        }
        return "unsuccessful";
    }

    public static ArrayList<User> searchUserById(String hospitalID) {
        ArrayList<User> searchList = new ArrayList<User>();
        if (Database.PATIENTS.containsKey(hospitalID)) {
            User searchedGuest = Database.PATIENTS.get(hospitalID);
            searchList.add(searchedGuest);
        }

        if (Database.STAFF.containsKey(hospitalID)) {
            User searchedGuest = Database.STAFF.get(hospitalID);
            searchList.add(searchedGuest);
        }
        return searchList;
    }

    // checks the role of provided hospitalId and returns role
    public static String checkRoleAndReturn(String hospitalId) {
        char ch = hospitalId.charAt(0);

        switch (ch) {
            case 'D':
                return "doctor";
            case 'A':
                return "admin";
            case 'P':
                int length = hospitalId.length();
                if (length == 4) {
                    return "pharmacist";
                } else if (length == 5) {
                    return "patient";
                }
            default:
                return "unsuccessful";
        }
    }

    // creates new password for the user
    public static void createNewPassword(String hospitalId) {
        String pw;
        boolean item = true;
        // valid bit that keeps the while loop running
        int valid = 0;
        // attributes to check for validity of password
        int lowercase = 0;
        int uppercase = 0;
        int hasDigit = 0;
        int symbolCount = 0;
        // finds role for database update
        String role = findRole(hospitalId);

        int tries = 0;
        // only continues if the role has no error
        if (!(role == "unsucessful")) {
            // user must know current password to change the password
            while (tries < 3) {
                System.out.println("Enter your current password: ");
                String attempt = scanner.nextLine();
                if ("PATIENTS".equals(role)) {
                    Patient patient = Database.PATIENTS.get(hospitalId);
                    if (patient != null && attempt.equals(patient.getPassword())) {
                        break;
                    }
                }
                if ("STAFF".equals(role)) {
                    Staff staff = Database.STAFF.get(hospitalId);
                    if (staff != null && attempt.equals(staff.getPassword())) {
                        break;
                    }
                }
                tries++;
            }
            tries = 0;

            while (item && tries < 5) {
                System.out.println("Enter the new password: ");
                pw = scanner.nextLine();

                valid = 0;
                lowercase = 0;
                uppercase = 0;
                hasDigit = 0;
                symbolCount = 0;
                // checks for valid password length
                if (pw.length() > 10) {
                    valid++;
                }
                for (char ch : pw.toCharArray()) {
                    // checks if there is a lowercase letter in the string, if there is at least
                    // one, then the if statement will be ignored
                    if (lowercase < 1 && Character.isLowerCase(ch)) {
                        lowercase++;
                        valid++;
                        // same as above but for uppercase letter
                    } else if (uppercase < 1 && Character.isUpperCase(ch)) {
                        uppercase++;
                        valid++;
                        // same as above but for digit
                    } else if (hasDigit < 1 && Character.isDigit(ch)) {
                        hasDigit++;
                        valid++;
                        // counts the number of digits in the string
                    } else if (!Character.isLetterOrDigit(ch)) {
                        symbolCount++;
                    }
                }
                // if > 2 digits in string, then valid bit increments
                if (symbolCount >= 2) {
                    valid++;
                }
                System.out.println(valid);
                // uppercase + lowercase + hasDigit + hasSymbol = 4
                if (valid == 5) {
                    if ("STAFF".equals(role)) {
                        Staff staff = Database.STAFF.get(hospitalId);
                        staff.setPassword(pw);
                        Database.STAFF.put(staff.getId(), staff);
                    } else if ("PATIENTS".equals(role)) {
                        Patient patient = Database.PATIENTS.get(hospitalId);
                        patient.setPassword(pw);
                        Database.PATIENTS.put(patient.getId(), patient);
                    }
                    item = false;
                    Database.saveFileIntoDatabase(FileType.STAFF);
                }
                tries++;

                if (tries == 5) {
                    System.out.println("Too many attempts. Returning..");
                }

            }
        } else {
            System.out.println("The hospitalId that you provided is invalid");
        }
    }

    // function to find hospitalId's role
    public static String findRole(String hospitalId) {
        int length = hospitalId.length();
        if (length == 4) {
            return "STAFF";
        }
        if (length == 5) {
            return "PATIENTS";
        }
        return "unsuccessful"; // error handling if hospitalId is faulty
    }
}
