package com.example.hrm.service;

import com.example.hrm.dto.EmployeeRequestDto;
import com.example.hrm.dto.EmployeeResponseDto;
import com.example.hrm.dto.EmployeeStatsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface EmployeeService {
    
    EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto);
    
    EmployeeResponseDto getEmployeeById(Long id);
    
    List<EmployeeResponseDto> getAllEmployees();
    
    Page<EmployeeResponseDto> getAllEmployeesPaginated(Pageable pageable);
    
    EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto);
    
    void deleteEmployee(Long id);
    
    List<EmployeeResponseDto> getEmployeesByPosition(String position);
    
    List<EmployeeResponseDto> getEmployeesBySalaryGreaterThan(BigDecimal salary);
    
    EmployeeStatsDto getEmployeeStatistics();
}