package org.limir.services;

import org.limir.models.entities.Car;

import java.util.List;

public interface CarService {
    boolean addCar(Car car);

    boolean updateCar(Car car);

    boolean deleteCar(Long id);

    List<Car> showCars();

    Car findCarById(Long id);

    Car findCarByModel(String model);
}
