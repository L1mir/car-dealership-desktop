package org.limir.dataAccessObjects.daoImpl;

import org.limir.dataAccessObjects.CompanyDao;
import org.limir.models.Company;

import java.util.List;

public class CompanyDaoImpl implements CompanyDao {
    @Override
    public boolean addCompany(Company company) {
        return false;
    }

    @Override
    public boolean updateCompany(Company company) {
        return false;
    }

    @Override
    public boolean deleteCompany(int id) {
        return false;
    }

    @Override
    public List<Company> showCompanies() {
        return List.of();
    }

    @Override
    public Company findCompanyById(int id) {
        return null;
    }

    @Override
    public Company findCompanyByName(String name) {
        return null;
    }
}
