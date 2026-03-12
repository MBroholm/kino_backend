package org.example.kino_backend.controller;

import org.example.kino_backend.model.Reservation;
import org.example.kino_backend.service.ReservationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/reservations")
public class AdminReservationController extends CrudRestController<Reservation, Long> {

    public AdminReservationController(ReservationService service) {
        super(service);
    }
}
