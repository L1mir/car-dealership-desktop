package org.limir.models.dto;

import org.limir.enums.UserRole;

public class UserDTO {
    private String username;
    private String email;
    private String phone;
    private String address;
    private UserRole userRole;

    public UserDTO() {
    }

    public UserDTO(String username, String email, String phone, String address, UserRole userRole) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }
}
