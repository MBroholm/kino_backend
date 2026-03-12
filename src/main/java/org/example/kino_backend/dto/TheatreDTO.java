package org.example.kino_backend.dto;

import org.example.kino_backend.model.Theatre;

public record TheatreDTO(
        Long theatreId,
        int theatreNumber,
        int numberOfRows,
        int seatsPerRow
) {
    public static TheatreDTO fromEntity(Theatre theatre) {
        int rows = theatre.getSeatRows().size();
        int seats = rows > 0 ? theatre.getSeatRows().get(0).getSeats().size() : 0;
        return new TheatreDTO(
                theatre.getTheatreId(),
                theatre.getTheatreNumber(),
                rows,
                seats
        );
    }
}
