package org.limir.models.dto;

import org.limir.models.entities.Company;

public class CompanyDTO {
    private Long companyId;
    private String name;
    private String address;
    private String phone;
    private String email;
    private String website;

    public CompanyDTO(Company company) {
        this.companyId = getCompanyId();
        this.name = company.getName();
        this.address = company.getAddress();
        this.phone = company.getPhone();
        this.email = company.getEmail();
        this.website = company.getWebsite();
    }

    public Company toCompany() {
        Company company = new Company();
        company.setCompany_id(this.companyId);
        company.setName(this.name);
        company.setAddress(this.address);
        company.setPhone(this.phone);
        company.setEmail(this.email);
        company.setWebsite(this.website);
        return company;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
}

