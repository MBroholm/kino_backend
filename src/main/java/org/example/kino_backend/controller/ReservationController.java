package org.example.kino_backend.controller;

import org.example.kino_backend.dto.*;
import org.example.kino_backend.model.Reservation;
import org.example.kino_backend.service.ReservationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @PostMapping
    public ResponseEntity<ReservationDTO> create(@RequestBody CreateReservationRequest req) {
        Reservation reservation = reservationService.create(req);
        ReservationDTO dto = ReservationDTO.fromEntity(reservation);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}