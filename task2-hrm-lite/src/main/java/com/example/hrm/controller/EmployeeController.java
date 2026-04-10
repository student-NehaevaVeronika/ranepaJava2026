package com.example.hrm.controller;

import com.example.hrm.dto.EmployeeRequestDto;
import com.example.hrm.dto.EmployeeResponseDto;
import com.example.hrm.dto.EmployeeStatsDto;
import com.example.hrm.service.EmployeeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
@Tag(name = "Employee Controller", description = "API для управления сотрудниками")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    @Operation(summary = "Получить всех сотрудников")
    public ResponseEntity<List<EmployeeResponseDto>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @GetMapping("/paged")
    @Operation(summary = "Получить сотрудников с пагинацией")
    public ResponseEntity<Page<EmployeeResponseDto>> getAllEmployeesPaginated(
            @PageableDefault(size = 10, sort = "name", direction = Sort.Direction.ASC) 
            Pageable pageable) {
        return ResponseEntity.ok(employeeService.getAllEmployeesPaginated(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить сотрудника по ID")
    public ResponseEntity<EmployeeResponseDto> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @PostMapping
    @Operation(summary = "Создать нового сотрудника")
    public ResponseEntity<EmployeeResponseDto> createEmployee(
            @Valid @RequestBody EmployeeRequestDto requestDto) {
        EmployeeResponseDto created = employeeService.createEmployee(requestDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные сотрудника")
    public ResponseEntity<EmployeeResponseDto> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDto requestDto) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить сотрудника")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/position/{position}")
    @Operation(summary = "Найти сотрудников по должности")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesByPosition(
            @PathVariable String position) {
        return ResponseEntity.ok(employeeService.getEmployeesByPosition(position));
    }

    @GetMapping("/salary/{minSalary}")
    @Operation(summary = "Найти сотрудников с зарплатой выше указанной")
    public ResponseEntity<List<EmployeeResponseDto>> getEmployeesBySalaryGreaterThan(
            @PathVariable BigDecimal minSalary) {
        return ResponseEntity.ok(employeeService.getEmployeesBySalaryGreaterThan(minSalary));
    }

    @GetMapping("/stats")
    @Operation(summary = "Получить статистику по сотрудникам")
    public ResponseEntity<EmployeeStatsDto> getEmployeeStatistics() {
        return ResponseEntity.ok(employeeService.getEmployeeStatistics());
    }
}