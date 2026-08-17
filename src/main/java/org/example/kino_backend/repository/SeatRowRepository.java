package org.example.kino_backend.repository;

import org.example.kino_backend.model.SeatRow;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRowRepository extends JpaRepository<SeatRow, Long> {
}
