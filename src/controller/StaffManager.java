package src.controller;

import java.util.ArrayList;
import java.util.Comparator;

import src.database.Database;
import src.database.FileType;

// For javadocs
import src.helper.*;
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

    public static void createStaff(String name, Gender gender, int age, Role role) {
        int gid = Helper.generateUniqueId(Database.STAFF);
        String hospitalID = "";
        String pw = "password";
        switch (role) {
            case DOCTOR: {
                hospitalID = String.format("D%03d", gid);
                break;
            }
            case ADMINISTRATOR: {
                hospitalID = String.format("A%03d", gid);
                break;
            }
            case PHARMACIST: {
                hospitalID = String.format("P%03d", gid);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid role specified: " + role);
        }

        Staff newStaff = new Staff(hospitalID, pw, role, name, gender, age);

        Database.STAFF.put(hospitalID, newStaff);
        Database.saveFileIntoDatabase(FileType.STAFF);

        System.out.println("Staff Created! Staff Details: ");
        printStaffDetails(newStaff);
    }

    // updates the staff name
    public static boolean updateStaff(String hospitalID, int attributeCode, String newValue) {
        ArrayList<Staff> updateList = searchStaffById(hospitalID);
        if (updateList.isEmpty()) {
            // guest not found
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalID);
            switch (attributeCode) {
                case 1:
                    staffToUpdate.setName(newValue);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                default:
                    break;
            }
        }
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    // updates the staff age
    public static boolean updateStaff(String hospitalID, int attributeCode, int newValue) {
        ArrayList<Staff> updateList = searchStaffById(hospitalID);
        if (updateList.size() == 0) {
            // guest not found
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalID);
            switch (attributeCode) {
                case 2:
                    staffToUpdate.setAge(newValue);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                default:
                    break;
            }
        }
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    // updates the staff gender
    public static boolean updateStaff(String hospitalID, int attributeCode, Gender gender) {
        ArrayList<Staff> updateList = searchStaffById(hospitalID);
        if (updateList.size() == 0) {
            // guest not found
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalID);
            switch (attributeCode) {
                case 3:
                    staffToUpdate.setGender(gender);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                default:
                    break;
            }
        }
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    // updates the staff role
    public static boolean updateStaff(String hospitalID, int attributeCode, Role role) {
        ArrayList<Staff> updateList = searchStaffById(hospitalID);
        if (updateList.size() == 0) {
            // guest not found
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalID);
            switch (attributeCode) {
                case 4:
                    staffToUpdate.setRole(role);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                default:
                    break;
            }
        }
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    // remove staff from the database
    public static boolean removeStaff(String hospitalID) {
        ArrayList<Staff> removeList = searchStaffById(hospitalID);
        if (removeList.isEmpty()) {
            // guest not found
            return false;
        }
        for (Staff staff : removeList) {
            printStaffDetails(staff);
            if (Helper.promptConfirmation("remove this staff")) {
                Database.STAFF.remove(hospitalID);
            } else {
                return false;
            }
        }
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    // to view and filter staff according to role, gender and age
    // choice = 1 : filter by age
    // choice = 2 : filter by gender
    // cohice = 3 : filter by role
    public static void viewStaff(int choice) {
        ArrayList<Staff> viewList = new ArrayList<Staff>();
        boolean isInvalid = false;

        // Copy staff from Database to viewList
        viewList.addAll(Database.STAFF.values());

        switch (choice) {
            case 1: // Sort by age
                viewList.sort(Comparator.comparingInt(Staff::getAge));
                System.out.println("Staff sorted by age:");
                break;

            case 2: // Sort by gender
                viewList.sort(Comparator.comparing(Staff::getGender));
                System.out.println("Staff sorted by gender:");
                break;

            case 3: // Sort by role
                viewList.sort(Comparator.comparing(Staff::getRole));
                System.out.println("Staff sorted by role:");
                break;

            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Print the sorted list
        if (!isInvalid) {
            for (Staff staff : viewList) {
                printStaffDetails(staff);
            }
        }
    }

    public static void printAllStaff(boolean byId) {
        ArrayList<Staff> sortedList = new ArrayList<Staff>();

        // copy
        for (Staff staff : Database.STAFF.values()) {
            sortedList.add(staff);
        }
        // if (byId) {
        // for (int index = 1; index < sortedList.size(); index++) {
        // Staff currentStaff = sortedList.get(index);
        // int gid = Integer.parseInt(currentStaff.getId().substring(1));
        // int position = index;
        // while (position > 0 && gid < Integer.parseInt(sortedList.get(position -
        // 1).getId().substring(1))) {
        // sortedList.set(position, sortedList.get(position - 1));
        // position--;
        // }
        // sortedList.set(position, currentStaff);
        // }
        // } else {
        // // print by name
        // Collections.sort(sortedList, Comparator.comparing(Staff::getName));
        // }

        if (byId) {
            sortedList.sort(Comparator.comparingInt(staff -> Integer.parseInt(staff.getId().substring(1))));
        } else {
            sortedList.sort(Comparator.comparing(Staff::getName));
        }

        // print
        for (Staff staff : sortedList) {
            System.out.println(staff.getId() + " = " + staff);
        }
    }

    public static ArrayList<Staff> searchStaffById(String hospitalID) {
        ArrayList<Staff> searchList = new ArrayList<Staff>();
        if (Database.STAFF.containsKey(hospitalID)) {
            Staff searchedStaff = Database.STAFF.get(hospitalID);
            searchList.add(searchedStaff);
        }
        return searchList;
    }

    public static ArrayList<Staff> searchStaffByKeyWord(String keyword) {
        ArrayList<Staff> searchList = new ArrayList<Staff>();
        for (Staff staff : Database.STAFF.values()) {
            String currentStaffName = staff.getName().toLowerCase();
            if (currentStaffName.contains(keyword.toLowerCase())) {
                searchList.add(staff);
            }
        }
        return searchList;
    }

    public static void printStaffDetails(Staff staff) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Staff ID", staff.getId()));
        System.out.println(String.format("%-20s: %s", "Name", staff.getName()));
        System.out.println(String.format("%-20s: %s", "Gender", staff.getGender().genderAsStr));
        System.out.println(String.format("%-20s: %s", "Password", staff.getPassword()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    public static void printStaffDetails(String hospitalID) {
        Staff staff = Database.STAFF.get(hospitalID);
        if (staff != null) {
            printStaffDetails(staff); // Call the original method with the Staff object
        } else {
            System.out.println("Staff with ID " + hospitalID + " not found.");
        }
    }

    public static void initializeDummyStaff() {
        createStaff("Dr. Alice Smith", Gender.FEMALE, 35, Role.DOCTOR);
        createStaff("Dr. Bob Jones", Gender.MALE, 40, Role.DOCTOR);
        createStaff("Dr. Carol White", Gender.FEMALE, 45, Role.DOCTOR);

        createStaff("Admin John Brown", Gender.MALE, 50, Role.ADMINISTRATOR);
        createStaff("Admin Jane Doe", Gender.FEMALE, 32, Role.ADMINISTRATOR);

        createStaff("Pharm. Dave Green", Gender.MALE, 29, Role.PHARMACIST);
        createStaff("Pharm. Eve Black", Gender.FEMALE, 28, Role.PHARMACIST);
        createStaff("Pharm. Frank Lee", Gender.MALE, 33, Role.PHARMACIST);
    }

}