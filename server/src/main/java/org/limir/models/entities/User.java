package org.limir.models.entities;

import org.limir.enums.UserRole;
import org.limir.models.dto.UserDTO;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "user_role")
    @Enumerated(EnumType.STRING)
    private UserRole user_role;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Payment> payments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<Order> orders = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    public User() {

    }

    public User(Long user_id, String username, String password, UserRole user_role, String email, String phone,
                String address, Set<Payment> payments, Set<Order> orders, Person person) {
        this.user_id = user_id;
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
        return new UserDTO(
                this.username,
                this.email,
                this.phone,
                this.address,
                this.user_role
        );
    }


    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public Set<Payment> getPayments() {
        return payments;
    }

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
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
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", user_role=" + user_role +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", payments=" + payments +
                ", orders=" + orders +
                '}';
    }
}
