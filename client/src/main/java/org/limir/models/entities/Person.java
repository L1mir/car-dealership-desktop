package org.limir.models.entities;

import com.google.gson.annotations.Expose;
import org.limir.models.enums.Gender;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Person implements Serializable {
    @Expose
    private Long person_id;

    @Expose
    private String first_name;

    @Expose
    private String last_name;

    @Expose
    private int age;

    @Expose
    private Gender gender;

    @Expose
    private List<Employee> employees;

    @Expose
    private List<User> users;

    public Person() {
        this.users = new ArrayList<>();
        this.employees = new ArrayList<>();
    }

    public Person(Long person_id, String first_name, String last_name,
                  int age, Gender gender, List<Employee> employees, List<User> users) {
        this.person_id = person_id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.gender = gender;
        this.employees = employees;
        this.users = users;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setUserData(User user) {
        this.users.add(user);
    }

    @Override
    public String toString() {
        return "Person{" +
                "person_id=" + person_id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", age=" + age +
                ", gender=" + gender +
                ", employees=" + employees +
                ", users=" + users +
                '}';
    }
}
