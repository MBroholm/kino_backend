package org.example.kino_backend.dto;

import org.example.kino_backend.model.Theatre;

public record TheatreDTO(
        Long theatreId,
        int theatreNumber
) {
    public static TheatreDTO fromEntity(Theatre theatre) {
        return new TheatreDTO(
                theatre.getTheatreId(),
                theatre.getTheatreNumber()
        );
    }
}

