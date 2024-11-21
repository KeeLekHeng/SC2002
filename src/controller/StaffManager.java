package src.controller;

import java.util.ArrayList;
import java.util.Comparator;
import src.database.Database;
import src.database.FileType;
import src.helper.*;
import src.model.Staff;
import src.model.enums.Gender;
import src.model.enums.Role;

/**
 * StaffManager is a controller class that acts as a "middleman" allows the user to print staff info allows the user manipulate staff info by updating, creating and removing staff data
 * @author Benjamin, Kee
 * @version 1.0
 * @since 2024-11-20
 */
public class StaffManager {
    public StaffManager() {
    }

    /**
     * Creates a new staff entry and adds it to the database.
     * @param name The name of the staff.
     * @param gender The gender of the staff.
     * @param age The age of the staff.
     * @param role The role of the staff (e.g., DOCTOR, ADMINISTRATOR, PHARMACIST).
     */
    public static void createStaff(String name, Gender gender, int age, Role role) {
        int gid = Helper.generateUniqueId(Database.STAFF);
        String employmentStatus = "EMPLOYED";
        String hospitalId = "";
        String pw = "password";
        int rating = 0;
        int ratingCount = 0;
        switch (role) {
            case DOCTOR: {
                hospitalId = String.format("D%03d", gid);
                break;
            }
            case ADMINISTRATOR: {
                hospitalId = String.format("A%03d", gid);
                break;
            }
            case PHARMACIST: {
                hospitalId = String.format("P%03d", gid);
                break;
            }
            default:
                throw new IllegalArgumentException("Invalid role specified: " + role);
        }

        Staff newStaff = new Staff(hospitalId, pw, role, name, gender, age, employmentStatus, rating, ratingCount);

        Database.STAFF.put(hospitalId, newStaff);
        Database.saveFileIntoDatabase(FileType.STAFF);

        System.out.println("Staff Created! Staff Details: ");
        printStaffDetails(newStaff);
    }

    /**
     * Updates a staff attribute by the provided hospital ID and attribute code.
     * @param hospitalId The hospital ID of the staff to update.
     * @param attributeCode The attribute code to identify which attribute to update.
     * @param newValue The new value for the attribute.
     * @return {@code true} if the update was successful, otherwise {@code false}.
     */
    public static boolean updateStaff(String hospitalId, int attributeCode, String newValue) {
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.isEmpty()) {
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalId);
            switch (attributeCode) {
                case 1:
                    staffToUpdate.setName(newValue);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                case 2:
                    staffToUpdate.setEmploymentStatus(newValue);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                default:
                    break;
            }
        }
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    /**
     * Updates the staff age by the provided hospital ID and attribute code.
     * @param hospitalId The hospital ID of the staff to update.
     * @param attributeCode The attribute code to identify which attribute to update.
     * @param newValue The new age value for the staff.
     * @return {@code true} if the update was successful, otherwise {@code false}.
     */
    public static boolean updateStaff(String hospitalId, int attributeCode, int newValue) {
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.size() == 0) {
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalId);
            switch (attributeCode) {
                case 3:
                    staffToUpdate.setAge(newValue);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                    break;
                case 100:
                    int ratingCount = staffToUpdate.getRatingCount();
                    double previousRating = staffToUpdate.getRating() * ratingCount;
                    staffToUpdate.incrementRatingCount();
                    double newRating = (previousRating + newValue) / staffToUpdate.getRatingCount();
                    staffToUpdate.setRating(newRating);
                    Database.STAFF.put(staff.getId(), staffToUpdate);
                default:
                    break;
            }
        }

        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    /**
     * Updates the staff gender by the provided hospital ID and attribute code.
     * @param hospitalId The hospital ID of the staff to update.
     * @param attributeCode The attribute code to identify which attribute to update.
     * @param gender The new gender value for the staff.
     * @return {@code true} if the update was successful, otherwise {@code false}.
     */
    public static boolean updateStaff(String hospitalId, int attributeCode, Gender gender) {
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.size() == 0) {
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalId);
            switch (attributeCode) {
                case 4:
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

    /**
     * Updates the staff role by the provided hospital ID and attribute code.
     * @param hospitalId The hospital ID of the staff to update.
     * @param attributeCode The attribute code to identify which attribute to update.
     * @param role The new role value for the staff.
     * @return {@code true} if the update was successful, otherwise {@code false}.
     */
    public static boolean updateStaff(String hospitalId, int attributeCode, Role role) {
        ArrayList<Staff> updateList = searchStaffById(hospitalId);
        if (updateList.size() == 0) {
            return false;
        }
        for (Staff staff : updateList) {
            Staff staffToUpdate = Database.STAFF.get(hospitalId);
            switch (attributeCode) {
                case 5:
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

    /**
     * Removes the staff by the provided hospital ID and updates their employment status.
     * @param hospitalId The hospital ID of the staff to remove.
     * @return {@code true} if the staff was successfully removed, otherwise {@code false}.
     */
    public static boolean removeStaff(String hospitalId) {
        ArrayList<Staff> removeList = searchStaffById(hospitalId);
        if (removeList.isEmpty()) {
            return false;
        }
        for (Staff staff : removeList) {
            printStaffDetails(staff);
            if (Helper.promptConfirmation("remove this staff")) {
                staff.setEmploymentStatus("REMOVED");
            } else {
                return false;
            }
        }
        Database.STAFF.remove(hospitalId);
        Database.saveFileIntoDatabase(FileType.STAFF);
        return true;
    }

    /**
     * Displays the staff sorted by the selected attribute.
     * @param choice The choice of sorting: 1 for sorting by age, 2 for sorting by gender, 3 for sorting by role.
     */
    public static void viewStaff(int choice) {
        ArrayList<Staff> viewList = new ArrayList<Staff>();
        boolean isInvalid = false;

        viewList.addAll(Database.STAFF.values());

        switch (choice) {
            case 1:
                viewList.sort(Comparator.comparingInt(staff -> Integer.parseInt(staff.getId().substring(1))));
                System.out.println("Staff sorted by ID:");
                break;
            case 2:
                viewList.sort(Comparator.comparing(Staff::getName));
                System.out.println("Staff sorted by name:");
                break;
            case 3: 
                viewList.sort(Comparator.comparingInt(Staff::getAge));
                System.out.println("Staff sorted by age:");
                break;

            case 4: 
                viewList.sort(Comparator.comparing(Staff::getGender));
                System.out.println("Staff sorted by gender:");
                break;

            case 5: 
                viewList.sort(Comparator.comparing(Staff::getRole));
                System.out.println("Staff sorted by role:");
                break;
            case 6: 
                viewList.sort(Comparator.comparing(Staff::getEmploymentStatus));
                System.out.println("Staff sorted by employment status:");
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        if (!isInvalid) {
            for (Staff staff : viewList) {
                printStaffDetails(staff);
            }
        }
    }

    /**
     * Prints all staff information, sorted by ID or name.
     * @param byId If {@code true}, staff are sorted by ID; otherwise, they are sorted by name.
     */
    public static void printAllStaff(boolean byId) {
        ArrayList<Staff> sortedList = new ArrayList<Staff>();
        for (Staff staff : Database.STAFF.values()) {
            sortedList.add(staff);
        }

        if (sortedList.isEmpty()) {
            System.out.println("There is no staff in database");
            return;
        }
        if (byId) {
            sortedList.sort(Comparator.comparingInt(staff -> Integer.parseInt(staff.getId().substring(1))));
        } else {
            sortedList.sort(Comparator.comparing(Staff::getName));
        }
        for (Staff staff : sortedList) {
            System.out.println(staff.getId() + " = " + staff);
        }
    }

    /**
     * Searches for staff by their hospital ID.
     * @param hospitalId The hospital ID of the staff to be searched.
     * @return A list containing the staff found, or an empty list if not found.
     */
    public static ArrayList<Staff> searchStaffById(String hospitalId) {
        ArrayList<Staff> searchList = new ArrayList<Staff>();
        if (Database.STAFF.containsKey(hospitalId)) {
            Staff searchedStaff = Database.STAFF.get(hospitalId);
            searchList.add(searchedStaff);
        }
        return searchList;
    }

    /**
     * Searches for staff by a keyword in their name.
     * @param keyword The keyword to search for in the staff names.
     * @return A list of staff whose names contain the keyword (case-insensitive).
     */
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

    /**
     * Prints the details of a staff member.
     * @param staff The staff member whose details are to be printed.
     */
    public static void printStaffDetails(Staff staff) {
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
        System.out.println(String.format("%-20s: %s", "Guest ID", staff.getId()));
        System.out.println(String.format("%-20s: %s", "Name", staff.getName()));
        System.out.println(String.format("%-20s: %s", "Gender", staff.getGender().genderAsStr));
        System.out.println(String.format("%-20s: %s", "Password", staff.getPassword()));
        System.out.println(String.format("%-20s: %s", "EmploymentStatus", staff.getEmploymentStatus()));
        System.out.println(String.format("%-40s", "").replace(" ", "-"));
    }

    /**
     * Prints the details of a staff member by their hospital ID.
     * @param hospitalId The hospital ID of the staff to be printed.
     */
    public static void printStaffDetails(String hospitalId) {
        Staff staff = Database.STAFF.get(hospitalId);
        if (staff != null) {
            printStaffDetails(staff); // Call the original method with the Staff object
        } else {
            System.out.println("Staff with ID " + hospitalId + " not found.");
        }
    }

    /**
     * Creates and adds dummy staff members to the database.
     */
    public static void createDummyStaff() {
        createStaff("Dr. Alice Smith", Gender.FEMALE, 35, Role.DOCTOR);
        createStaff("Dr. Carol White", Gender.FEMALE, 45, Role.DOCTOR);
        createStaff("Admin John Brown", Gender.MALE, 50, Role.ADMINISTRATOR);
        createStaff("Pharm. Dave Green", Gender.MALE, 29, Role.PHARMACIST);
        createStaff("Pharm. Frank Lee", Gender.MALE, 33, Role.PHARMACIST);
    }

    /**
     * Creates and adds a starting admin staff member to the database.
     */
    public static void createStartingAdmin() {
        createStaff("Admin", Gender.MALE, 50, Role.ADMINISTRATOR);
    }
}