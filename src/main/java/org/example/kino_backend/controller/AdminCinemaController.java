package org.example.kino_backend.controller;


import org.example.kino_backend.model.Cinema;
import org.example.kino_backend.service.CinemaService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/cinemas")
public class AdminCinemaController extends CrudRestController<Cinema, Long> {

    public AdminCinemaController(CinemaService service) {
        super(service);
    }
}