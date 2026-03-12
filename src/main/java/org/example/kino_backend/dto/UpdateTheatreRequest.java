package org.example.kino_backend.dto;

public record UpdateTheatreRequest(
        int theatreNumber,
        int numberOfRows,
        int seatsPerRow
) {}
