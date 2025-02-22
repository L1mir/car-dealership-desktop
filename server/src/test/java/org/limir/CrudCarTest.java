package org.limir;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.limir.dataAccessObjects.CarDao;
import org.limir.dataAccessObjects.daoImpl.CarDaoImpl;
import org.limir.enums.CarStatus;
import org.limir.models.entities.Car;
import org.limir.sessionFactory.HibernateSessionFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class CrudCarTest {

    private static CarDao carDao;

    @BeforeAll
    static void setUp() {
        HibernateSessionFactory.getSessionFactory();
        carDao = new CarDaoImpl();
    }

    @BeforeEach
    void cleanDatabase() {
        try (var session = HibernateSessionFactory.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM Car").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    @Order(1)
    void testAddCar() {
        Car car = new Car(null, "Toyota Corolla", 2020, BigDecimal.valueOf(20000), CarStatus.AVAILABLE, null);
        boolean result = carDao.addCar(car);
        assertTrue(result, "Car should be added successfully");
        assertNotNull(car.getCar_id(), "Car ID should be generated");
    }

    @Test
    @Order(2)
    void testFindCarById() {
        Car car = new Car(null, "Toyota Corolla", 2020, BigDecimal.valueOf(20000), CarStatus.AVAILABLE, null);
        carDao.addCar(car);

        Car foundCar = carDao.findCarById(car.getCar_id());
        assertNotNull(foundCar, "Car should be found by ID");
        assertEquals(car.getCar_id(), foundCar.getCar_id(), "Car ID should match");
    }

    @Test
    @Order(3)
    void testFindCarByModel() {
        String model = "Toyota Corolla";
        Car car = new Car(null, model, 2020, BigDecimal.valueOf(20000), CarStatus.AVAILABLE, null);
        carDao.addCar(car);

        Car foundCar = carDao.findCarByModel(model);
        assertNotNull(foundCar, "Car should be found by model");
        assertEquals(model, foundCar.getModel(), "Car model should match");
    }

    @Test
    @Order(4)
    void testUpdateCar() {
        Car car = new Car(null, "Toyota Corolla", 2020, BigDecimal.valueOf(20000), CarStatus.AVAILABLE, null);
        carDao.addCar(car);

        car.setPrice(BigDecimal.valueOf(21000));
        boolean result = carDao.updateCar(car);
        assertTrue(result, "Car should be updated successfully");

        Car updatedCar = carDao.findCarById(car.getCar_id());
        assertEquals(BigDecimal.valueOf(21000).setScale(2), updatedCar.getPrice(), "Car price should be updated");
    }

    @Test
    @Order(5)
    void testShowCars() {
        carDao.addCar(new Car(null, "Toyota Corolla", 2020, BigDecimal.valueOf(20000), CarStatus.AVAILABLE, null));
        carDao.addCar(new Car(null, "Honda Civic", 2019, BigDecimal.valueOf(18000), CarStatus.AVAILABLE, null));

        List<Car> cars = carDao.showCars();
        assertNotNull(cars, "Car list should not be null");
        assertEquals(2, cars.size(), "Car list should contain 2 cars");
    }

    @Test
    @Order(6)
    void testDeleteCar() {
        Car car = new Car(null, "Toyota Corolla", 2020, BigDecimal.valueOf(20000), CarStatus.AVAILABLE, null);
        carDao.addCar(car);

        boolean result = carDao.deleteCar(car.getCar_id());
        assertTrue(result, "Car should be deleted successfully");

        Car deletedCar = carDao.findCarById(car.getCar_id());
        assertNull(deletedCar, "Deleted car should not be found");
    }
}