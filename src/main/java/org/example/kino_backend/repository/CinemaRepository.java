package org.example.kino_backend.repository;

import org.example.kino_backend.model.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CinemaRepository extends JpaRepository<Cinema, Long> {
    boolean existsByName(String name);
}
