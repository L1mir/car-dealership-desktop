package org.limir.models.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class Employee implements Serializable {
    private Long employee_id;
    private String position;
    private BigDecimal salary;
    private Company company;
    private Person person;

    public Employee() {

    }

    public Employee(Long employee_id, String position, BigDecimal salary, Company company, Person person) {
        this.employee_id = employee_id;
        this.position = position;
        this.salary = salary;
        this.company = company;
        this.person = person;
    }

    public Long getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Long employee_id) {
        this.employee_id = employee_id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employee_id +
                ", position='" + position + '\'' +
                ", salary=" + salary +
                ", company=" + company +
                ", person=" + person +
                '}';
    }
}
