package org.example.kino_backend.controller;

import org.example.kino_backend.model.Employee;
import org.example.kino_backend.service.EmployeeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/employees")
public class AdminEmployeeController extends CrudRestController<Employee, Long> {

    public AdminEmployeeController(EmployeeService service) {
        super(service);
    }
}
