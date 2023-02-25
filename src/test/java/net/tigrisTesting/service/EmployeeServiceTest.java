package net.tigrisTesting.service;

import net.tigrisTesting.exception.ResourceNotFoundException;
import net.tigrisTesting.model.Employee;
import net.tigrisTesting.repository.EmployeeRepository;
import net.tigrisTesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
    private Employee employee;

    @BeforeEach
    public void setup() {

        //employeeRepository= Mockito.mock(EmployeeRepository.class);
        // employeeService=new EmployeeServiceImpl(employeeRepository); // using mockito annotation

        employee = Employee.builder()
                .id(1L)
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
    }

    //test for saving employee method
    @Test
    public void givenEmployeeObject_whenSaveEmployee_thenReturnEmployeeObject() {
        //given --precondition operation
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn((Employee) Optional.empty().orElse(null));

        given(employeeRepository.save(employee)).willReturn(employee);

        //when --action that we are going test
        Employee savedEmployee = employeeService.saveEmployee(employee);

        //then verify the output
        assertThat(savedEmployee).isNotNull();

    }

    //test for saving employee method which throws exception
    @Test
    @DisplayName("JUnit saveEmployee method which throws exception")
    public void givenEmployeeObject_whenSaveEmployee_thenThrowsException() {

        //given --precondition operation
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn((Optional.of(employee)).orElseThrow());

        //when --action that we are going test
        Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.saveEmployee(employee);
        });

        //then
        verify(employeeRepository, never()).save(employee);

    }


    //test for get getAllEmployees method
    @Test
    @DisplayName("JUnit getAllEmployee operation")
    public void givenEmployeeList_whenGetAllEmployees_thenReturnAllEmployeesList() {
        //given --precondition operation

        Employee employee1 = Employee.builder()
                .id(2L)
                .firstName("jen")
                .lastName("jen")
                .email("jen@mail.com")
                .build();


        given(employeeRepository.findAll()).willReturn(List.of(employee, employee1));

        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        //when --action that we are going test
        List<Employee> employeeList = employeeService.getAllEmployees();

        //then verify the output

        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


    //test for get Employee By id
    @Test
    @DisplayName("JUnit getEmployeeById operation")
    public void givenEmployeeId_whenGetEmployeeById_thenResultEmployeeObject() {
        //given --precondition operation
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when --action that we are going test
        Employee savedEmployee = employeeService.getEmployeeById(employee.getId()).get();

        //then verify the output
        assertThat(savedEmployee).isNotNull();
    }

    //test for delete employee byId operation
    @Test
    @DisplayName("Delete employeeById operation")
    public void givenEmployeeId_whenDeleteEmployeeById_thenNothing() {
        //given --precondition operation
        long employeeId = 1L;
        willDoNothing().given(employeeRepository).deleteById(employeeId);

        //when --action that we are going test
        employeeService.deleteEmployee(employeeId);

        //then verify the output
        verify(employeeRepository, times(1)).deleteById(employeeId);

    }
}