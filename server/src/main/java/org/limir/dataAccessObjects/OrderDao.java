package org.limir.dataAccessObjects;

import org.limir.models.entities.Order;
import org.limir.models.entities.Payment;

import java.util.List;

public interface OrderDao {
    boolean addOrder(Order order);

    boolean updateOrder(Order order);

    boolean deleteOrder(int id);

    List<Order> showOrders();

    Order findOrderById(int id);

    List<Order> findOrdersByUsername(String username);
}
