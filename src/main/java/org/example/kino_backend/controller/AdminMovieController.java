package org.example.kino_backend.controller;


import org.example.kino_backend.service.MovieService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin/movies")
public class AdminMovieController {

    final MovieService movieService;

    public AdminMovieController(MovieService movieService) {
        this.movieService = movieService;
    }


}
