package ru.ranepa.hrm.service;

import ru.ranepa.hrm.model.Employee;
import ru.ranepa.hrm.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class HRMService {
    private EmployeeRepository repository;

    // Конструктор — получает репозиторий
    public HRMService(EmployeeRepository repository) {
        this.repository = repository;
    }

    // Добавить сотрудника
    public Employee addEmployee(String name, String position, double salary, java.time.LocalDate hireDate) {
        Employee employee = new Employee(name, position, salary, hireDate);
        return repository.save(employee);
    }

    // Получить всех сотрудников
    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    // Найти сотрудника по ID
    public Optional<Employee> findEmployeeById(Long id) {
        return repository.findById(id);
    }

    // Удалить сотрудника по ID
    public boolean deleteEmployee(Long id) {
        return repository.delete(id);
    }

    // Средняя зарплата по компании
    public double calculateAverageSalary() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty()) {
            return 0.0;
        }
        double sum = employees.stream()
                .mapToDouble(Employee::getSalary)
                .sum();
        return sum / employees.size();
    }

    // Самый высокооплачиваемый сотрудник
    public Optional<Employee> findTopPaidEmployee() {
        List<Employee> employees = repository.findAll();
        return employees.stream()
                .max((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
    }

    // Фильтрация по должности
    public List<Employee> filterByPosition(String position) {
        return repository.findAll().stream()
                .filter(e -> e.getPosition().equalsIgnoreCase(position))
                .collect(Collectors.toList());
    }

}