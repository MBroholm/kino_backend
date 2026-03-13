package org.example.kino_backend.service;

import org.example.kino_backend.model.Movie;
import org.example.kino_backend.model.Showing;
import org.example.kino_backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class MovieService extends CrudServiceImpl<Movie, Long> {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        super(movieRepository);
        this.movieRepository = movieRepository;
    }

    public Set<Showing> getShowingsForMovie(Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + movieId));

        return movie.getShowings();
    }
}
