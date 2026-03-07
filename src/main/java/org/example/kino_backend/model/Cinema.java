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

    public void addTheatre(Theatre theatre) {
        if (theatre == null) {
            throw new IllegalArgumentException("Theatre cannot be null");
        }
        theatre.setCinema(this);
        theatres.add(theatre);
    }

    @Override
    public void setId(Long id) {
        this.cinemaId = id;
    }

    @Override
    public Long getId() {
        return cinemaId;
    }
}