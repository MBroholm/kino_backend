package org.example.kino_backend.dto;

import org.example.kino_backend.model.Seat;

public record ShowingSeatDTO(
        Long seatId,
        int rowNumber,
        int seatNumber,
        boolean occupied
)

{
    public static ShowingSeatDTO fromEntity(Seat seat, boolean occupied){
        return new ShowingSeatDTO(
                seat.getSeatId(),
                seat.getSeatRow().getSeatRowNumber(),
                seat.getSeatNumber(),
                occupied
        );
    }
}
