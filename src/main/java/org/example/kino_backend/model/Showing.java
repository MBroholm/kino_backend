package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Showing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long showingId;

    private LocalDateTime startTime;
    private double price;

    @ManyToOne
    @JoinColumn(name = "movie", referencedColumnName = "movieId")
    private Movie movie;

    @ManyToOne
    @JoinColumn(name = "theatre", referencedColumnName = "theatreId")
    private Theatre theatre;

    @OneToMany(mappedBy = "showing")
    private Set<Reservation> reservationSet = new HashSet<>();
}
