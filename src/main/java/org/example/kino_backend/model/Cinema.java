package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Cinema implements Identifiable<Long>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cinemaId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @OneToMany(mappedBy = "cinema", cascade = CascadeType.ALL)
    private Set<Theatre> theatres = new HashSet<>();

    @OneToMany(mappedBy = "cinema")
    private Set<Employee> employees = new HashSet<>();

    @Override
    public void setId(Long id) {
        this.cinemaId = id;
    }

    @Override
    public Long getId() {
        return cinemaId;
    }
}
