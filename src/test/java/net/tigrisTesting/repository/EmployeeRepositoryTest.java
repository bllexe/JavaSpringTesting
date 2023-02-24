package net.tigrisTesting.repository;

import net.tigrisTesting.model.Employee;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import java.util.Optional;


@DataJpaTest
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    //JUnit test for save employee operation

    @Test
    @DisplayName("Saving employee operation")
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {

        //given - precondition or setup
        Employee employee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilalykt@mail.com")
                .build();

        //when -action or the behavior that we are going test

        Employee savedEmployee = employeeRepository.save(employee);

        //then verify the output

        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);

    }

    //JUnit test for
    @Test
    @DisplayName("Get All Employee operation")
    public void givenEmployeeList_whenFindAll_thenEmployeeList() {
        //given --precondition operation
        Employee employee1 = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilalykt@mail.com")
                .build();
        Employee employee2 = Employee.builder()
                .firstName("jennifer")
                .lastName("jennifer")
                .email("jennifer@mail.com")
                .build();
        Employee employee3 = Employee.builder()
                .firstName("ema")
                .lastName("ema")
                .email("ema@mail.com")
                .build();

        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        employeeRepository.save(employee3);

        //when --action that we are going test
        List<Employee> employeeList = employeeRepository.findAll();


        //then verify the output
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(3);
    }


    //test for getOneEmployeeById operation
    @Test
    @DisplayName("Get Employee By Id operation")
    public void givenEmployeeObject_whenFindEmployeeById_thenReturnEmployeeObject() {
        //given --precondition operation

        Employee employee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        employeeRepository.save(employee);

        //when --action that we are going test
        Employee employeeDb = employeeRepository.findById(employee.getId()).get();

        //then verify the output
        assertThat(employeeDb).isNotNull();


    }

    //test for get Employee by Email operation
    @Test
    @DisplayName("Get Employee By Email operation")
    public void givenEmployeeMail_whenFindEmployeeByEmail_thenEmployeeObject() {
        //given --precondition operation

        Employee employee = Employee.builder()
                .firstName("jennifer")
                .lastName("jennifer")
                .email("jennifer@mail.com")
                .build();
        employeeRepository.save(employee);

        //when --action that we are going test
        Employee employeeDb = employeeRepository.findByEmail(employee.getEmail());

        //then verify the output
        assertThat(employeeDb).isNotNull();
    }

    //test for update employee operation
    @Test
    @DisplayName("update Employee with by id operation")
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        //given --precondition operation
        Employee employee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        employeeRepository.save(employee);

        //when --action that we are going test

        Employee employeeDb = employeeRepository.findById(employee.getId()).get();
        employeeDb.setEmail("billy@mail.com");
        employeeDb.setFirstName("bilal");
        employeeDb.setLastName("yakut");
        Employee updatedEmployee = employeeRepository.save(employeeDb);
        //then verify the output
        assertThat(updatedEmployee.getEmail()).isEqualTo("billy@mail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("bilal");
        assertThat(updatedEmployee.getLastName()).isEqualTo("yakut");


    }


    //test for delete employee operation
    @Test
    @DisplayName("delete employee operation")
    public void givenEmployeeObject_whenDeleteEmployee_thenRemoveEmployee() {
        //given --precondition operation
        Employee employee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        employeeRepository.save(employee);


        //when --action that we are going test
        //employeeRepository.deleteById(employee.getId());
        employeeRepository.delete(employee);
        Optional<Employee> optionalEmployee= employeeRepository.findById(employee.getId());

        //then verify the output
        assertThat(optionalEmployee).isEmpty();
    }

}