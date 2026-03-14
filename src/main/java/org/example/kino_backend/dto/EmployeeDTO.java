package org.example.kino_backend.dto;

import org.example.kino_backend.model.Employee;
import org.example.kino_backend.model.EmployeeRole;

import java.util.Set;

public record EmployeeDTO(
    Long employeeId,
    String username,
    Set<EmployeeRole> roles
) {
    public static EmployeeDTO fromEntity(Employee employee) {
        return new EmployeeDTO(
                employee.getEmployeeId(),
                employee.getUsername(),
                employee.getRole()
        );
    }
}
