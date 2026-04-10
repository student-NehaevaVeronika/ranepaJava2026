package com.example.hrm.dto;

import java.math.BigDecimal;
import java.util.List;

public class EmployeeStatsDto {
    
    private long totalEmployees;
    private BigDecimal averageSalary;
    private BigDecimal maxSalary;
    private BigDecimal minSalary;
    private List<EmployeeResponseDto> topPaidEmployees;

    public EmployeeStatsDto() {
    }

    public EmployeeStatsDto(long totalEmployees, BigDecimal averageSalary, 
                            BigDecimal maxSalary, BigDecimal minSalary,
                            List<EmployeeResponseDto> topPaidEmployees) {
        this.totalEmployees = totalEmployees;
        this.averageSalary = averageSalary;
        this.maxSalary = maxSalary;
        this.minSalary = minSalary;
        this.topPaidEmployees = topPaidEmployees;
    }

    public long getTotalEmployees() {
        return totalEmployees;
    }

    public void setTotalEmployees(long totalEmployees) {
        this.totalEmployees = totalEmployees;
    }

    public BigDecimal getAverageSalary() {
        return averageSalary;
    }

    public void setAverageSalary(BigDecimal averageSalary) {
        this.averageSalary = averageSalary;
    }

    public BigDecimal getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(BigDecimal maxSalary) {
        this.maxSalary = maxSalary;
    }

    public BigDecimal getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(BigDecimal minSalary) {
        this.minSalary = minSalary;
    }

    public List<EmployeeResponseDto> getTopPaidEmployees() {
        return topPaidEmployees;
    }

    public void setTopPaidEmployees(List<EmployeeResponseDto> topPaidEmployees) {
        this.topPaidEmployees = topPaidEmployees;
    }
}