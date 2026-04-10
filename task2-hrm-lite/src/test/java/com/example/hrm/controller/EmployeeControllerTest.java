package com.example.hrm.controller;

import com.example.hrm.dto.EmployeeRequestDto;
import com.example.hrm.dto.EmployeeResponseDto;
import com.example.hrm.dto.EmployeeStatsDto;
import com.example.hrm.exception.EmployeeNotFoundException;
import com.example.hrm.exception.GlobalExceptionHandler;
import com.example.hrm.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(EmployeeController.class)
@Import(GlobalExceptionHandler.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeResponseDto testEmployee;
    private EmployeeRequestDto validRequest;

    @BeforeEach
    void setUp() {
        testEmployee = new EmployeeResponseDto(
                1L,
                "Иван Иванов",
                "Java Developer",
                new BigDecimal("150000.00"),
                LocalDate.of(2024, 1, 15),
                LocalDate.now()
        );

        validRequest = new EmployeeRequestDto();
        validRequest.setName("Иван Иванов");
        validRequest.setPosition("Java Developer");
        validRequest.setSalary(new BigDecimal("150000.00"));
        validRequest.setHireDate(LocalDate.of(2024, 1, 15));
    }

    @Test
    void shouldReturnAllEmployees() throws Exception {
        when(employeeService.getAllEmployees())
                .thenReturn(Collections.singletonList(testEmployee));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Иван Иванов"));
    }

    @Test
    void shouldCreateEmployee() throws Exception {
        when(employeeService.createEmployee(any(EmployeeRequestDto.class)))
                .thenReturn(testEmployee);

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Иван Иванов"));
    }

    @Test
    void shouldReturn404ForNonExistentEmployee() throws Exception {
        when(employeeService.getEmployeeById(999L))
                .thenThrow(new EmployeeNotFoundException(999L));

        mockMvc.perform(get("/api/employees/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturn400ForInvalidEmployeeData() throws Exception {
        EmployeeRequestDto invalidRequest = new EmployeeRequestDto();

        mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldDeleteEmployee() throws Exception {
        mockMvc.perform(delete("/api/employees/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnStatistics() throws Exception {
        EmployeeStatsDto stats = new EmployeeStatsDto(
                1L,
                new BigDecimal("150000.00"),
                new BigDecimal("150000.00"),
                new BigDecimal("150000.00"),
                Collections.singletonList(testEmployee)
        );

        when(employeeService.getEmployeeStatistics()).thenReturn(stats);

        mockMvc.perform(get("/api/employees/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEmployees").value(1))
                .andExpect(jsonPath("$.averageSalary").value(150000.00));
    }

    @Test
    void shouldUpdateEmployee() throws Exception {
        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequestDto.class)))
                .thenReturn(testEmployee);

        mockMvc.perform(put("/api/employees/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Иван Иванов"));
    }
}