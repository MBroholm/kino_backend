package org.example.kino_backend.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theatreId;

    @Column(nullable = false)
    private int theatreNumber;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatRow> seatRows = new ArrayList<>();

    @OneToMany(mappedBy = "theatre")
    private Set<Showing> showings = new HashSet<>();

    public Theatre() {}

    public Theatre(int theatreNumber, Cinema cinema, int rowQty, int seatsPerRow) {
        if (theatreNumber <= 0) {
            throw new IllegalArgumentException("Theatre number must be positive");
        }
        if (rowQty <= 0) {
            throw new IllegalArgumentException("Row quantity must be positive");
        }
        if (seatsPerRow <= 0) {
            throw new IllegalArgumentException("Seats per row must be positive");
        }

        this.theatreNumber = theatreNumber;
        this.cinema = cinema;
        initializeSeatRows(rowQty, seatsPerRow);
    }

    public void initializeSeatRows(int rowQty, int seatQty) {
        if (rowQty <= 0) {
            throw new IllegalArgumentException("Row quantity must be positive");
        }
        if (seatQty <= 0) {
            throw new IllegalArgumentException("Seat quantity must be positive");
        }

        seatRows.clear();

        for (int i = 1; i <= rowQty; i++) {
            SeatRow seatRow = new SeatRow(this, i, seatQty);
            addSeatRow(seatRow);
        }
    }

    public void addSeatRow(SeatRow seatRow) {
        if (seatRow == null) {
            throw new IllegalArgumentException("Seat row cannot be null");
        }
        seatRow.setTheatre(this);
        seatRows.add(seatRow);
    }

    public void addSeatRow(int seatQty) {
        if (seatQty <= 0) {
            throw new IllegalArgumentException("Seat quantity must be positive");
        }

        int nextRowNo = getNextSeatRowNumber();
        SeatRow seatRow = new SeatRow(this, nextRowNo, seatQty);
        addSeatRow(seatRow);
    }

    private int getNextSeatRowNumber() {
        return seatRows.isEmpty() ? 1 : seatRows.getLast().getSeatRowNumber() + 1;
    }

}