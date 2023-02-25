package net.tigrisTesting.service.impl;

import net.tigrisTesting.exception.ResourceNotFoundException;
import net.tigrisTesting.model.Employee;
import net.tigrisTesting.repository.EmployeeRepository;
import net.tigrisTesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {

        Optional<Employee> employeeDb = Optional.ofNullable(employeeRepository.findByEmail(employee.getEmail()));

        if (employeeDb.isPresent()){
            throw  new ResourceNotFoundException("Employee already exist with this mail :" + employee.getEmail());
        }

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public void deleteEmployee(long id) {

    }
}
