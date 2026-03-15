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

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int ageLimit;

    @Column(nullable = false)
    private int duration;

    private String description;

    @OneToMany(mappedBy = "movie")
    private Set<Showing> showings = new HashSet<>();

    public Movie() {
    }

    public Movie(String title, int ageLimit, int duration,
                 Set<Category> categories, String description) {
        this.title = title;
        this.ageLimit = ageLimit;
        this.duration = duration;
        this.categories = categories;
        this.description = description;
    }

}
