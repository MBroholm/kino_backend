package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class ReservationSeat {

    @EmbeddedId
    private ReservationSeatId id = new ReservationSeatId();

    @ManyToOne
    @MapsId("reservation")
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    @ManyToOne
    @MapsId("seat")
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private UUID ticketUuid;
    private boolean checkedIn;

    @PrePersist
    private void onCreate() {
        this.ticketUuid = UUID.randomUUID();
    }
}
