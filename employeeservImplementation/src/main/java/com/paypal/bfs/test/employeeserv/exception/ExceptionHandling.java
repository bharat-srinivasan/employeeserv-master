package com.paypal.bfs.test.employeeserv.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionHandling {

    @ExceptionHandler({EmployeeAlreadyExistsException.class, InvalidDateOfBirthFormatException.class})
    public ResponseEntity<Map<String, String>> employeeAlreadyExists(RuntimeException ex) {
        return ResponseEntity.badRequest().body(new HashMap<String, String>(1) {{
            put("error", ex.getMessage());
        }});
    }
}
