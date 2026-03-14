package org.example.kino_backend.controller;


import org.example.kino_backend.dto.CreateMovieRequest;
import org.example.kino_backend.dto.MovieDTO;
import org.example.kino_backend.model.Movie;
import org.example.kino_backend.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/movies")
public class AdminMovieController {

    final MovieService movieService;

    public AdminMovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping
    public ResponseEntity<MovieDTO> create(@RequestBody CreateMovieRequest request) {
        Movie movie = movieService.create(request);
        MovieDTO dto = MovieDTO.fromEntity(movie);

        return ResponseEntity.ok(dto);
    }
}
