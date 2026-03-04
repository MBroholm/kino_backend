package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaId;

    private String name;
    private String address;

    @OneToMany(mappedBy = "cinema")
    private Set<Theatre> theatres = new HashSet<>();

    @OneToMany(mappedBy = "cinema")
    private Set<Employee> employees = new HashSet<>();
}
