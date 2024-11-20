package org.limir.models.dto;

import org.limir.models.entities.Car;
import org.limir.models.enums.CarStatus;

import java.math.BigDecimal;

public class CarDTO {
    private String model;
    private int year;
    private BigDecimal price;
    private String carStatus;
    private String companyName;

    public CarDTO(Car car) {
        this.model = car.getModel();
        this.year = car.getYear();
        this.price = car.getPrice();
        this.carStatus = car.getCar_status().toString();
        this.companyName = car.getCompany() != null ? car.getCompany().getName() : null;
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

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(String carStatus) {
        this.carStatus = carStatus;
    }
}
