package org.limir.services.servicesImpl;

import org.limir.dataAccessObjects.CompanyDao;
import org.limir.dataAccessObjects.daoImpl.CompanyDaoImpl;
import org.limir.models.Company;
import org.limir.services.CompanyService;

import java.util.ArrayList;
import java.util.List;

public class CompanyServiceImpl implements CompanyService {
    CompanyDao companyDao = new CompanyDaoImpl();

    public CompanyServiceImpl() {
    }

    @Override
    public boolean addCompany(Company company) {
        boolean isAdded = false;
        try {
            if (companyDao.addCompany(company)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateCompany(Company company) {
        boolean isUpdated = false;
        try {
            if (companyDao.updateCompany(company)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteCompany(int id) {
        boolean isDeleted = false;
        try {
            if (companyDao.deleteCompany(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<Company> showCompanies() {
        List<Company> companies = new ArrayList<>();
        try {
            companies = companyDao.showCompanies();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return companies;
    }

    @Override
    public Company findCompanyById(int id) {
        Company company = null;
        try {
            company = companyDao.findCompanyById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }

    @Override
    public Company findCompanyByName(String name) {
        Company company = null;
        try {
            company = companyDao.findCompanyByName(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return company;
    }
}
