package org.limir.services.servicesImpl;

import org.hibernate.HibernateError;
import org.limir.dataAccessObjects.PaymentDao;
import org.limir.dataAccessObjects.daoImpl.PaymentDaoImpl;
import org.limir.models.entities.Company;
import org.limir.models.entities.Payment;
import org.limir.models.entities.Person;
import org.limir.services.PaymentService;

import java.util.List;

public class PaymentServiceImpl implements PaymentService {
    PaymentDao paymentDao = new PaymentDaoImpl();

    @Override
    public boolean addPayment(Payment payment) {
        boolean isAdded = false;
        try {
            if (paymentDao.addPayment(payment)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updatePayment(Payment payment) {
        boolean isUpdated = false;
        try {
            if (paymentDao.updatePayment(payment)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deletePayment(int id) {
        boolean isDeleted = false;
        try {
            if (paymentDao.deletePayment(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<Payment> showPayments() {
        List<Payment> payments = null;
        try {
            payments = paymentDao.showPayments();
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return payments;
    }

    @Override
    public Payment findPaymentById(int id) {
        Payment payment = null;

        try {
            payment = paymentDao.findPaymentById(id);
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return payment;
    }
}
