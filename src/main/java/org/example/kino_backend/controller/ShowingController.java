package org.example.kino_backend.controller;

import org.example.kino_backend.dto.CreateShowingRequest;
import org.example.kino_backend.dto.ShowingDTO;
import org.example.kino_backend.dto.ShowingSeatDTO;
import org.example.kino_backend.model.Showing;
import org.example.kino_backend.service.ShowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/showings")
public class ShowingController {

    private final ShowingService showingService;

    public ShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }

    @GetMapping
    public ResponseEntity<List<ShowingDTO>> findAll() {
        List<ShowingDTO> dtos = showingService.findAll()
                .stream()
                .map(ShowingDTO::fromEntity)
                .toList();

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShowingDTO> findById(@PathVariable Long id) {
        return showingService.findById(id)
                .map(ShowingDTO::fromEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<ShowingSeatDTO>> getSeats(@PathVariable Long id) {
        return ResponseEntity.ok(showingService.getSeatsForShowing(id));
    }

    @PostMapping
    public ResponseEntity<ShowingDTO> create(@RequestBody CreateShowingRequest req) {
        Showing showing = showingService.create(req);
        ShowingDTO dto = ShowingDTO.fromEntity(showing);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
