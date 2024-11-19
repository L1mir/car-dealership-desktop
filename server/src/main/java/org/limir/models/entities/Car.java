package org.limir.models.entities;

import org.limir.enums.CarStatus;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "Cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "car_id")
    private Long car_id;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private int year;

    @Column(name = "price")
    BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "car_status")
    CarStatus car_status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public Car() {

    }

    public Car(Long car_id, String model, int year, BigDecimal price, CarStatus car_status, Company company) {
        this.car_id = car_id;
        this.model = model;
        this.year = year;
        this.price = price;
        this.car_status = car_status;
        this.company = company;
    }

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public CarStatus getCar_status() {
        return car_status;
    }

    public void setCar_status(CarStatus car_status) {
        this.car_status = car_status;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Car{" +
                "car_id=" + car_id +
                ", model='" + model + '\'' +
                ", year=" + year +
                ", price=" + price +
                ", car_status=" + car_status +
                ", car_status=" + car_status +
                ", company=" + company +
                '}';
    }
}
