package com.paypal.bfs.test.employeeserv.converter;

import com.paypal.bfs.test.employeeserv.entity.Employee;
import com.paypal.bfs.test.employeeserv.exception.InvalidDateOfBirthFormatException;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Converter for handling conversions between Entity class and model class
 */
@Component
public class EmployeeConverter implements EntityConverter<com.paypal.bfs.test.employeeserv.api.model.Employee, Employee> {

    private AddressConverter addressConverter;

    private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

    public EmployeeConverter(AddressConverter addressConverter) {
        this.addressConverter = addressConverter;
    }

    @Override
    public Employee toEntity(com.paypal.bfs.test.employeeserv.api.model.Employee model) {
        if (null == model) return null;
        Employee employee = new Employee();
        employee.setEmployeeId(model.getId());
        employee.setFirstName(model.getFirstName());
        employee.setLastName(model.getLastName());
        try {
            employee.setDateOfBirth(new Date(dateFormatter.parse(model.getDateOfBirth()).getTime()));
        } catch (ParseException e) {
            throw new InvalidDateOfBirthFormatException("Unable to parse date of birth provided. Expected format is yyyy-mm-dd.");
        }
        employee.setAddress(addressConverter.toEntity(model.getAddress()));
        return employee;
    }

    @Override
    public com.paypal.bfs.test.employeeserv.api.model.Employee toModel(Employee entity) {
        if (null == entity) return null;
        com.paypal.bfs.test.employeeserv.api.model.Employee employee = new com.paypal.bfs.test.employeeserv.api.model.Employee();
        employee.setId(entity.getEmployeeId());
        employee.setFirstName(entity.getFirstName());
        employee.setLastName(entity.getLastName());
        employee.setAddress(addressConverter.toModel(entity.getAddress()));
        employee.setDateOfBirth(dateFormatter.format(new Date(entity.getDateOfBirth().getTime())));
        return employee;
    }
}
