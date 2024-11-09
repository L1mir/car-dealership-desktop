package org.limir.services;

import org.limir.models.entities.User;

import java.util.List;

public interface UserService {
    boolean addUser(User user);
    boolean updateUser(User user);
    boolean deleteUser(int id);
    List<User> showUsers();
    User findUserById(int id);
}
