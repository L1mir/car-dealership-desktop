package org.limir.models.entities;

import java.io.Serializable;
import java.util.List;

public class Company implements Serializable {
    private Long company_id;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;
    private List<Car> cars;
    private List<Order> orders;
    private List<Payment> payments;
    private List<Service> services;
    private List<Employee> employees;

    public Company() {

    }

    public Company(Long company_id, String name, String address, String phone,
                   String email, String website, List<Car> cars, List<Order> orders,
                   List<Payment> payments, List<Service> services, List<Employee> employees) {
        this.company_id = company_id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.website = website;
        this.cars = cars;
        this.orders = orders;
        this.payments = payments;
        this.services = services;
        this.employees = employees;
    }

    public Long getCompany_id() {
        return company_id;
    }

    public void setCompany_id(Long company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public String toString() {
        return "Company{" +
                "company_id=" + company_id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", website='" + website + '\'' +
                ", cars_count=" + (cars != null ? cars.size() : 0) +
                ", orders_count=" + (orders != null ? orders.size() : 0) +
                ", payments_count=" + (payments != null ? payments.size() : 0) +
                ", services_count=" + (services != null ? services.size() : 0) +
                ", employees_count=" + (employees != null ? employees.size() : 0) +
                '}';
    }
}
