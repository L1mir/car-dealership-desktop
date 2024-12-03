package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.OrderDao;
import org.limir.models.entities.Order;
import org.limir.sessionFactory.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    @Override
    public boolean addOrder(Order order) {
        boolean isAdded = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(order);
            tx.commit();
            session.close();
            isAdded = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateOrder(Order order) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(order);
            tx.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteOrder(int id) {
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
    public List<Order> showOrders() {
        List<Order> orders = (List<Order>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("FROM Order").list();
        return orders;
    }

    @Override
    public Order findOrderById(int id) {
        Order order = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Order> cr = cb.createQuery(Order.class);
            Root<Order> root = cr.from(Order.class);
            cr.select(root).where(cb.equal(root.get("personId"), id));
            order = session.createQuery(cr).getSingleResult();
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return order;
    }
}
