package org.limir.services.servicesImpl;

import org.hibernate.HibernateError;
import org.limir.dataAccessObjects.PersonDao;
import org.limir.dataAccessObjects.daoImpl.PersonDaoImpl;
import org.limir.models.entities.Person;
import org.limir.services.PersonService;

import java.util.List;

public class PersonServiceImpl implements PersonService {
    PersonDao personDao = new PersonDaoImpl();

    public PersonServiceImpl() {

    }

    @Override
    public boolean addPerson(Person person) {
        boolean isAdded = false;
        try {
            if (personDao.addPerson(person)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updatePerson(Person person) {
        boolean isUpdated = false;
        try {
            if (personDao.updatePerson(person)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deletePerson(int id) {
        boolean isDeleted = false;
        try {
            if (personDao.deletePerson(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<Person> showPeople() {
        List<Person> people = null;
        try {
            people = personDao.showPeople();
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return people;
    }

    @Override
    public Person findPersonById(int id) {
        Person person = null;

        try {
            person = personDao.findPersonById(id);
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return person;
    }

    @Override
    public Person findPersonByName(String name) {
        Person person = null;

        try {
            person = personDao.findPersonByName(name);
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return person;
    }
}
