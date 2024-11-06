package org.limir.dataAccessObjects.daoImpl;

import org.limir.dataAccessObjects.PersonDao;
import org.limir.models.Person;

import java.util.List;

public class PersonDaoImpl implements PersonDao {
    @Override
    public boolean addPerson(Person person) {
        return false;
    }

    @Override
    public boolean updatePerson(Person person) {
        return false;
    }

    @Override
    public boolean deletePerson(int id) {
        return false;
    }

    @Override
    public List<Person> showPeople() {
        return List.of();
    }

    @Override
    public Person findPersonById(int id) {
        return null;
    }
}
