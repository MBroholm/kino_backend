package org.example.kino_backend.dto;

import org.example.kino_backend.model.Reservation;
import java.time.LocalDateTime;
import java.util.List;

public record ReservationDTO(
        String referenceNumber,
        String movieTitle,
        int theatreNumber,
        LocalDateTime startTime,
        LocalDateTime endTime,
        List<ReservationSeatDTO> seats
) {
    public static ReservationDTO fromEntity(Reservation reservation) {
        List<ReservationSeatDTO> seats = reservation.getReservationSeats().stream()
                .map(rs -> new ReservationSeatDTO(
                        rs.getSeat().getSeatRow().getSeatRowNumber(),
                        rs.getSeat().getSeatNumber()
                ))
                .toList();

        return new ReservationDTO(
                reservation.getReferenceNumber(),
                reservation.getShowing().getMovie().getTitle(),
                reservation.getShowing().getTheatre().getTheatreNumber(),
                reservation.getShowing().getStartTime(),
                reservation.getShowing().getEndTime(),
                seats
        );
    }
}
