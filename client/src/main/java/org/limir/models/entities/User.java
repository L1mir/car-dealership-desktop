package org.limir.models.entities;

import com.google.gson.annotations.Expose;
import org.limir.models.dto.UserDTO;
import org.limir.models.enums.UserRole;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {
    @Expose
    private Long person_id;

    @Expose
    private String username;

    @Expose
    private String password;

    @Expose
    private UserRole user_role;

    @Expose
    private String email;

    @Expose
    private String phone;

    @Expose
    private String address;

    @Expose
    private List<Payment> payments = new ArrayList<>();

    @Expose
    private List<Order> orders = new ArrayList<>();

    @Expose
    private Person person;

    public User() {

    }

    public User(Long person_id, String username, String password, UserRole user_role, String email, String phone,
                String address, List<Payment> payments, List<Order> orders, Person person) {
        this.person_id = person_id;
        this.username = username;
        this.password = password;
        this.user_role = user_role;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.payments = payments;
        this.orders = orders;
        this.person = person;
    }

    public UserDTO toDTO() {
        return new UserDTO(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(person_id, user.person_id) && Objects.equals(username, user.username) && Objects.equals(password, user.password) && user_role == user.user_role && Objects.equals(email, user.email) && Objects.equals(phone, user.phone) && Objects.equals(address, user.address) && Objects.equals(payments, user.payments) && Objects.equals(orders, user.orders) && Objects.equals(person, user.person);
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRole getUser_role() {
        return user_role;
    }

    public void setUser_role(UserRole user_role) {
        this.user_role = user_role;
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

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "User{" +
                "person_id=" + person_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", user_role=" + user_role +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", payments=" + payments +
                ", orders=" + orders +
                ", person=" + person +
                '}';
    }
}
