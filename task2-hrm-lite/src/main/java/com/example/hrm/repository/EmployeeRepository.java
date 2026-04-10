package com.example.hrm.repository;

import com.example.hrm.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    
    List<Employee> findByPosition(String position);
    
    List<Employee> findBySalaryGreaterThanEqual(BigDecimal salary);
    
    Page<Employee> findAllByOrderByNameAsc(Pageable pageable);
}