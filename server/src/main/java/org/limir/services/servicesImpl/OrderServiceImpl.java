package org.limir.services.servicesImpl;

import org.hibernate.HibernateError;
import org.limir.dataAccessObjects.OrderDao;
import org.limir.dataAccessObjects.daoImpl.OrderDaoImpl;
import org.limir.models.entities.Order;
import org.limir.services.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    OrderDao orderDao = new OrderDaoImpl();

    @Override
    public boolean addOrder(Order order) {
        boolean isAdded = false;
        try {
            if (orderDao.addOrder(order)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateOrder(Order order) {
        boolean isUpdated = false;
        try {
            if (orderDao.updateOrder(order)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteOrder(int id) {
        boolean isDeleted = false;
        try {
            if (orderDao.deleteOrder(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<Order> showOrders() {
        List<Order> orders = null;
        try {
            orders = orderDao.showOrders();
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order findOrderById(int id) {
        Order order = null;

        try {
            order = orderDao.findOrderById(id);
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return order;
    }

    @Override
    public List<Order> findOrdersByUsername(String username) {
        List<Order> orders = null;
        try {
            orders = orderDao.findOrdersByUsername(username);
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return orders;
    }
}
