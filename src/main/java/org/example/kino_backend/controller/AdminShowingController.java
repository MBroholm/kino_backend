package org.example.kino_backend.controller;

import org.example.kino_backend.dto.CreateShowingRequest;
import org.example.kino_backend.dto.ShowingDTO;
import org.example.kino_backend.dto.UpdateShowingRequest;
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

    @PutMapping("/{id}")
    public ResponseEntity<ShowingDTO> update(@PathVariable Long id, @RequestBody UpdateShowingRequest req) {
        Showing showing = showingService.update(id, req);
        ShowingDTO dto = ShowingDTO.fromEntity(showing);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!showingService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        showingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
