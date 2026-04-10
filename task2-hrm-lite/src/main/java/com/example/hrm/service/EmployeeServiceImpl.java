package com.example.hrm.service;

import com.example.hrm.dto.EmployeeRequestDto;
import com.example.hrm.dto.EmployeeResponseDto;
import com.example.hrm.dto.EmployeeStatsDto;
import com.example.hrm.exception.EmployeeNotFoundException;
import com.example.hrm.model.Employee;
import com.example.hrm.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public EmployeeResponseDto createEmployee(EmployeeRequestDto requestDto) {
        Employee employee = new Employee(
                requestDto.getName(),
                requestDto.getPosition(),
                requestDto.getSalary(),
                requestDto.getHireDate()
        );
        
        Employee savedEmployee = employeeRepository.save(employee);
        return convertToResponseDto(savedEmployee);
    }

    @Override
    public EmployeeResponseDto getEmployeeById(Long id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return convertToResponseDto(employee);
    }

    @Override
    public List<EmployeeResponseDto> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<EmployeeResponseDto> getAllEmployeesPaginated(Pageable pageable) {
        return employeeRepository.findAllByOrderByNameAsc(pageable)
                .map(this::convertToResponseDto);
    }

    @Override
    public EmployeeResponseDto updateEmployee(Long id, EmployeeRequestDto requestDto) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        
        employee.setName(requestDto.getName());
        employee.setPosition(requestDto.getPosition());
        employee.setSalary(requestDto.getSalary());
        employee.setHireDate(requestDto.getHireDate());
        
        Employee updatedEmployee = employeeRepository.save(employee);
        return convertToResponseDto(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesByPosition(String position) {
        return employeeRepository.findByPosition(position).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeResponseDto> getEmployeesBySalaryGreaterThan(BigDecimal salary) {
        return employeeRepository.findBySalaryGreaterThanEqual(salary).stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeStatsDto getEmployeeStatistics() {
        List<Employee> allEmployees = employeeRepository.findAll();
        
        long totalEmployees = allEmployees.size();
        
        BigDecimal averageSalary = totalEmployees > 0 
                ? allEmployees.stream()
                        .map(Employee::getSalary)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
                        .divide(BigDecimal.valueOf(totalEmployees), 2, RoundingMode.HALF_UP)
                : BigDecimal.ZERO;
        
        BigDecimal maxSalary = allEmployees.stream()
                .map(Employee::getSalary)
                .max(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        
        BigDecimal minSalary = allEmployees.stream()
                .map(Employee::getSalary)
                .min(BigDecimal::compareTo)
                .orElse(BigDecimal.ZERO);
        
        List<EmployeeResponseDto> topPaidEmployees = allEmployees.stream()
                .sorted(Comparator.comparing(Employee::getSalary).reversed())
                .limit(3)
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
        
        return new EmployeeStatsDto(totalEmployees, averageSalary, 
                                    maxSalary, minSalary, topPaidEmployees);
    }

    private EmployeeResponseDto convertToResponseDto(Employee employee) {
        return new EmployeeResponseDto(
                employee.getId(),
                employee.getName(),
                employee.getPosition(),
                employee.getSalary(),
                employee.getHireDate(),
                employee.getCreatedAt()
        );
    }
}