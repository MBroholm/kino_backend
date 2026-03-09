package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateShowingRequest;
import org.example.kino_backend.model.Movie;
import org.example.kino_backend.model.Showing;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.repository.MovieRepository;
import org.example.kino_backend.repository.ShowingRepository;
import org.example.kino_backend.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShowingService extends CrudServiceImpl<Showing, Long> {

    private final ShowingRepository showingRepository;
    private final MovieRepository movieRepository;
    private final TheatreRepository theatreRepository;

    public ShowingService(
            ShowingRepository showingRepository,
            MovieRepository movieRepository,
            TheatreRepository theatreRepository
    ) {
        super(showingRepository);
        this.showingRepository = showingRepository;
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

        // 4. Validate no overlap
        LocalDateTime reqStart = req.startTime();
        LocalDateTime reqEnd = reqStart.plusMinutes(movie.getDuration());

        List<Showing> overlaps = showingRepository.findOverlappingShowings(
                theatre,
                reqStart,
                reqEnd
        );

        if (!overlaps.isEmpty()) {
            throw new IllegalArgumentException(
                    "Showing overlaps with existing showing starting at " +
                            overlaps.get(0).getStartTime()
            );
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
        showing.setEndTime(reqEnd);
        showing.setPrice(req.price());

        // 6. Save
        return save(showing); // inherited from CrudServiceImpl
    }
}
