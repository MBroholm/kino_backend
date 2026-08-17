package org.example.kino_backend.controller;

import org.example.kino_backend.dto.CreateEmployeeRequest;
import org.example.kino_backend.dto.UpdateEmployeeRequest;
import org.example.kino_backend.dto.EmployeeDTO;
import org.example.kino_backend.model.Employee;
import org.example.kino_backend.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/employees")
public class AdminEmployeeController {
    private final EmployeeService employeeService;

    public AdminEmployeeController(EmployeeService employeeService) { this.employeeService = employeeService; }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        List<EmployeeDTO> employeeDTOS = employeeService.findAll()
                .stream()
                .map(EmployeeDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(employeeDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> findById(@PathVariable Long id) {
        return employeeService.findById(id)
                .map(EmployeeDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> create(@RequestBody CreateEmployeeRequest req) {
        Employee employee = employeeService.createEmployee(req);
        EmployeeDTO dto = EmployeeDTO.fromEntity(employee);
        return ResponseEntity.status(201).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> update(@PathVariable Long id, @RequestBody UpdateEmployeeRequest req) {
        Employee employee = employeeService.updateEmployee(id, req);
        return ResponseEntity.ok(EmployeeDTO.fromEntity(employee));
    }
}
