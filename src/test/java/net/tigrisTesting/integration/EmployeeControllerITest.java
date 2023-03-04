package net.tigrisTesting.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.tigrisTesting.model.Employee;
import net.tigrisTesting.repository.EmployeeRepository;
import static org.hamcrest.CoreMatchers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class EmployeeControllerITest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        employeeRepository.deleteAll();
    }

    @Test
    public void givenEmployeeObject_whenEmployeeCreate_thenReturnEmployeeObject() throws Exception {
        //given --precondition operation
        Employee employee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        //when --action that we are going test

        ResultActions response = mockMvc.perform(post("/api/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then verify the output
        response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    @Test
    @DisplayName("Test for get All employee Rest Api")
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        //given --precondition operation
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("bilal").lastName("yakut").email("bilal@mail.com").build());
        listOfEmployees.add(Employee.builder().firstName("jenny").lastName("jenny").email("jenn@mail.com").build());
        employeeRepository.saveAll(listOfEmployees);

        //when --action that we are going test
        ResultActions response = mockMvc.perform(get("/api/employee/"));

        //then verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(listOfEmployees.size())));
    }

    @Test
    @DisplayName("GetEmployeeById rest controller operation")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given --precondition operation

        Employee employee = Employee.builder().firstName("bilal").lastName("yakut").email("bilal@mail.com").build();
        employeeRepository.save(employee);

        //when --action that we are going test
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employee.getId()));

        //then verify the output
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(employee.getLastName())))
                .andExpect(jsonPath("$.email", is(employee.getEmail())));

    }

    // JUnit test for update employee REST API - positive scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnUpdateEmployeeObject() throws Exception{
        // given - precondition or setup
        Employee savedEmployee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Billy")
                .lastName("ykt")
                .email("billy@mail.com")
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employee/{id}", savedEmployee.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));


        // then - verify the output
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", is(updatedEmployee.getEmail())));
    }

    // JUnit test for update employee REST API - negative scenario
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception{
        // given - precondition or setup
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        employeeRepository.save(savedEmployee);

        Employee updatedEmployee = Employee.builder()
                .firstName("Billy")
                .lastName("ykt")
                .email("billy@mail.com")
                .build();

        // when -  action or the behaviour that we are going test
        ResultActions response = mockMvc.perform(put("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        // then - verify the output
        response.andExpect(status().isNotFound())
                .andDo(print());
    }


    //test for delete employee Rest Api
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given --precondition operation
        Employee savedEmployee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        employeeRepository.save(savedEmployee);

        //when --action that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employee/{id}", savedEmployee.getId()));

        //then verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }

}
