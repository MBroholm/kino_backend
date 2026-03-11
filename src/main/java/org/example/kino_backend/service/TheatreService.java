package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateTheatreRequest;
import org.example.kino_backend.dto.UpdateTheatreRequest;
import org.example.kino_backend.model.Cinema;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.repository.CinemaRepository;
import org.example.kino_backend.repository.ShowingRepository;
import org.example.kino_backend.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TheatreService extends CrudServiceImpl<Theatre, Long> {
    private final CinemaRepository cinemaRepository;
    private final ShowingRepository showingRepository;

    public TheatreService(TheatreRepository theatreRepository,
                          CinemaRepository cinemaRepository,
                          ShowingRepository showingRepository) {
        super(theatreRepository);
        this.cinemaRepository = cinemaRepository;
        this.showingRepository = showingRepository;
    }

    public Theatre create(CreateTheatreRequest req) {
        Cinema cinema = cinemaRepository.findById(req.cinemaId())
                .orElseThrow(() -> new IllegalArgumentException("Cinema not found: " + req.cinemaId()));

        Theatre theatre = new Theatre(req.theatreNumber(), cinema, req.numberOfRows(), req.seatsPerRow());
        cinema.addTheatre(theatre);

        return save(theatre);
    }

    public Theatre update(Long id, UpdateTheatreRequest req) {

        Theatre theatre = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Theatre not found: " + id));

        validateNoUpcomingShowings(theatre);

        theatre.initializeSeatRows(req.numberOfRows(), req.seatsPerRow());
        return save(theatre);
    }


    public void delete(Long id) {
        Theatre theatre = findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Theatre not found: " + id));
        validateNoUpcomingShowings(theatre);
        deleteById(id);
    }

    //Helper methods
    private void validateNoUpcomingShowings(Theatre theatre) {
        if (showingRepository.existsByTheatreAndStartTimeAfter(theatre, LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot update theatre with upcoming showings");
        }
    }
}
