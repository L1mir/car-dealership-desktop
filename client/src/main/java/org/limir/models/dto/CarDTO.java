package org.limir.models.dto;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import org.limir.models.entities.Car;

import java.math.BigDecimal;

public class CarDTO {
    private Long carId;
    private String model;
    private int year;
    private BigDecimal price;
    private String carStatus;
    private String companyName;
    private Long companyId;

    private BooleanProperty favorite = new SimpleBooleanProperty(false);

    public CarDTO(Car car) {
        this.carId = car.getCar_id();
        this.model = car.getModel();
        this.year = car.getYear();
        this.price = car.getPrice();
        this.carStatus = car.getCar_status().toString();
        this.companyName = car.getCompany() != null ? car.getCompany().getName() : null;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
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

    public boolean isFavorite() {
        return favorite.get();
    }

    public void setFavorite(boolean favorite) {
        this.favorite.set(favorite);
    }

    public BooleanProperty favoriteProperty() {
        return favorite;
    }
}
