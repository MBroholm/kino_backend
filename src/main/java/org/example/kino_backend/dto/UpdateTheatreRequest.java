package org.example.kino_backend.dto;

public record UpdateTheatreRequest(
        int numberOfRows,
        int seatsPerRow
) {}
