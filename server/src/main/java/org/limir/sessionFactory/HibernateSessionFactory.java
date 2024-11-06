package org.limir.sessionFactory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.limir.models.*;

public class HibernateSessionFactory {
    private static SessionFactory sessionFactory;

    private HibernateSessionFactory() {

    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                org.hibernate.cfg.Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Car.class);
                configuration.addAnnotatedClass(Company.class);
                configuration.addAnnotatedClass(Employee.class);
                configuration.addAnnotatedClass(Order.class);
                configuration.addAnnotatedClass(Payment.class);
                configuration.addAnnotatedClass(Person.class);
                configuration.addAnnotatedClass(Service.class);
                configuration.addAnnotatedClass(User.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
