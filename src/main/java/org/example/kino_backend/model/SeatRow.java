package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class SeatRow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatRowId;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @OneToMany(mappedBy = "seatRow", cascade = CascadeType.ALL)
    private Set<Seat> seats = new HashSet<>();

    @Column(nullable = false)
    private int seatRowNumber;
}
