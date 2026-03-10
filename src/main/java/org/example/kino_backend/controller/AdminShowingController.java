package org.example.kino_backend.controller;

import org.example.kino_backend.dto.CreateShowingRequest;
import org.example.kino_backend.dto.ShowingDTO;
import org.example.kino_backend.model.Showing;
import org.example.kino_backend.service.ShowingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin/showings")
public class AdminShowingController {

    private final ShowingService showingService;

    public AdminShowingController(ShowingService showingService) {
        this.showingService = showingService;
    }


    @PostMapping
    public ResponseEntity<ShowingDTO> create(@RequestBody CreateShowingRequest req) {
        Showing showing = showingService.create(req);
        ShowingDTO dto = ShowingDTO.fromEntity(showing);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }
}
