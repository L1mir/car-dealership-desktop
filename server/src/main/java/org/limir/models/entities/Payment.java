package org.limir.models.entities;

import org.limir.enums.PaymentMethod;
import org.limir.enums.PaymentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Long payment_id;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod payment_method;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus payment_status;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "company_id", nullable = false)
    private Company company;

    public Payment() {}

    public Payment(Long payment_id, BigDecimal amount, Date date, PaymentMethod payment_method,
                   PaymentStatus payment_status, Order order, User user, Company company) {
        this.payment_id = payment_id;
        this.amount = amount;
        this.date = date;
        this.payment_method = payment_method;
        this.payment_status = payment_status;
        this.order = order;
        this.user = user;
        this.company = company;
    }

    public Long getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(Long payment_id) {
        this.payment_id = payment_id;
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
                "payment_id=" + payment_id +
                ", amount=" + amount +
                ", date=" + date +
                ", payment_method=" + payment_method +
                ", payment_status=" + payment_status +
                ", order_id=" + (order != null ? order.getOrder_id() : "null") +
                ", user_id=" + (user != null ? user.getUser_id() : "null") +
                ", company_id=" + (company != null ? company.getCompany_id() : "null") +
                '}';
    }

}

