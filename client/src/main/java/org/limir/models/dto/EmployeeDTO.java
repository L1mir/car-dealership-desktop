package org.limir.models.dto;

import org.limir.models.entities.Employee;

import java.math.BigDecimal;

public class EmployeeDTO {
    private Long employeeId;
    private String employeePosition;
    BigDecimal employeeSalary;
    private String companyName;
    private Long companyId;

    public EmployeeDTO() {

    }

    public Employee toEmployee() {
        Employee employee = new Employee();
        employee.setEmployee_id(employeeId);
        employee.setPosition(employeePosition);
        employee.setSalary(employeeSalary);
        employee.setSalary(employeeSalary);
        return employee;
    }

    public EmployeeDTO(Employee employee) {
        this.employeeId = employee.getEmployee_id();
        this.employeePosition = employee.getPosition();
        this.employeeSalary = employee.getSalary();
        this.companyName = employee.getCompany() != null ? employee.getCompany().getName() : null;
        this.companyId = employee.getCompany() != null ? employee.getCompany().getCompany_id() : null;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeePosition() {
        return employeePosition;
    }

    public void setEmployeePosition(String employeePosition) {
        this.employeePosition = employeePosition;
    }

    public BigDecimal getEmployeeSalary() {
        return employeeSalary;
    }

    public void setEmployeeSalary(BigDecimal employeeSalary) {
        this.employeeSalary = employeeSalary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
