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

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @ElementCollection(targetClass = EmployeeRole.class)
    @Enumerated(EnumType.STRING)
    private Set<EmployeeRole> role = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

}
