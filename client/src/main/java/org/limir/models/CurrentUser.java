package org.limir.models;

import org.limir.models.dto.UserDTO;

public class CurrentUser {
    private static UserDTO user;

    private CurrentUser() {
        // Приватный конструктор для предотвращения создания экземпляра
    }

    public static void setUser(UserDTO loggedUser) {
        user = loggedUser;
    }

    public static UserDTO getUser() {
        return user;
    }

    public static void clear() {
        user = null; // Сбрасываем данные пользователя при выходе
    }
}

