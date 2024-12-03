package org.limir.dataAccessObjects;

import org.limir.models.entities.Payment;

import java.util.List;

public interface PaymentDao {
    boolean addPayment(Payment payment);

    boolean updatePayment(Payment payment);

    boolean deletePayment(int id);

    List<Payment> showPayments();

    Payment findPaymentById(int id);
}
