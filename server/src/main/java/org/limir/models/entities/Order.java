package org.limir.models.entities;

import org.limir.enums.OrderStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long order_id;

    @Column(name = "date")
    private Date date;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status")
    private OrderStatus order_status;

    @Column(name = "total_price")
    private BigDecimal total_price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Payment> payments;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "compant_id", nullable = false)
    private Company company;

    public Order() {

    }

    public Order(Long order_id, Date date, OrderStatus order_status,
                 BigDecimal total_price, List<Payment> payments, User user, Company company) {
        this.order_id = order_id;
        this.date = date;
        this.order_status = order_status;
        this.total_price = total_price;
        this.payments = payments;
        this.user = user;
        this.company = company;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrder_status() {
        return order_status;
    }

    public void setOrder_status(OrderStatus order_status) {
        this.order_status = order_status;
    }

    public BigDecimal getTotal_price() {
        return total_price;
    }

    public void setTotal_price(BigDecimal total_price) {
        this.total_price = total_price;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
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
        return "Order{" +
                "order_id=" + order_id +
                ", date=" + date +
                ", order_status=" + order_status +
                ", total_price=" + total_price +
                ", payments=" + payments +
                ", user=" + user +
                ", company=" + company +
                '}';
    }
}
