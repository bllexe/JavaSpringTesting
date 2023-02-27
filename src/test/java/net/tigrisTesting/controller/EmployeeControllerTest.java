package net.tigrisTesting.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.tigrisTesting.model.Employee;
import net.tigrisTesting.service.EmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    //test for create employee controller layer
    @Test
    @DisplayName("Test for Create employee controller")
    public void givenEmployeeObject_whenEmployeeCreate_thenReturnEmployeeObject() throws Exception {
        //given --precondition operation
        Employee employee = Employee.builder()
                .firstName("bilal")
                .lastName("yakut")
                .email("bilal@mail.com")
                .build();
        given(employeeService.saveEmployee(any(Employee.class)))
                .willAnswer((invocationOnMock -> invocationOnMock.getArgument(0)));

        //when --action that we are going test

        ResultActions response = mockMvc.perform(post("/api/employee/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        //then verify the output
        response
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    //test for get All Employee Rest Api
    @Test
    @DisplayName("Test for get All employee Rest Api")
    public void givenListOfEmployee_whenGetAllEmployees_thenReturnListOfEmployees() throws Exception {
        //given --precondition operation
        List<Employee> listOfEmployees = new ArrayList<>();
        listOfEmployees.add(Employee.builder().firstName("bilal").lastName("yakut").email("bilal@mail.com").build());
        listOfEmployees.add(Employee.builder().firstName("jenny").lastName("jenny").email("jenn@mail.com").build());
        given(employeeService.getAllEmployees()).willReturn(listOfEmployees);

        //when --action that we are going test
        ResultActions response = mockMvc.perform(get("/api/employee/"));

        //then verify the output
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", CoreMatchers.is(listOfEmployees.size())));
    }


    //test for  get Employee ById Rest Api
    //positive scenario -if employee id valid
    @Test
    @DisplayName("GetEmployeeById rest controller operation")
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() throws Exception {
        //given --precondition operation
        long employeeId = 1L;
        Employee employee = Employee.builder().firstName("bilal").lastName("yakut").email("bilal@mail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        //when --action that we are going test
        ResultActions response = mockMvc.perform(get("/api/employee/{id}", employeeId));

        //then verify the output
        response
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(employee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(employee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(employee.getEmail())));

    }

    //Test for update Employee Rest Api
    @Test
    public void givenUpdatedEmployee_whenUpdatedEmployee_thenReturnUpdateObject() throws Exception {
        //given --precondition operation
        long employeeId = 1L;
        Employee savedEmployee = Employee.builder()
                .firstName("bilal").lastName("yakut").email("bilal@mail.com").build();

        Employee updatedEmployee = Employee.builder()
                .firstName("billy").lastName("billy").email("billy@mail.com").build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.ofNullable(savedEmployee));
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation) -> invocation.getArgument(0));

        //when --action that we are going test
        ResultActions response = mockMvc.perform(put("/api/employee/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee)));

        //then verify the output

        response
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", CoreMatchers.is(updatedEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName", CoreMatchers.is(updatedEmployee.getLastName())))
                .andExpect(jsonPath("$.email", CoreMatchers.is(updatedEmployee.getEmail())));

    }

    //test for delete employee Rest Api
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturn200() throws Exception {
        //given --precondition operation
        long employeeId=1L;
        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        //when --action that we are going test
        ResultActions response = mockMvc.perform(delete("/api/employee/{id}", employeeId));

        //then verify the output
        response.andExpect(status().isOk())
                .andDo(print());

    }
}