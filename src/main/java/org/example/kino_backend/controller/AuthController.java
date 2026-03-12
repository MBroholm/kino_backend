package org.example.kino_backend.controller;

import org.example.kino_backend.dto.LoginRequest;
import org.example.kino_backend.dto.LoginResponse;
import org.example.kino_backend.security.JwtUtil;
import org.example.kino_backend.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final EmployeeService employeeService;
    private final JwtUtil jwtUtil;

    public AuthController(EmployeeService employeeService, JwtUtil jwtUtil) {
        this.employeeService = employeeService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return employeeService.authenticate(request.username(), request.password())
                .map(employee -> {
                    String token = jwtUtil.generateToken(employee.getUsername());
                    return ResponseEntity.ok(new LoginResponse(token));
                })
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                .build()
                );
    }
}
