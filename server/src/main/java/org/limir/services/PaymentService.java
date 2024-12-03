package org.limir.services;

import org.limir.models.entities.Company;
import org.limir.models.entities.Payment;

import java.util.List;

public interface PaymentService {
    boolean addPayment(Payment payment);

    boolean updatePayment(Payment payment);

    boolean deletePayment(int id);

    List<Payment> showPayments();

    Payment findPaymentById(int id);
}

