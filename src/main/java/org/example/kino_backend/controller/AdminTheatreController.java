package org.example.kino_backend.controller;


import org.example.kino_backend.dto.TheatreDTO;
import org.example.kino_backend.service.TheatreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/theatres")
public class AdminTheatreController {

    private final TheatreService theatreService;

    public AdminTheatreController(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @GetMapping
    public ResponseEntity<List<TheatreDTO>> findAll() {
        List<TheatreDTO> dtos = theatreService.findAll()
                .stream()
                .map(TheatreDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }
}
