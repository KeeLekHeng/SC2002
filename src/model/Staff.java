package src.model;

import src.model.enums.Role;
import src.model.enums.Gender;

/**
 * Represents a staff member in the system, extending from User.
 * A staff member has additional attributes such as name, age, and gender.
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class Staff extends User {

    /* Rating count of the staff member */
    public int ratingCount;

    /* Rating of the staff member */
    public double rating;

    /** Name of the staff member. */
    public String name;

    /** Age of the staff member. */
    public int age;

    /** Gender of the staff member. */
    public Gender gender;

    public String EmploymentStatus;

    /**
     * Constructs a new Staff with the specified attributes.
     * @param id Unique identifier of the staff member.
     * @param password Password for the staff member.
     * @param role Role of the staff member in the system.
     * @param name Name of the staff member.
     * @param gender Gender of the staff member.
     * @param age Age of the staff member.
     * @param rating Rating of the staff member.
     * @param ratingCount Rating count of the staff member.
     * @param EmploymentStatus Employment status of the staff member.
     */
    public Staff(String id, String password, Role role, String name, Gender gender, int age, String EmploymentStatus,
            double rating, int ratingCount) {
        super(id, password, role);
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.EmploymentStatus = EmploymentStatus;
        this.rating = 0;
        this.ratingCount = 0;
    }

    /**
     * Gets the rating count of the staff member.
     * @return the rating count of the staff member.
     */
    public int getRatingCount() {
        return this.ratingCount;
    }

    /**
     * Resets the rating count of the staff member.
     * @param ratingCount New rating count for the staff member.
     */
    public void resetRatingCount() {
        this.ratingCount = 0;
    }

    /**
     * Increments the rating count of the staff member.
     * @param ratingCount Increments rating count for the staff member.
     */
    public void incrementRatingCount() {
        this.ratingCount++;
    }

    /**
     * Gets the rating of the staff member.
     * @return the rating of the staff member.
     */
    public double getRating() {
        return this.rating;
    }

    /**
     * Sets the rating of the staff member.
     * @param rating New rating for the staff member.
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Gets the name of the staff member.
     * @return the name of the staff member.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the staff member.
     * @param name New name for the staff member.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the age of the staff member.
     * @return the age of the staff member.
     */
    public int getAge() {
        return this.age;
    }

    /**
     * Sets the age of the staff member.
     * @param age New age for the staff member.
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets the gender of the staff member.
     * @return the gender of the staff member.
     */
    public Gender getGender() {
        return this.gender;
    }

    /**
     * Sets the gender of the staff member.
     * @param gender New gender for the staff member.
     */
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    /**
     * Sets the role of the staff member.
     * @param role New role for the staff member.
     */
    public void setRole(Role role) {
        this.role = role;
    }

    /**
     * Sets the employment status of the staff member.
     * @param status New employment status for the staff member.
     */
    public void setEmploymentStatus(String status) {
        this.EmploymentStatus = status;
    }

    /**
     * Gets the employment status of the staff member.
     * @return the employment status of the staff member.
     */
    public String getEmploymentStatus() {
        return this.EmploymentStatus;
    }
}