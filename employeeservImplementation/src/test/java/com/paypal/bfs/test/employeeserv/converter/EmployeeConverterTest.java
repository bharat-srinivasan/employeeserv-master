package com.paypal.bfs.test.employeeserv.converter;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeConverterTest {

    @Mock
    @SuppressWarnings("unused")
    private AddressConverter addressConverter;

    @InjectMocks
    private EmployeeConverter employeeConverter;

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
    public void toEntityConverter() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Employee toBeCreated = getEmployee();
        com.paypal.bfs.test.employeeserv.entity.Employee entity = employeeConverter.toEntity(toBeCreated);
        assertTrue(1 == entity.getEmployeeId());
        assertEquals("test_f", entity.getFirstName());
        assertEquals("test_d", entity.getLastName());
        assertEquals("1990-01-01", sdf.format(entity.getDateOfBirth()));
    }
}