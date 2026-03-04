package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    @ElementCollection(targetClass = Category.class)
    @Enumerated(EnumType.STRING)
    private Set<Category> categories = new HashSet<>();

    private int ageLimit;
    private int duration;
    private String description;

    @OneToMany(mappedBy = "movie")
    private Set<Showing> showings = new HashSet<>();


}
