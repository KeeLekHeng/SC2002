package src.model;

import src.model.enums.Gender;

/**
 * Represents a staff member in the system, extending from User. 
 * A staff member has additional attributes such as name, age, and gender.
 * 
 * @author JiaWei
 * @version 1.0
 * @since 2024-11-13
 */
public class Staff extends User {

    /** Name of the staff member. */
    public String name;

    /** Age of the staff member. */
    public int age;

    /** Gender of the staff member. */
    public final Gender gender;

    /**
     * Constructs a new Staff with the specified attributes.
     * 
     * @param id     Unique identifier of the staff member.
     * @param password Password for the staff member.
     * @param role   Role of the staff member in the system.
     * @param name   Name of the staff member.
     * @param gender Gender of the staff member.
     * @param age    Age of the staff member.
     */
    public Staff(String id, String password, Role role, String name, Gender gender, int age) {
        super(id, password, role);
        this.name = name;
        this.gender = gender;
        this.age = age;
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
}
