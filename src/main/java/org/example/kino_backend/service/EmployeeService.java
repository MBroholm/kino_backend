package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateEmployeeRequest;
import org.example.kino_backend.dto.UpdateEmployeeRequest;
import org.example.kino_backend.model.Employee;
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

    public Employee createEmployee(CreateEmployeeRequest req) {
        Employee employee = new Employee();
        employee.setUsername(req.username());
        employee.setPasswordHash(encoder.encode(req.password()));
        employee.getRole().addAll(req.roles());
        return save(employee);
    }

    public Optional<Employee> authenticate(String username, String rawPassword) {
        return employeeRepository.findByUsername(username)
                .filter(u -> encoder.matches(rawPassword, u.getPasswordHash()));
    }

    public Employee updateEmployee(Long id, UpdateEmployeeRequest req) {
        Employee employee = employeeRepository.findById(id).orElseThrow();
        if (req.username() != null) {
            employee.setUsername(req.username());
        }
        if (req.password() != null && !req.password().isEmpty()) {
            employee.setPasswordHash(encoder.encode(req.password()));
        }
        if (req.roles() != null) {
            employee.getRole().clear();
            employee.getRole().addAll(req.roles());
        }
        return save(employee);
    }
}
