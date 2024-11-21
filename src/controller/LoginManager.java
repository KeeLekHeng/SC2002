package src.controller;

import java.util.Scanner;
import java.util.ArrayList;
import src.database.Database;
import src.database.FileType;
import src.helper.Helper;
import src.model.Patient;
import src.model.Staff;
import src.model.User;

/**
 * LoginManager allows the user to log in to the hospital system.
 * Users are required to input their hospital ID and password.
 * The login credentials are validated against the database,
 * and the user will be granted role-specific access to the system.
 * Users can also change their password if necessary, subject to specific
 * criteria.
 * 
 * @author Benjamin Kam, Kee
 * @version 1.0
 * @since 2024-11-20
 */
public class LoginManager {
    static Scanner scanner = new Scanner(System.in);

    public LoginManager() {
    }

    /**
     * Logs in a user based on the provided hospital ID and password.
     * 
     * @param hospitalID the ID of the user
     * @param password   the password of the user
     * @return the role of the user if login is successful, or "unsuccessful" if
     *         credentials are incorrect
     */
    public static String LoginUser(String hospitalID, String password) {
        String pw;

        if (Database.STAFF.containsKey(hospitalID) || Database.PATIENTS.containsKey(hospitalID)) {
            if (Database.STAFF.containsKey(hospitalID)) {
                Staff staff = Database.STAFF.get(hospitalID);
                pw = staff.getPassword();
                if (password.equals(pw)) {
                    String item = checkRoleAndReturn(hospitalID);
                    return item;
                }
            } else if (Database.PATIENTS.containsKey(hospitalID)) {
                Patient patient = Database.PATIENTS.get(hospitalID);
                pw = patient.getPassword();
                if (password.equals(pw)) {
                    String item = checkRoleAndReturn(hospitalID);
                    return item;
                }
            }
        } else {
            return "unsuccessful";
        }
        return "unsuccessful";
    }

    /**
     * Searches for a user by hospital ID.
     * 
     * @param hospitalID the ID of the user to search for
     * @return a list of users matching the provided hospital ID
     */
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

    /**
     * Checks the role of the user based on their hospital ID.
     * 
     * @param hospitalId the hospital ID to check the role for
     * @return the role of the user (doctor, admin, pharmacist, patient, or
     *         unsuccessful)
     */
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

    /**
     * Allows a user to create a new password for their account.
     * The new password must meet specific criteria such as length,
     * containing lowercase, uppercase letters, digits, and special characters.
     * 
     * @param hospitalId the ID of the user changing their password
     */
    public static void createNewPassword(String hospitalId) {
        String pw;
        boolean item = true;
        int valid = 0;
        int lowercase = 0;
        int uppercase = 0;
        int hasDigit = 0;
        int symbolCount = 0;
        String role = findRole(hospitalId);

        int tries = 0;
        if (!(role == "unsucessful")) {
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
                System.out.println("Incorrect password provided. " + (3 - tries) + " tries left.");
            }
            if (tries != 3) {
                tries = 0;
                while (item && tries < 5) {
                    System.out.println(String.format("%-40s", "").replace(" ", "-"));
                    if (tries == 0) {
                        System.out.println("Enter the new password: ");
                    } else {
                        System.out.println("Invalid password." + (5 - tries) + " tries left. Enter new password: ");
                    }
                    System.out.println(
                            "Password should contain\n- at least 10 characters long\n- at least 1 lowercase and 1 uppercase character\n- at least 1 number\n- at least 2 special characters");
                    pw = scanner.nextLine();

                    valid = 0;
                    lowercase = 0;
                    uppercase = 0;
                    hasDigit = 0;
                    symbolCount = 0;
                    if (pw.length() > 10) {
                        valid++;
                    }
                    for (char ch : pw.toCharArray()) {
                        if (lowercase < 1 && Character.isLowerCase(ch)) {
                            lowercase++;
                            valid++;
                        } else if (uppercase < 1 && Character.isUpperCase(ch)) {
                            uppercase++;
                            valid++;
                        } else if (hasDigit < 1 && Character.isDigit(ch)) {
                            hasDigit++;
                            valid++;
                        } else if (!Character.isLetterOrDigit(ch)) {
                            symbolCount++;
                        }
                    }
                    if (symbolCount >= 2) {
                        valid++;
                    }
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
                        System.out.println("Password updated successfully. Returning..");
                        item = false;
                        Database.saveFileIntoDatabase(FileType.STAFF);
                    }
                    tries++;

                    if (tries == 5) {
                        System.out.println("Too many attempts. Returning..");
                        Helper.pressAnyKeyToContinue();
                    }
                }
            } else {
                System.out.println("Too many attempts. Returning..");
                Helper.pressAnyKeyToContinue();
            }

        } else {
            System.out.println("The hospitalId that you provided is invalid. Returning..");
            Helper.pressAnyKeyToContinue();
        }
    }

    /**
     * Finds the role of a user based on their hospital ID length.
     * 
     * @param hospitalId the ID of the user
     * @return the role of the user (STAFF, PATIENTS, or unsuccessful)
     */
    public static String findRole(String hospitalId) {
        int length = hospitalId.length();
        if (length == 4) {
            return "STAFF";
        }
        if (length == 5) {
            return "PATIENTS";
        }
        return "unsuccessful";
    }
}
