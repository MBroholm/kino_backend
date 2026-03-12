package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateShowingRequest;
import org.example.kino_backend.dto.ShowingSeatDTO;
import org.example.kino_backend.model.Movie;
import org.example.kino_backend.model.ReservationStatus;
import org.example.kino_backend.model.Showing;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.repository.MovieRepository;
import org.example.kino_backend.repository.ShowingRepository;
import org.example.kino_backend.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ShowingService extends CrudServiceImpl<Showing, Long> {

    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;

    public ShowingService(
            ShowingRepository showingRepository,
            MovieRepository movieRepository,
            TheatreRepository theatreRepository
    ) {
        super(showingRepository);
        this.movieRepository = movieRepository;
        this.theatreRepository = theatreRepository;
    }

    public Showing create(CreateShowingRequest req) {
        // 1. Validate movie
        Movie movie = movieRepository.findById(req.movieId())
                .orElseThrow(() -> new IllegalArgumentException("Movie not found: " + req.movieId()));

        // 2. Validate theatre
        Theatre theatre = theatreRepository.findById(req.theatreId())
                .orElseThrow(() -> new IllegalArgumentException("Theatre not found: " + req.theatreId()));

        // 3. Validate start time
        if (req.startTime() == null) {
            throw new IllegalArgumentException("Start time cannot be null");
        }

        // 4. Validate price
        if (req.price() < 0) {
            throw new IllegalArgumentException("Price must be non-negative");
        }

        // 5. Create entity
        Showing showing = new Showing();
        showing.setMovie(movie);
        showing.setTheatre(theatre);
        showing.setStartTime(req.startTime());
        showing.setPrice(req.price());

        // 6. Save
        return save(showing); // inherited from CrudServiceImpl
    }

        // Returns all seats for the showing, marking those that are occupied
    public List<ShowingSeatDTO> getSeatsForShowing(Long showingId){
        Showing showing = findById(showingId)
                .orElseThrow(() -> new IllegalArgumentException("Showing not found: " + showingId));

        // Collect IDs of all seats that are taken — ignore cancelled reservations
        Set<Long> occupiedSeatIds = showing.getReservationSet().stream()
                .filter(r -> r.getStatus() != ReservationStatus.CANCELLED)
                .flatMap(r -> r.getReservationSeats().stream())
                .map(rs -> rs.getSeat().getSeatId())
                .collect(Collectors.toSet());

        // Map every seat in the theatre to a DTO, flagging it as occupied
        // if its ID exists in the occupied set
        return showing.getTheatre().getSeatRows().stream()
                .flatMap(row -> row.getSeats().stream())
                .map(seat -> ShowingSeatDTO.fromEntity(seat, occupiedSeatIds.contains(seat.getSeatId())))
                .toList();


    }
}
