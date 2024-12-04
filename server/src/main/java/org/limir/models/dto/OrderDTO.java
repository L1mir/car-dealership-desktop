package org.limir.models.dto;

import org.limir.enums.OrderStatus;
import org.limir.enums.PaymentMethod;
import org.limir.models.entities.Order;

import java.math.BigDecimal;
import java.util.Date;

public class OrderDTO {
    private Long carId;
    private Long companyId;
    private Long orderId;
    private Date date;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
    private String userName;
    private String companyName;
    private PaymentMethod paymentMethod;

    public OrderDTO(Order order) {
        if (order != null) {
            this.orderId = order.getOrder_id();
            this.date = order.getDate();
            this.orderStatus = order.getOrder_status();
            this.totalPrice = order.getTotal_price();

            if (order.getPayments() != null && !order.getPayments().isEmpty()) {
                this.paymentMethod = order.getPayments().get(0).getPayment_method();
            }
            if (order.getCompany() != null) {
                this.companyId = order.getCompany().getCompany_id();
                this.companyName = order.getCompany().getName();
            }

            if (order.getUser() != null) {
                this.userName = order.getUser().getUsername();
            }
        }
    }


    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public OrderDTO() {
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCartId(Long cartId) {
        this.carId = cartId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }


    @Override
    public String toString() {
        return "OrderDTO{" +
                "carId=" + carId +
                ", companyId=" + companyId +
                ", orderId=" + orderId +
                ", date=" + date +
                ", orderStatus=" + orderStatus +
                ", totalPrice=" + totalPrice +
                ", userName='" + userName + '\'' +
                ", companyName='" + companyName + '\'' +
                ", paymentMethod='" + paymentMethod + '\'' +
                '}';
    }
}
