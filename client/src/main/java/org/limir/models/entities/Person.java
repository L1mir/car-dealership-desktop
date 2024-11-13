package org.limir.models.entities;

import com.google.gson.annotations.Expose;
import org.limir.models.enums.Gender;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Employee> employees = new HashSet<>();

    @Expose
    private Set<User> users = new HashSet<>();

    public Person() {

    }

    public Person(Long person_id, String first_name, int age, String last_name,
                  Gender gender, Set<Employee> employees, Set<User> users) {
        this.person_id = person_id;
        this.first_name = first_name;
        this.age = age;
        this.last_name = last_name;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
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
