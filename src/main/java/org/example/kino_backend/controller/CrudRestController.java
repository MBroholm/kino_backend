package org.example.kino_backend.controller;

import org.example.kino_backend.model.Identifiable;
import org.example.kino_backend.service.CrudService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class CrudRestController<T extends Identifiable<ID>, ID> {

    protected final CrudService<T, ID> service;

    protected CrudRestController(CrudService<T, ID> service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<T>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<T> findById(@PathVariable ID id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<T> create(@RequestBody T entity) {
        return ResponseEntity.ok(service.save(entity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        entity.setId(id);
        return ResponseEntity.ok(service.save(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        if (!service.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
