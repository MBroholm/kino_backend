package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String username;
    private String password;

    @ElementCollection(targetClass = EmployeeRole.class)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeRole> role = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;
}
