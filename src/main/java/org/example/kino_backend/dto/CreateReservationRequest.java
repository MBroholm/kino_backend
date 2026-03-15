package org.example.kino_backend.dto;


import java.util.List;

public record CreateReservationRequest(
        Long showingId,
        List<Long> seatIds
)
{}
