package org.example.kino_backend.dto;

public record CreateTheatreRequest(
        int theatreNumber,
        Long cinemaId,
        int numberOfRows,
        int seatsPerRow
) {}