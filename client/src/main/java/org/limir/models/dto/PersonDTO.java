package org.limir.models.dto;

import org.limir.models.entities.Person;
import org.limir.models.enums.Gender;

public class PersonDTO {
    private Long person_id;
    private String first_name;
    private String last_name;
    private int age;
    private Gender gender;

    PersonDTO(Person person) {
        this.person_id = person.getPerson_id();
        this.first_name = person.getFirst_name();
        this.last_name = person.getLast_name();
        this.age = person.getAge();
        this.gender = person.getGender();
    }

    public Person toPerson() {
        Person person = new Person();
        person.setPerson_id(this.person_id);
        person.setFirst_name(this.first_name);
        person.setLast_name(this.last_name);
        person.setAge(this.age);
        person.setGender(this.gender);
        return person;
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
}
