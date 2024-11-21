package src.helper;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Helper class for supporting functions.
 * 
 * @author Kee, Seann
 * @version 1.0
 * @since 2024/10/22
 */
public class Helper {
    /**
     * Scanner object for user input.
     */
    public static final Scanner sc = new Scanner(System.in);

    /**
     * Initializes the Scanner object.
     */
    public Helper() {
    }

    /**
     * Reads an integer value from the terminal.
     * 
     * @return The entered integer.
     */
    public static int readInt() {
        while (true) {
            try {
                int userInput = -1;
                userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input, Enter an integer!");
            }
        }
    }

    /*
     * Function to validate email provided
     * 
     * It checks whether the the email provided meets the email valid format
     * If the email is invalid, then false will be returned
     * Otherwise, true will be returned for a valid email
     */
    public static boolean EmailValidator(String email) {
        // Define the regex pattern for a valid email address
        final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        // Compile the regex and match the email
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        boolean isValid = email != null && pattern.matcher(email).matches();

        // Return the result
        if (isValid) {
            return true;
        } else {
            return false;
        }
    }

    /*
     * Function to validate phone number provided
     * 
     * It checks whether the phone number provided meets the accepted criteria
     * If the phone number is invalid, then false will be returned
     * Otherwise, true will be returned for a valid phone number
     */
    public static boolean PhoneNumValidator(String phoneNumber) {
        if (phoneNumber.length() != 10) {
            return false;
        } else if (phoneNumber.charAt(0) != '6' && phoneNumber.charAt(1) != '5') {
            return false;
        }
        return true;
    }

    /**
     * Reads an integer value within a specified range.
     * 
     * @param min minimum valid value.
     * @param max maximum valid value.
     * @return The entered integer.
     */
    public static int readInt(int min, int max) {
        while (true) {
            try {
                int userInput = -1;
                userInput = sc.nextInt();
                sc.nextLine(); // Consume newline left-over
                if (userInput < min || userInput > max) {
                    throw new OutOfRange();
                } else {
                    return userInput;
                }
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input, Enter an integer!");
            } catch (OutOfRange e) {
                System.out.println("Input is out of allowed range");
            }
        }
    }

    /**
     * Reads a double value from the terminal.
     * 
     * @return The entered double.
     */
    public static double readDouble() {
        while (true) {
            try {
                double userInput = -1;
                userInput = sc.nextDouble();
                sc.nextLine(); // Consume newline left-over
                return userInput;
            } catch (InputMismatchException e) {
                sc.nextLine();
                System.out.println("Invalid Input, Enter a double!");
            }
        }
    }

    /**
     * Reads a new line of string input.
     * 
     * @return The entered string.
     */
    public static String readString() {
        return sc.nextLine();
    }

    /**
     * <<<<<<< HEAD
     * Reads a valid Appointment ID in the format "AXXXXX" or allows the user to go
     * back.
     * 
     * @return A valid appointment ID or null if the user enters "back".
     *         =======
     *         Reads a valid Appointment ID (Format: AXXXXX) or allows the user to
     *         go back.
     * @return A valid appointment ID or an empty string if the user enters "back".
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static String readAppointmentID() {
        String appointmentID;
        while (true) {
            System.out.print("Enter Appointment ID (Format: AXXXXX) or type 'back' to go back: ");
            appointmentID = sc.nextLine().trim();
            if (appointmentID.equalsIgnoreCase("back")) {
                return "";
            }
            if (appointmentID.matches("A\\d{5}")) {
                return appointmentID;
            } else {
                System.out.println("Invalid format! Please try again.");
            }
        }
    }

    /**
     * <<<<<<< HEAD
     * Reads a valid Patient ID in the format "PXXXX" or allows the user to go back.
     * 
     * @return A valid patient ID or null if the user enters "back".
     *         =======
     *         Reads a valid Patient ID (Format: PXXXX) or allows the user to go
     *         back.
     * @return A valid patient ID or an empty string if the user enters "back".
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static String readPatientID() {
        String patientID;
        while (true) {
            System.out.print("Enter Patient ID (Format: PXXXX) or type 'back' to go back: ");
            patientID = sc.nextLine().trim();
            if (patientID.equalsIgnoreCase("back")) {
                return "";
            }
            if (patientID.matches("P\\d{4}")) {
                return patientID;
            } else {
                System.out.println("Invalid format! Please try again.");
            }
        }
    }

    /**
     * <<<<<<< HEAD
     * Reads a valid Pharmacist, Doctor, or Admin ID in the format "PXXX", "DXXX",
     * or "AXXX" or allows the user to go back.
     * 
     * @return A valid staff ID or null if the user enters "back".
     *         =======
     *         Reads a valid Staff ID (Formats: PXXX, DXXX, AXXX) or allows the user
     *         to go back.
     * @return A valid staff ID or an empty string if the user enters "back".
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static String readStaffID() {
        String staffID;
        while (true) {
            staffID = sc.nextLine().trim();
            if (staffID.equalsIgnoreCase("back")) {
                return "";
            }
            if (staffID.matches("[PDA]\\d{3}")) {
                return staffID;
            } else {
                System.out.println("Invalid format! Please try again.");
            }
        }
    }

    /**
     * <<<<<<< HEAD
     * Reads a valid Prescription ID in the format "PXXXXX" or allows the user to go
     * back.
     * 
     * @return A valid prescription ID or null if the user enters "back".
     *         =======
     *         Reads a valid Prescription ID (Format: PXXXXX) or allows the user to
     *         go back.
     * @return A valid prescription ID or an empty string if the user enters "back".
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static String readPrescriptionID() {
        String prescriptionID;
        while (true) {
            System.out.print("Enter Prescription ID (Format: PXXXXX) or type 'back' to go back: ");
            prescriptionID = sc.nextLine().trim();
            if (prescriptionID.equalsIgnoreCase("back")) {
                return "";
            }
            if (prescriptionID.matches("P\\d{5}")) {
                return prescriptionID;
            } else {
                System.out.println("Invalid format! Please try again.");
            }
        }
    }

    /**
     * <<<<<<< HEAD
     * Reads a valid Request ID in the format "RXXXX" or allows the user to go back.
     * 
     * @return A valid request ID or null if the user enters "back".
     *         =======
     *         Reads a valid Request ID (Format: RXXXX) or allows the user to go
     *         back.
     * @return A valid request ID or an empty string if the user enters "back".
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static String readRequestID() {
        String requestID;
        while (true) {
            System.out.print("Enter Request ID (Format: RXXXX) or type 'back' to go back: ");
            requestID = sc.nextLine().trim();
            if (requestID.equalsIgnoreCase("back")) {
                return "";
            }
            if (requestID.matches("R\\d{4}")) {
                return requestID;
            } else {
                System.out.println("Invalid format! Please try again.");
            }
        }
    }

    /**
     * <<<<<<< HEAD
     * Reads a valid Medicine ID in the format "MXXXX" or allows the user to go
     * back.
     * 
     * @return A valid medicine ID or null if the user enters "back".
     *         =======
     *         Reads a valid Medicine ID (Format: MXXXX) or allows the user to go
     *         back.
     * @return A valid medicine ID or an empty string if the user enters "back".
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static String readMedicineID() {
        String medicineID;
        while (true) {
            System.out.print("Enter Medicine ID (Format: MXXXX) or type 'back' to go back: ");
            medicineID = sc.nextLine().trim();
            if (medicineID.equalsIgnoreCase("back")) {
                return "";
            }
            if (medicineID.matches("M\\d{4}")) {
                return medicineID;
            } else {
                System.out.println("Invalid format! Please try again.");
            }
        }
    }

    /**
     * Prompts the user for confirmation, typically used for confirming data
     * removal.
     * 
     * @param message The confirmation message to display.
     * @return {@code true} if the user inputs 'yes', otherwise {@code false}.
     */
    public static boolean promptConfirmation(String message) {
        System.out.println(String.format("Are you sure you want to %s? (yes/no)", message));
        String userInput = sc.nextLine();
        return userInput.equals("yes");
    }

    /**
     * Generates a unique ID for a HashMap key.
     * 
     * @param <K>      The key type of the HashMap.
     * @param <V>      The value type of the HashMap.
     * @param database The HashMap to generate the ID for.
     * @return A unique ID for the database.
     */
    public static <K, V> int generateUniqueId(HashMap<K, V> database) {
        if (database.size() == 0) {
            return 1;
        }
        String currentMax = "";
        for (K key : database.keySet()) {
            if (key instanceof String) {
                String currentKey = (String) key;
                if (currentKey.compareTo(currentMax) > 0) {
                    currentMax = currentKey;
                }
            }
        }
        String maxId = currentMax.substring(1);
        return Integer.parseInt(maxId) + 1;
    }

    /**
     * Sets the date to either the current date or a user-input date.
     * 
     * @param now {@code true} to return the current time, otherwise {@code false}
     *            to prompt the user for a new time.
     * @return Date in the format "yyyy-MM-dd HH:mm".
     */
    public static String setDate(boolean now) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        if (now) {
            return getTimeNow();
        }
        System.out.println("Please enter the date in this format: 'yyyy-MM-dd HH:mm'");
        String date = sc.nextLine();
        try {
            LocalDateTime Date = LocalDateTime.parse(date, format);
            date = format.format(Date);
            if (validateDate(date, format)) {
                return date;
            } else {
                System.out.println("Invalid Date");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date format");
        }
        return "";
    }

    /**
     * Sets the date to either the current date or a user-input date without time.
     * 
     * @return Date in the format "yyyy-MM-dd".
     */
    public static String setDateOnly() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.println("Please enter the date in this format: 'yyyy-MM-dd'");
        String dateInput = sc.nextLine();

        try {
            LocalDate date = LocalDate.parse(dateInput, format);
            if (validateDate(date)) {
                return date.format(format);
            } else {
                System.out.println("Invalid Date");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Invalid Date format. Please use 'yyyy-MM-dd'.");
        }

        return "";
    }

    /**
     * Validates if the date is in the future.
     * 
     * @param date Date to validate.
     * @return {@code true} if the date is in the future, otherwise {@code false}.
     */
    public static boolean validateDate(LocalDate date) {
        return date.isAfter(LocalDate.now()); // Example validation: only allow future dates
    }

    /**
     * Parses a string date into a {@link LocalDateTime} object.
     * 
     * @param date   Date in string format.
     * @param format {@link DateTimeFormatter} for formatting the date.
     * @return {@link LocalDateTime} object after parsing.
     */
    public static LocalDateTime getDate(String date, DateTimeFormatter format) {
        return LocalDateTime.parse(date, format);
    }

    /**
     * Parses a string date into a {@link LocalDate} object.
     * 
     * @param date   Date in string format.
     * @param format {@link DateTimeFormatter} for formatting the date.
     * @return {@link LocalDate} object after parsing.
     */
    public static LocalDate getDateOnly(String date, DateTimeFormatter format) {
        return LocalDate.parse(date, format);
    }

    /**
     * Gets the current date and time.
     * 
     * @return Current date and time in the format "yyyy-MM-dd HH:mm".
     */
    public static String getTimeNow() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime date = LocalDateTime.now();
        return date.format(format);
    }

    /**
     * Validates if the given date is in the future.
     * 
     * @param date   Date in string format.
     * @param format {@link DateTimeFormatter} for formatting the date.
     * @return {@code true} if the date is valid (in the future), otherwise
     *         {@code false}.
     */
    public static boolean validateDate(String date, DateTimeFormatter format) {
        LocalDateTime Date = getDate(date, format);
        LocalDateTime now = LocalDateTime.now();
        return (Date.compareTo(now) >= 0);
    }

    /**
     * <<<<<<< HEAD
     * MAYBE CAN THROW AWAY
     * Method to check if the time difference of the input date and current time
     * exceeds 1 hour (Hotel check in / check out checking)
     * 
     * @param date Date in string
     * @return {@code true} if the date does not exceed 1 hour. Otherwise,
     *         {@code false}.
     *         =======
     *         Checks if the time difference between the given date and current time
     *         exceeds 1 hour.
     * @param date Date in string format.
     * @return {@code true} if the time difference exceeds 1 hour, otherwise
     *         {@code false}.
     *         >>>>>>> a9fc1015e9899781f542b932d369f9d5f7111d64
     */
    public static boolean LocalDateTimediff(String date) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = getDate(date, format);
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime fromTemp = LocalDateTime.from(from);

        long hours = fromTemp.until(to, ChronoUnit.HOURS);
        fromTemp = fromTemp.plusHours(hours);

        long minutes = fromTemp.until(to, ChronoUnit.MINUTES);
        fromTemp = fromTemp.plusMinutes(minutes);

        return hours > 1;
    }

    /**
     * Calculates the days elapsed between two dates.
     * 
     * @param fromDate From date in string format.
     * @param toDate   To date in string format.
     * @return Days difference between the two dates.
     */
    public static long calculateDaysElapsed(String fromDate, String toDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = getDate(fromDate, format);
        LocalDateTime to = getDate(toDate, format);
        long daysBetween = from.until(to, ChronoUnit.DAYS);
        return daysBetween + 1;
    }

    /**
     * Checks if fromDate is earlier than toDate.
     * 
     * @param fromDate From date in string format.
     * @param toDate   To date in string format.
     * @return {@code true} if fromDate is earlier than toDate, otherwise
     *         {@code false}.
     */
    public static boolean validateTwoDates(String fromDate, String toDate) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime from = getDate(fromDate, format);
        LocalDateTime to = getDate(toDate, format);
        return (to.compareTo(from) >= 0);
    }

    /**
     * Checks if the date is a weekend.
     * 
     * @param dateToCheck Date to check in string format.
     * @return {@code true} if the date is a weekend, otherwise {@code false}.
     */
    public static boolean checkIsDateWeekend(String dateToCheck) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = getDate(dateToCheck, format);
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * Pauses the application and prompts the user to press Enter to continue.
     */
    public static void pressAnyKeyToContinue() {
        System.out.println("Press Enter key to continue...");
        try {
            System.in.read();
        } catch (Exception e) {
        } finally {
        }
    }

    /**
     * Clears the terminal screen for better user experience.
     */
    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
}
