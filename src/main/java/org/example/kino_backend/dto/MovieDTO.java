package org.example.kino_backend.dto;

import org.example.kino_backend.model.Category;
import org.example.kino_backend.model.Movie;

import java.util.Set;

public record MovieDTO(
        Long movieId,
        String title,
        int ageLimit,
        int duration,
        Set<Category> categories,
        String description
) {
    public static MovieDTO fromEntity(Movie movie) {
        return new MovieDTO(
                movie.getMovieId(),
                movie.getTitle(),
                movie.getAgeLimit(),
                movie.getDuration(),
                movie.getCategories(),
                movie.getDescription()
        );
    }
}
