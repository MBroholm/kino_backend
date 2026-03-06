package org.example.kino_backend.controller;


import org.example.kino_backend.service.ReservationSeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservation-seats")
public class ReservationSeatController {

    public ReservationSeatController(ReservationSeatService service) {

    }
}
