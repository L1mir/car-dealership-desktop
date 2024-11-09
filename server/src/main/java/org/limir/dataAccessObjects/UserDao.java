package org.limir.dataAccessObjects;

import org.limir.models.entities.User;

import java.util.List;

public interface UserDao {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    List<User> showUsers();
    User findUserById(int id);
}
