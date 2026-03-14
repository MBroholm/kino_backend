package org.example.kino_backend.controller;

import org.example.kino_backend.model.Category;
import org.example.kino_backend.model.EmployeeRole;
import org.example.kino_backend.model.ReservationStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/metadata")
public class MetadataController {

    @GetMapping("/categories")
    public ResponseEntity<Category[]> getCategories() {
        return ResponseEntity.ok(Category.values());
    }

    @GetMapping("/employee-roles")
    public ResponseEntity<EmployeeRole[]> getEmployeeRoles() {
        return ResponseEntity.ok(EmployeeRole.values());
    }

    @GetMapping("/reservation-statuses")
    public ResponseEntity<ReservationStatus[]> getReservationStatuses() {
        return ResponseEntity.ok(ReservationStatus.values());
    }
}

