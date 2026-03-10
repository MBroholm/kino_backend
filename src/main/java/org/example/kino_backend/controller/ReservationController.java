package org.example.kino_backend.controller;

import org.example.kino_backend.service.ReservationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    public ReservationController(ReservationService service) {
    }
}
