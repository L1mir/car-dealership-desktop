package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.CarDao;
import org.limir.models.entities.Car;
import org.limir.models.entities.Company;
import org.limir.sessionFactory.HibernateSessionFactory;

import java.util.List;

public class CarDaoImpl implements CarDao {
    @Override
    public boolean addCar(Car car) {
        boolean isAdded = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(car);
            transaction.commit();
            session.close();
            isAdded = true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return isAdded;
    }

    @Override
    public boolean updateCar(Car car) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(car);
            transaction.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCar(Long id) {
        boolean isDeleted = false;
        Transaction tx = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Car car = session.get(Car.class, id);
            if (car != null) {
                session.delete(car);
                isDeleted = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.out.println("Exception: " + e);
        }
        return isDeleted;
    }

    public List<Car> showCars() {
        List<Car> cars = HibernateSessionFactory.getSessionFactory()
                .openSession()
                .createQuery("from Car", Car.class)
                .list();
        return cars;
    }


    @Override
    public Car findCarById(Long id) {
        Car car = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            car = session.get(Car.class, id);
        } catch (HibernateException e) {
            System.out.println("Exception: " + e);
        }
        return car;
    }

    @Override
    public Car findCarByModel(String model) {
        Car car = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            car = session.createQuery("FROM Car WHERE model = :model", Car.class)
                    .setParameter("model", model)
                    .uniqueResult();

            if (car != null) {
                Hibernate.initialize(car.getCompany());
            }
        } catch (HibernateException e) {
            System.out.println("Exception: " + e);
        }
        return car;
    }
}
