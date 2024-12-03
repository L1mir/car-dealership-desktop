package org.limir.dataAccessObjects;

import org.limir.models.entities.Company;

import java.util.List;

public interface CompanyDao {
    boolean addCompany(Company company);

    boolean updateCompany(Company company);

    boolean deleteCompany(Long id);

    List<Company> showCompanies();

    Company findCompanyById(Long id);

    Company findCompanyByName(String name);
}
