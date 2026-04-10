package com.example.hrm.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public class EmployeeRequestDto {

    @NotBlank(message = "Имя сотрудника обязательно")
    @Size(min = 2, max = 100, message = "Имя должно содержать от 2 до 100 символов")
    private String name;

    @NotBlank(message = "Должность обязательна")
    @Size(min = 2, max = 100, message = "Должность должна содержать от 2 до 100 символов")
    private String position;

    @NotNull(message = "Зарплата обязательна")
    @Positive(message = "Зарплата должна быть положительной")
    @DecimalMax(value = "10000000", message = "Зарплата не может превышать 10 000 000")
    private BigDecimal salary;

    @NotNull(message = "Дата найма обязательна")
    @PastOrPresent(message = "Дата найма не может быть в будущем")
    private LocalDate hireDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
}