package org.example.kino_backend.dto;

import org.example.kino_backend.model.Category;

import java.util.Set;

public record UpdateMovieRequest(
        String title,
        int ageLimit,
        int duration,
        Set<Category> categories,
        String description
) {
}
