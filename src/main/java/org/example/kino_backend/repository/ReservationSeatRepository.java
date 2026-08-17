package org.example.kino_backend.repository;

import org.example.kino_backend.model.ReservationSeat;
import org.example.kino_backend.model.ReservationSeatId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationSeatRepository extends JpaRepository<ReservationSeat, ReservationSeatId> {
}
