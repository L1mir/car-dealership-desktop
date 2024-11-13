package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.UserDao;
import org.limir.models.entities.Person;
import org.limir.models.entities.User;
import org.limir.sessionFactory.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDaoImpl implements UserDao {

    @Override
    public boolean addUser(User user) {
        boolean isAdded = false;

        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(user);
            tx.commit();
            session.close();
            isAdded = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateUser(User user) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteUser(int id) {
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
    public List<User> showUsers() {
        List<User> users = (List<User>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("FROM User ").list();
        return users;
    }

    @Override
    public User findUserById(int id) {
        User user = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cr = cb.createQuery(User.class);
            Root<User> root = cr.from(User.class);
            cr.select(root).where(cb.equal(root.get("userId"), id));
            user = session.createQuery(cr).getSingleResult();
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return user;
    }
}
