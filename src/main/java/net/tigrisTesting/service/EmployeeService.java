package net.tigrisTesting.service;

import net.tigrisTesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {

    public Employee saveEmployee(Employee employee);
    public List<Employee> getAllEmployees();
    public Optional<Employee> getEmployeeById(long id);

    public Employee updateEmployee(Employee employee);

    public void deleteEmployee(long id);
}
