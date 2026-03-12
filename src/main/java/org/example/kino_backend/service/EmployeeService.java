package org.example.kino_backend.service;

import org.example.kino_backend.model.Employee;
import org.example.kino_backend.model.EmployeeRole;
import org.example.kino_backend.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeService extends CrudServiceImpl<Employee, Long> {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder encoder;


    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        super(employeeRepository);
        this.employeeRepository = employeeRepository;
        this.encoder = passwordEncoder;
    }

    public Employee createEmployee(String username, String rawPassword) {
        Employee employee = new Employee();
        employee.setUsername(username);
        employee.setPasswordHash(encoder.encode(rawPassword));
        employee.getRole().add(EmployeeRole.ADMIN);
        return save(employee);
    }

    public Optional<Employee> authenticate(String username, String rawPassword) {
        return employeeRepository.findByUsername(username)
                .filter(u -> encoder.matches(rawPassword, u.getPasswordHash()));
    }
}
