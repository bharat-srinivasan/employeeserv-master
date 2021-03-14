package com.paypal.bfs.test.employeeserv.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.EmployeeservApplication;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EmployeeservApplication.class})
@AutoConfigureMockMvc
public class EmployeeResourceImplTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Before
    public void cleanup() {
        employeeRepository.deleteAll();
    }

    private String toJson(Employee employee) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(employee);
    }

    private Employee getEmployee() {
        Employee emp = new Employee();
        emp.setId(1);
        emp.setFirstName("test_f");
        emp.setLastName("test_d");
        emp.setDateOfBirth("1990-01-01");
        Address addr = new Address();
        addr.setLine1("line1");
        addr.setLine2("line2");
        addr.setCity("city");
        addr.setState("state");
        addr.setZipCode(123456);
        emp.setAddress(addr);
        return emp;
    }

    @Test
    public void createAndRetrievalSuccessful() throws Exception {
        Employee emp = getEmployee();
        mvc.perform(post("/v1/bfs/employees").contentType(MediaType.APPLICATION_JSON).content(toJson(emp)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)));

        mvc.perform(get("/v1/bfs/employees/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void duplicateEmployeeCreationFails() throws Exception {
        Employee emp = getEmployee();
        mvc.perform(post("/v1/bfs/employees").contentType(MediaType.APPLICATION_JSON).content(toJson(emp)))
                .andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(1)));
        mvc.perform(post("/v1/bfs/employees").contentType(MediaType.APPLICATION_JSON).content(toJson(emp)))
                .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error", is("Employee already exists")));
    }

    @Test
    public void missingFewMandatoryFieldsResultsInBadRequest() throws Exception {
        Employee emp = getEmployee();
        emp.setDateOfBirth(null);
        mvc.perform(post("/v1/bfs/employees").contentType(MediaType.APPLICATION_JSON).content(toJson(emp))).andExpect(status().isBadRequest());
        emp.setFirstName(null);
        emp.setDateOfBirth("2012-10-10");
        mvc.perform(post("/v1/bfs/employees").contentType(MediaType.APPLICATION_JSON).content(toJson(emp))).andExpect(status().isBadRequest());
        ;
    }
}