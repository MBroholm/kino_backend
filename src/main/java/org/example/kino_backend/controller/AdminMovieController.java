package org.example.kino_backend.controller;


import org.example.kino_backend.dto.CreateMovieRequest;
import org.example.kino_backend.dto.MovieDTO;
import org.example.kino_backend.dto.UpdateMovieRequest;
import org.example.kino_backend.model.Movie;
import org.example.kino_backend.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(@PathVariable Long id, @RequestBody UpdateMovieRequest updateMovieRequest) {
        Movie movie = movieService.update(id, updateMovieRequest);
        MovieDTO dto = MovieDTO.fromEntity(movie);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!movieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
