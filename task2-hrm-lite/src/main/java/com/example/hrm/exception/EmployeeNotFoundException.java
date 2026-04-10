package com.example.hrm.exception;

public class EmployeeNotFoundException extends RuntimeException {
    
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    public EmployeeNotFoundException(Long id) {
        super("Сотрудник с ID " + id + " не найден");
    }
}