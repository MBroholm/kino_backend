package org.example.kino_backend.dto;

import org.example.kino_backend.model.EmployeeRole;

import java.util.Set;

public record UpdateEmployeeRequest (
        String username,
        String password,
        Set<EmployeeRole> roles
) {}
