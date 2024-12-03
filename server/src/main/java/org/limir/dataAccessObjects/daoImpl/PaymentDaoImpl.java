package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.PaymentDao;
import org.limir.models.entities.Payment;
import org.limir.sessionFactory.HibernateSessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PaymentDaoImpl implements PaymentDao {
    @Override
    public boolean addPayment(Payment payment) {
        boolean isAdded = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.save(payment);
            tx.commit();
            session.close();
            isAdded = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updatePayment(Payment payment) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            session.update(payment);
            tx.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deletePayment(int id) {
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
    public List<Payment> showPayments() {
        List<Payment> payments = (List<Payment>) HibernateSessionFactory
                .getSessionFactory()
                .openSession()
                .createQuery("FROM Payment ").list();
        return payments;
    }

    @Override
    public Payment findPaymentById(int id) {
        Payment payment = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction tx = session.beginTransaction();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Payment> cr = cb.createQuery(Payment.class);
            Root<Payment> root = cr.from(Payment.class);
            cr.select(root).where(cb.equal(root.get("personId"), id));
            payment = session.createQuery(cr).getSingleResult();
            tx.commit();
            session.close();
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return payment;
    }
}
