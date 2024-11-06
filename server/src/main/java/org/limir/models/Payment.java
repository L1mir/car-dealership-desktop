package org.limir.models;

import org.limir.enums.PaymentMethod;
import org.limir.enums.PaymentStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long order_id;

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

    public Payment(Long order_id, BigDecimal amount, Date date, PaymentMethod payment_method, PaymentStatus payment_status) {
        this.order_id = order_id;
        this.amount = amount;
        this.date = date;
        this.payment_method = payment_method;
        this.payment_status = payment_status;
    }

    public Payment() {

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

    public PaymentMethod getPaymentMethod() {
        return payment_method;
    }

    public void setPaymentMethod(PaymentMethod payment_method) {
        this.payment_method = payment_method;
    }

    public PaymentStatus getPaymentStatus() {
        return payment_status;
    }

    public void setPaymentStatus(PaymentStatus payment_status) {
        this.payment_status = payment_status;
    }
}
