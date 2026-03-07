package org.example.kino_backend.dto;

import java.time.LocalDateTime;

public record CreateShowingRequest(
        Long movieId,
        Long theatreId,
        LocalDateTime startTime,
        double price
) {}

