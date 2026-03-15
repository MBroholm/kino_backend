package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "seatRow", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @Column(nullable = false)
    private int seatRowNumber;

    public SeatRow() {
    }

    public SeatRow(Theatre theatre, int seatRowNumber, int seatQty) {
        if (seatRowNumber <= 0) {
            throw new IllegalArgumentException("Row number must be positive");
        }
        if (seatQty <= 0) {
            throw new IllegalArgumentException("Seat quantity must be positive");
        }

        this.theatre = theatre;
        this.seatRowNumber = seatRowNumber;
        addSeats(seatQty);
    }

    public void addSeats(int qty) {
        if (qty <= 0) {
            throw new IllegalArgumentException("Seat quantity must be positive");
        }

        for (int i = 1; i <= qty; i++) {
            Seat seat = new Seat(this, i);
            addSeat(seat);
        }
    }

    public void addSeat(Seat seat) {
        if (seat == null) {
            throw new IllegalArgumentException("Seat cannot be null");
        }
        seat.setSeatRow(this);
        seats.add(seat);
    }

    public void addSeat() {
        int nextSeatNo = getNextSeatNumber();
        Seat seat = new Seat(this, nextSeatNo);
        addSeat(seat);
    }

    private int getNextSeatNumber() {
        return seats.isEmpty() ? 1 : seats.getLast().getSeatNumber() + 1;
    }

}