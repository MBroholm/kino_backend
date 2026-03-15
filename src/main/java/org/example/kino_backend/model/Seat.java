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
    @JoinColumn(name = "seat_row_id")
    private SeatRow seatRow;

    @OneToMany(mappedBy = "seat")
    private Set<ReservationSeat> reservationSeats = new HashSet<>();

    @Column(nullable = false)
    private int seatNumber;

    public Seat() {}

    public Seat(SeatRow seatRow, int seatNumber) {
        this.seatRow = seatRow;
        this.seatNumber = seatNumber;
    }

}
