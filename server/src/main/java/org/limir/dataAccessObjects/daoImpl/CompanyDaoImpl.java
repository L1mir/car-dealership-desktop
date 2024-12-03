package org.limir.dataAccessObjects.daoImpl;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.limir.dataAccessObjects.CompanyDao;
import org.limir.models.entities.Company;
import org.limir.sessionFactory.HibernateSessionFactory;

import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    @Override
    public boolean addCompany(Company company) {
        boolean isAdded = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.save(company);
            transaction.commit();
            session.close();
            isAdded = true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return isAdded;
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean isUpdated = false;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            Transaction transaction = session.beginTransaction();
            session.update(company);
            transaction.commit();
            session.close();
            isUpdated = true;
        } catch (NoClassDefFoundError e) {
            System.out.println("Exception: " + e);
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCompany(Long id) {
        boolean isDeleted = false;
        Transaction tx = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            Company company = session.get(Company.class, id);
            if (company != null) {
                session.delete(company);
                isDeleted = true;
            }
            tx.commit();
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            System.out.println("Exception: " + e);
        }
        return isDeleted;
    }

    public List<Company> showCompanies() {
        List<Company> companies = HibernateSessionFactory.getSessionFactory()
                .openSession()
                .createQuery("from Company ", Company.class)
                .list();
        return companies;
    }

    @Override
    public Company findCompanyById(Long id) {
        Company company = null;
        try {
            Session session = HibernateSessionFactory.getSessionFactory().openSession();
            company = session.get(Company.class, id);
        } catch (HibernateException e) {
            System.out.println("Exception: " + e);
        }
        return company;
    }

    @Override
    public Company findCompanyByName(String name) {
        Company company = null;
        try (Session session = HibernateSessionFactory.getSessionFactory().openSession()) {
            company = session.createQuery("FROM Company WHERE name = :name", Company.class)
                    .setParameter("name", name)
                    .uniqueResult();

            if (company != null) {
                Hibernate.initialize(company.getOrders());
                Hibernate.initialize(company.getCars());
                Hibernate.initialize(company.getPayments());
                Hibernate.initialize(company.getServices());
                Hibernate.initialize(company.getEmployees());
            }
        } catch (HibernateException e) {
            System.out.println("Exception: " + e);
        }
        return company;
    }
}
