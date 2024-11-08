package org.limir.models.entities;

import org.limir.models.enums.PaymentMethod;
import org.limir.models.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.Date;

public class Payment {
    private Long order_id;
    private BigDecimal amount;
    private Date date;
    private PaymentMethod payment_method;
    private PaymentStatus payment_status;
    private Order order;
    private User user;
    private Company company;

    public Payment() {

    }

    public Payment(Long order_id, BigDecimal amount, Date date, PaymentMethod payment_method,
                   PaymentStatus payment_status, Order order, User user, Company company) {
        this.order_id = order_id;
        this.amount = amount;
        this.date = date;
        this.payment_method = payment_method;
        this.payment_status = payment_status;
        this.order = order;
        this.user = user;
        this.company = company;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentMethod getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public PaymentStatus getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(PaymentStatus payment_status) {
        this.payment_status = payment_status;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "order_id=" + order_id +
                ", amount=" + amount +
                ", date=" + date +
                ", payment_method=" + payment_method +
                ", payment_status=" + payment_status +
                ", order=" + order +
                ", user=" + user +
                ", company=" + company +
                '}';
    }
}
