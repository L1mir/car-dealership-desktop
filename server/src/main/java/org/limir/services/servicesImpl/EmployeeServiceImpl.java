package org.limir.services.servicesImpl;

import org.limir.dataAccessObjects.EmployeeDao;
import org.limir.dataAccessObjects.daoImpl.EmployeeDaoImpl;
import org.limir.models.entities.Employee;
import org.limir.services.EmployeeService;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public boolean addEmployee(Employee employee) {
        boolean isAdded = false;
        try {
            if (employeeDao.addEmployee(employee)) {
                isAdded = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isAdded;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        boolean isUpdated = false;
        try {
            if (employeeDao.updateEmployee(employee)) {
                isUpdated = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }

    @Override
    public boolean deleteEmployee(Long id) {
        boolean isDeleted = false;
        try {
            if (employeeDao.deleteEmployee(id)) {
                isDeleted = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDeleted;
    }

    @Override
    public List<Employee> showEmployee() {
        List<Employee> employees = new ArrayList<>();
        try {
            employees = employeeDao.showEmployee();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public Employee findEmployeeById(Long id) {
        Employee employee = null;
        try {
            employee = employeeDao.findEmployeeById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }

    @Override
    public Employee findEmployeeByPosition(String position) {
        Employee employee = null;
        try {
            employee = employeeDao.findEmployeeByPosition(position);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return employee;
    }
}
