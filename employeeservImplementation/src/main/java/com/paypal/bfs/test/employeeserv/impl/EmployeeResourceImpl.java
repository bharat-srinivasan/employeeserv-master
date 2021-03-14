package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.converter.EmployeeConverter;
import com.paypal.bfs.test.employeeserv.exception.EmployeeAlreadyExistsException;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * Implementation class for employee resource.
 */
@Validated
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private static Logger _logger = LoggerFactory.getLogger(EmployeeResourceImpl.class);

    private EmployeeRepository employeeRepository;

    private EmployeeConverter employeeConverter;

    public EmployeeResourceImpl(EmployeeRepository employeeRepository, EmployeeConverter employeeConverter) {
        this.employeeRepository = employeeRepository;
        this.employeeConverter = employeeConverter;
    }

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {
        int empId;
        try {
            empId = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            _logger.error("Error occurred while parsing employee ID", ex);
            throw new NumberFormatException("Invalid employee ID format");
        }
        Optional<com.paypal.bfs.test.employeeserv.entity.Employee> employee = employeeRepository.findById(empId);
        return employee.map(value -> ResponseEntity.ok(employeeConverter.toModel(value))).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    @Transactional
    public ResponseEntity<Employee> createEmployee(Employee emp) {
        Optional<com.paypal.bfs.test.employeeserv.entity.Employee> match = employeeRepository.findById(emp.getId());
        if (match.isPresent()) {
            _logger.error("Employee with provided ID already exists");
            throw new EmployeeAlreadyExistsException("Employee already exists");
        }
        com.paypal.bfs.test.employeeserv.entity.Employee toCreate = employeeConverter.toEntity(emp);
        com.paypal.bfs.test.employeeserv.entity.Employee created = employeeRepository.save(toCreate);
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeConverter.toModel(created));
    }
}
