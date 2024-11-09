package src.controller;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import src.database.Database;
import src.database.FileType;

// For javadocs
import src.helper.*;
import src.view.*;
import src.model.Staff;
import src.model.enums.Gender;
import src.model.enums.Role;

/**
 * StaffManager is a controller class that acts as a "middleman"
 * allows the user to print staff info
 * allows the user manipulate staff info by updating, creating and removing
 * staff data
 * 
 * @author Benjamin Kam
 * @version 1.0
 * @since 2024-11-8
 */

public class StaffManager {
    // Default constructor for StaffManager
    public StaffManager() {
    }

    public static void createStaff(String name, Gender gender, int age, Role role, String password) {
        String gid = Helper.generateUniqueId(Database.STAFF);
        String staffId = "";
        String pw = password;
        switch (role) {
            case DOCTOR: {
                staffId = String.format("D%03d", gid);
                break;
            }
            case ADMINISTRATOR: {
                staffId = String.format("A%03d", gid);
                break;
            }
            case PHARMACIST: {
                staffId = String.format("P%03d", gid);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid role specified: " + role);
        }

        Staff newStaff = new Staff(gid, pw, role, name, gender, age);

        Database.STAFF.put(staffId, newStaff);
        Database.saveFileIntoDatabase(FileType.STAFF);

        System.out.println("Staff Created! Staff Details: ");
        printStaffDetails(newStaff);
    }

    public static void updateStaff(String staffId, int attributeCode) {
        ArrayList<Staff> updateList = searchStaffById(staffId);
        if (updateList.size() == 0) {
            // guest not found
            return false;
        }
    }

    public static void printStaffDetails(Staff staff) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Guest ID", staff.getId()));
        System.out.println(String.format("%-20s: %s", "Name", staff.getName()));
        System.out.println(String.format("%-20s: %s", "Gender", staff.getGender().genderAsStr));
        System.out.println(String.format("%-20: %s", "Password", staff.getPassword()));
        System.out.println(String.format("%-20s: %s", "Role", staff.getRole()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }
}