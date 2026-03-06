package ru.ranepa.hrm.repository;

import ru.ranepa.hrm.model.Employee;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepository {
    private List<Employee> employees = new ArrayList<>();
    private Long nextId = 1L;

    // Сохранить нового сотрудника
    public Employee save(Employee employee) {
        employee.setId(nextId++);
        employees.add(employee);
        return employee;
    }

    // Получить всех сотрудников
    public List<Employee> findAll() {
        return new ArrayList<>(employees);
    }

    // Найти сотрудника по ID
    public Optional<Employee> findById(Long id) {
        return employees.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst();
    }

    // Удалить сотрудника по ID
    public boolean delete(Long id) {
        return employees.removeIf(e -> e.getId().equals(id));
    }

    // Получить количество сотрудников
    public int count() {
        return employees.size();
    }
}
