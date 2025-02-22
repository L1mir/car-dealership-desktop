package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.PersonDao;
import org.limir.models.entities.Person;
import org.limir.sessionFactory.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PersonDaoImpl implements PersonDao {
    @Override
    public boolean addPerson(Person person) {
        boolean isAdded = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(person);
            tx.commit();
            session.close();
            isAdded = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updatePerson(Person person) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(person);
            tx.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deletePerson(int id) {
        boolean isDeleted = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.delete(id);
            tx.commit();
            session.close();
            isDeleted = true;
        } catch (HibernateException e) {
            throw new RuntimeException(e);
        }
        return isDeleted;
    }

    @Override
    public List<Person> showPeople() {
        List<Person> people = (List<Person>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("FROM Persons").list();
        return people;
    }

    @Override
    public Person findPersonById(int id) {
        Person person = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Person> cr = cb.createQuery(Person.class);
            Root<Person> root = cr.from(Person.class);
            cr.select(root).where(cb.equal(root.get("personId"), id));
            person = session.createQuery(cr).getSingleResult();
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return person;
    }

    @Override
    public Person findPersonByName(String name) {
        Person person = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Person> cr = cb.createQuery(Person.class);
            Root<Person> root = cr.from(Person.class);
            cr.select(root).where(cb.equal(root.get("last_name"), name));
            person = session.createQuery(cr).getSingleResult();
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return person;
    }
}
