package org.example.kino_backend.controller;

import org.example.kino_backend.model.SeatRow;
import org.example.kino_backend.service.SeatRowService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/seat-rows")
public class AdminSeatRowController extends CrudRestController<SeatRow, Long> {

    public AdminSeatRowController(SeatRowService service) {
        super(service);
    }
}
