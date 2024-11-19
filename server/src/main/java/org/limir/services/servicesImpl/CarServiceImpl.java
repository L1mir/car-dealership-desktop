package org.limir.services.servicesImpl;

import org.limir.dataAccessObjects.CarDao;
import org.limir.dataAccessObjects.daoImpl.CarDaoImpl;
import org.limir.models.entities.Car;
import org.limir.services.CarService;

import java.util.ArrayList;
import java.util.List;

public class CarServiceImpl implements CarService {
    CarDao carDao = new CarDaoImpl();

    public CarServiceImpl() {

    }

    @Override
    public boolean addCar(Car car) {
        boolean isAdded = false;
        try {
            if (carDao.addCar(car)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateCar(Car car) {
        boolean isUpdated = false;
        try {
            if (carDao.updateCar(car)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCar(int id) {
        boolean isDeleted = false;
        try {
            if (carDao.deleteCar(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<Car> showCars() {
        List<Car> companies = new ArrayList<>();
        try {
            companies = carDao.showCars();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public Car findCarById(int id) {
        Car car = null;
        try {
            car = carDao.findCarById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return car;
    }
}
