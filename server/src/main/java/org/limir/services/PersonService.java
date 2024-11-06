package org.limir.services;

import org.limir.models.Person;

import java.util.List;

public interface PersonService {
    boolean addPerson(Person person);
    boolean updatePerson(Person person);
    boolean deletePerson(int id);
    List<Person> showPeople();
    Person findPersonById(int id);
}
