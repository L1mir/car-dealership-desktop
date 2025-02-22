package org.limir.dataAccessObjects;

import org.limir.models.entities.Person;

import java.util.List;

public interface PersonDao {
    boolean addPerson(Person person);

    boolean updatePerson(Person person);

    boolean deletePerson(int id);

    List<Person> showPeople();

    Person findPersonById(int id);

    Person findPersonByName(String name);
}
