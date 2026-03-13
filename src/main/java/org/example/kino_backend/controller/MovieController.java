package org.example.kino_backend.controller;

import org.example.kino_backend.dto.MovieDTO;
import org.example.kino_backend.dto.ShowingDTO;
import org.example.kino_backend.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public ResponseEntity<List<MovieDTO>> findAll() {
        List<MovieDTO> dtos = movieService.findAll()
                .stream()
                .map(MovieDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieDTO> findById(Long id) {
        return movieService.findById(id)
                .map(MovieDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/showings")
    public ResponseEntity<List<ShowingDTO>> getShowings(@PathVariable Long id) {
        if(!movieService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        List<ShowingDTO> dtos = movieService.getShowingsForMovie(id)
                .stream()
                .map(ShowingDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}