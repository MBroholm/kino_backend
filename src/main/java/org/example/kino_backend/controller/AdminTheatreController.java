package org.example.kino_backend.controller;


import org.example.kino_backend.dto.CreateTheatreRequest;
import org.example.kino_backend.dto.TheatreDTO;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.service.TheatreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseEntity<TheatreDTO> create(@RequestBody CreateTheatreRequest req) {
        Theatre theatre = theatreService.create(req);
        TheatreDTO dto = TheatreDTO.fromEntity(theatre);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping
    public ResponseEntity<TheatreDTO> update(@PathVariable Long id, @RequestBody CreateTheatreRequest req) {
        Theatre theatre = theatreService.update(id, req);
        TheatreDTO dto = TheatreDTO.fromEntity(theatre);

        return ResponseEntity.ok(dto);
    }
}
