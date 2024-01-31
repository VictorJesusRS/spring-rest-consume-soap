package com.example.demo6.controller;

import com.example.demo6.dto.EmployeeResponseStoredDto;
import com.example.demo6.entity.EmployeeEntity;
import com.example.demo6.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.xml.datatype.DatatypeConfigurationException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    ResponseEntity<Object> store(@Valid @RequestBody EmployeeEntity employee) throws DatatypeConfigurationException {
        System.out.println(employee.toString());
        if (!this.employeeService.isAdult(employee.getBirthday())){
            return ResponseEntity.unprocessableEntity().body("It's not a adult");
        }

        try{
            EmployeeResponseStoredDto stored = this.employeeService.store(employee);
            return ResponseEntity.ok(stored);
        }catch (RuntimeException e){
            throw new RuntimeException("Employee controller store failed" + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        System.out.println("aaa");

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handle(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().body("your own message" + e.getMessage());
    }
}

