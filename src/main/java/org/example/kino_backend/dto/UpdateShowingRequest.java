package org.example.kino_backend.dto;

import java.time.LocalDateTime;

public record UpdateShowingRequest(
        Long movieId,
        Long theatreId,
        LocalDateTime startTime,
        double price
) {}

