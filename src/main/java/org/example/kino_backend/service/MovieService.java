package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateMovieRequest;
import org.example.kino_backend.dto.UpdateMovieRequest;
import org.example.kino_backend.model.Movie;
import org.example.kino_backend.model.Showing;
import org.example.kino_backend.repository.MovieRepository;
import org.springframework.stereotype.Service;

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

    public Movie create(CreateMovieRequest request) {
        Movie movie = new Movie();
        movie.setTitle(request.title());
        movie.setAgeLimit(request.ageLimit());
        movie.setDuration(request.duration());
        movie.setCategories(request.categories());
        movie.setDescription(request.description());

        return save(movie);
    }

    public Movie update(Long id, UpdateMovieRequest request) {
        Movie movie = movieRepository.findById(id)
                .orElseThrow( () -> new IllegalArgumentException("Movie not found: " + id));

        movie.setTitle(request.title());
        movie.setAgeLimit(request.ageLimit());
        movie.setDuration(request.duration());
        movie.setCategories(request.categories());
        movie.setDescription(request.description());

        return save(movie);
    }
}
