package org.example.kino_backend.controller;

import org.example.kino_backend.model.Seat;
import org.example.kino_backend.service.SeatService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/seats")
public class AdminSeatController extends CrudRestController<Seat, Long> {

    public AdminSeatController(SeatService service) {
        super(service);
    }
}
