package ru.ranepa.hrm.presentation;

import ru.ranepa.hrm.model.Employee;
import ru.ranepa.hrm.repository.EmployeeRepository;
import ru.ranepa.hrm.service.HRMService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class HRMApplication {
    private static Scanner scanner = new Scanner(System.in);
    private static HRMService service;

    public static void main(String[] args) {
        // Инициализация
        EmployeeRepository repository = new EmployeeRepository();
        service = new HRMService(repository);

        System.out.println("Система управления персоналом HRM Lite");
        
        boolean running = true;
        while (running) {
            printMenu();
            int choice = readInt("Выберите опцию: ");
            
            switch (choice) {
                case 1:
                    showAllEmployees();
                    break;
                case 2:
                    addEmployee();
                    break;
                case 3:
                    deleteEmployee();
                    break;
                case 4:
                    findEmployeeById();
                    break;
                case 5:
                    showStatistics();
                    break;
                case 6:
                    filterByPosition();
                    break;
                case 0:
                    System.out.println("Выход из программы...");
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор. Попробуйте снова.");
            }
            System.out.println();
        }
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("HRM System Menu");
        System.out.println("1. Показать всех сотрудников");
        System.out.println("2. Добавить сотрудника");
        System.out.println("3. Удалить сотрудника по ID");
        System.out.println("4. Найти сотрудника по ID");
        System.out.println("5. Показать статистику");
        System.out.println("6. Фильтр сотрудников по должности");
        System.out.println("0. Выход");
    }

    private static void showAllEmployees() {
        List<Employee> employees = service.getAllEmployees();
        if (employees.isEmpty()) {
            System.out.println("Список сотрудников пуст.");
        } else {
            System.out.println("Список всех сотрудников");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    private static void addEmployee() {
        System.out.println("Добавление нового сотрудника");
        
        String name = readString("Введите имя: ");
        String position = readString("Введите должность: ");
        double salary = readDouble("Введите зарплату: ");
        LocalDate hireDate = readDate("Введите дату приема (ГГГГ-ММ-ДД): ");
        
        Employee emp = service.addEmployee(name, position, salary, hireDate);
        System.out.println("Сотрудник добавлен успешно! ID: " + emp.getId());
    }

    private static void deleteEmployee() {
        Long id = readLong("Введите ID сотрудника для удаления: ");
        boolean deleted = service.deleteEmployee(id);
        if (deleted) {
            System.out.println("Сотрудник с ID " + id + " удален.");
        } else {
            System.out.println("Сотрудник с ID " + id + " не найден.");
        }
    }

    private static void findEmployeeById() {
        Long id = readLong("Введите ID сотрудника: ");
        Optional<Employee> emp = service.findEmployeeById(id);
        if (emp.isPresent()) {
            System.out.println("Найден сотрудник:");
            System.out.println(emp.get());
        } else {
            System.out.println("Сотрудник с ID " + id + " не найден.");
        }
    }

    private static void showStatistics() {
        System.out.println("Статистика");
        double avgSalary = service.calculateAverageSalary();
        System.out.printf("Средняя зарплата: %.2f%n", avgSalary);
        
        Optional<Employee> topPaid = service.findTopPaidEmployee();
        if (topPaid.isPresent()) {
            System.out.println("Самый высокооплачиваемый сотрудник:");
            System.out.println(topPaid.get());
        } else {
            System.out.println("Нет данных о сотрудниках.");
        }
    }

    // 🔹 НОВЫЙ МЕТОД: Фильтрация по должности
    private static void filterByPosition() {
        System.out.println("Фильтр по должности");
        String position = readString("Введите должность для поиска: ");
        List<Employee> filtered = service.filterByPosition(position);
        
        if (filtered.isEmpty()) {
            System.out.println("Сотрудников с должностью '" + position + "' не найдено.");
        } else {
            System.out.println("Найдено сотрудников: " + filtered.size());
            for (Employee emp : filtered) {
                System.out.println(emp);
            }
        }
    }

    // Вспомогательные методы для безопасного ввода
    private static int readInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите целое число.");
            }
        }
    }

    private static long readLong(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Long.parseLong(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите число.");
            }
        }
    }

    private static double readDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ошибка! Введите число (например: 1500.50).");
            }
        }
    }

    private static String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static LocalDate readDate(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return LocalDate.parse(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Ошибка! Введите дату в формате ГГГГ-ММ-ДД (например: 2025-03-01).");
            }
        }
    }
}