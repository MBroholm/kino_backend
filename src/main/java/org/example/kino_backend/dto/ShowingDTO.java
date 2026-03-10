package org.example.kino_backend.dto;

import org.example.kino_backend.model.Movie;
import org.example.kino_backend.model.Showing;

import java.time.LocalDateTime;

public record ShowingDTO(
        Long showingId,
        LocalDateTime startTime,
        LocalDateTime endTime,
        double price,
        Long movieId,
        String movieTitle,
        Long theatreId,
        int theatreNumber
) {
    public static ShowingDTO fromEntity(Showing showing) {
        Movie movie = showing.getMovie();

        return new ShowingDTO(
                showing.getShowingId(),
                showing.getStartTime(),
                showing.getEndTime(),
                showing.getPrice(),
                showing.getMovie().getMovieId(),
                showing.getMovie().getTitle(),
                showing.getTheatre().getTheatreId(),
                showing.getTheatre().getTheatreNumber()
        );
    }
}
