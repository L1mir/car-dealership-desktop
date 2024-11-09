package org.limir.services.servicesImpl;

import org.hibernate.HibernateError;
import org.limir.dataAccessObjects.UserDao;
import org.limir.dataAccessObjects.daoImpl.UserDaoImpl;
import org.limir.models.entities.User;
import org.limir.services.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDao = new UserDaoImpl();

    @Override
    public boolean addUser(User user) {
        boolean isAdded = false;
        try {
            if (userDao.addUser(user)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateUser(User user) {
        boolean isUpdated = false;
        try {
            if (userDao.updateUser(user)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteUser(int id) {
        boolean isDeleted = false;
        try {
            if (userDao.deleteUser(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<User> showUsers() {
        List<User> users = null;
        try {
            users = userDao.showUsers();
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return users;
    }

    @Override
    public User findUserById(int id) {
        User user = null;

        try {
            user = userDao.findUserById(id);
        } catch (HibernateError error) {
            error.printStackTrace();
        }
        return user;
    }
}
