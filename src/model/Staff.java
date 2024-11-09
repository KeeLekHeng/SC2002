package src.model;

import src.model.enums.Role;
import src.model.enums.Gender;

public class Staff extends User {
    public String name;
    public int age;
    public final Gender gender;

    public Staff(String id, String password, Role role, String name, Gender gender, int age) {
        super(id, password, role);
        this.name = name;
        this.gender = gender;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return this.gender;
    }

}