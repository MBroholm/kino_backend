package org.example.kino_backend.model;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class ReservationSeatId {
    private Long reservation;
    private Long seat;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReservationSeatId that = (ReservationSeatId) o;
        return Objects.equals(reservation, that.reservation) && Objects.equals(seat, that.seat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(reservation, seat);
    }
}
