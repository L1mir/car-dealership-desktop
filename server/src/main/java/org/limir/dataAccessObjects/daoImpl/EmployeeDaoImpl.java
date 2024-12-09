package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.EmployeeDao;
import org.limir.models.entities.Employee;
import org.limir.sessionFactory.HibernateSessionFactory;

import java.util.List;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public boolean addEmployee(Employee employee) {
        boolean isAdded = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
            session.close();
            isAdded = true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return isAdded;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        boolean isDeleted = false;
        Transaction tx = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();

            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                if (employee.getCompany() != null) {
                    employee.getCompany().getEmployees().remove(employee);
                    employee.setCompany(null);
                }
                if (employee.getPerson() != null) {
                    employee.getPerson().getEmployees().remove(employee);
                    employee.setPerson(null);
                }

                session.delete(employee);
                isDeleted = true;
            }

            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.out.println("Exception: " + e);
        }
        return isDeleted;
    }


    @Override
    public List<Employee> showEmployee() {
        List<Employee> employees = HibernateSessionFactory.getSessionFactory()
                .openSession()
                .createQuery("from Employee ", Employee.class)
                .list();
        return employees;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Employee employee = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            employee = session.get(Employee.class, id);
        } catch (HibernateException e) {
            System.out.println("Exception: " + e);
        }
        return employee;
    }

    @Override
    public Employee findEmployeeByPosition(String position) {
        Employee employee = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            employee = session.createQuery("FROM Employee WHERE position = :position", Employee.class)
                    .setParameter("position", position)
                    .uniqueResult();
        } catch (HibernateException e) {
            System.out.println("Exception: " + e);
        }
        return employee;
    }
}
