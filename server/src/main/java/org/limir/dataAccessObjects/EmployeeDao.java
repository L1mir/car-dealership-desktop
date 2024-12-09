package org.limir.dataAccessObjects;

import org.limir.models.entities.Employee;

import java.util.List;

public interface EmployeeDao {
    boolean addEmployee(Employee employee);

    boolean updateEmployee(Employee employee);

    boolean deleteEmployee(Long id);

    List<Employee> showEmployee();

    Employee findEmployeeById(Long id);

    Employee findEmployeeByPosition(String position);
}
