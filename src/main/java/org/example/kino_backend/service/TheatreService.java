package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateTheatreRequest;
import org.example.kino_backend.model.Cinema;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.repository.CinemaRepository;
import org.example.kino_backend.repository.TheatreRepository;
import org.springframework.stereotype.Service;

@Service
public class TheatreService extends CrudServiceImpl<Theatre, Long> {
    private final CinemaRepository cinemaRepository;

    public TheatreService(TheatreRepository theatreRepository, CinemaRepository cinemaRepository) {
        super(theatreRepository);
        this.cinemaRepository = cinemaRepository;
    }

    public Theatre create(CreateTheatreRequest req) {
        Cinema cinema = cinemaRepository.findById(req.cinemaId())
                .orElseThrow(() -> new IllegalArgumentException("Cinema not found: " + req.cinemaId()));

        Theatre theatre = new Theatre(req.theatreNumber(), cinema, req.numberOfRows(), req.seatsPerRow());
        cinema.addTheatre(theatre);

        return save(theatre);
    }
}
