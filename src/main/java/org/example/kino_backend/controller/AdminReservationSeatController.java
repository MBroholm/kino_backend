package org.example.kino_backend.controller;


import org.example.kino_backend.service.ReservationSeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reservation-seats")
public class AdminReservationSeatController {

    public AdminReservationSeatController(ReservationSeatService service) {

    }
}
