package src.controller;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import src.database.Database;
import src.model.User;
import src.model.enums.Role;

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
        if (Database.STAFF.containsKey(hospitalID) || Database.PATIENT.containsKey(hospitalID)) {
            System.out.println("The hospitalID can be found within the database");
            // checks if hospitalID is in STAFF database and continue
            if (Database.STAFF.containsKey(hospitalID)) {
                pw = Database.STAFF.get(password);
                if (pw == password) { // when input password is correct
                    // check for role then return role
                    return checkRoleAndReturn(hospitalID);
                }
                // checks if hospitalID is in PATIENT database and continue
            } else if (Database.PATIENT.containsKey(hospitalID)) {
                pw = Database.PATIENT.get(password);
                if (pw == password) { // when input password is correct
                    // check for role then return role
                    return checkRoleAndReturn(hospitalID);
                }
            }
        } else {
            System.out.println("The hospitalID cannot be found within the database");
            return "unsuccessful";
        }
    }

    public static ArrayList<User> searchUserById(String hospitalID) {
        ArrayList<User> searchList = new ArrayList<User>();
        if (Database.PATIENT.containsKey(hospitalID)) {
            User searchedGuest = Database.PATIENT.get(hospitalID);
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
                return "Doctor";
            case 'A':
                return "Administrator";
            case 'P':
                int length = hospitalId.length();
                if (length == 4) {
                    return "Pharmacist";
                } else if (length == 5) {
                    return "Patient";
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
        // breaks from function if hospitalId is invalid to prevent database error
        if (role == "unsucessful") {
            System.out.println("The hospitalId that you provided is invalid");
            break;
        }
        // user must know current password to change the password
        while (tries < 3) {
            System.out.println("Enter your current password: ");
            String attempt = scanner.nextLine();
            if (attempt == Database.role.get(password)) {
                break;
            }
            tries++;
        }

        while (item) {
            System.out.println("Enter the new password: ");
            pw = scanner.nextLine();
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

            // uppercase + lowercase + hasDigit + hasSymbol = 4
            if (valid == 4) {
                Database.role.put(hospitalId, pw);
                item = false;
            }
            // resets valid to zero before reentering while loop
            valid = 0;
        }
    }

    // function to find hospitalId's role
    public static String findRole(String hospitalId) {
        int length = hospitalId.length();
        if (length == 4) {
            return "STAFF";
        }
        if (length == 5) {
            return "PATIENT";
        }
        return "unsuccessful"; // error handling if hospitalId is faulty
    }
}
