package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    @ManyToOne
    @JoinColumn(name = "row_id")
    private Row row;

    @OneToMany(mappedBy = "seat")
    private Set<ReservationSeat> reservationSeats = new HashSet<>();

    private int seatNumber;
}
