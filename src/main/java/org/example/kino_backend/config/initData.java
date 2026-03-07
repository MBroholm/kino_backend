package org.example.kino_backend.config;

import org.example.kino_backend.model.Cinema;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.repository.CinemaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class initData  implements CommandLineRunner {

    private final CinemaRepository cinemaRepository;

    public initData(CinemaRepository cinemaRepository) {
        this.cinemaRepository = cinemaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        initCinemaWithTheatres();
    }

    public void initCinemaWithTheatres() {
        // Check if cinema already exists
        boolean exists = cinemaRepository.existsByName("Kino");

        if (exists) {
            System.out.println("Cinema 'Kino' already exists — skipping init.");
            return;
        }

        // Create cinema
        Cinema cinema = new Cinema();
        cinema.setName("Kino");
        cinema.setAddress("Guldbergsgade 29N, 2200 København");

        // Create theatres
        Theatre theatre1 = new Theatre(1, cinema, 20, 12);
        cinema.addTheatre(theatre1);

        Theatre theatre2 = new Theatre(2, cinema, 25, 16);
        cinema.addTheatre(theatre2);

        // Save to DB
        cinemaRepository.save(cinema);

        System.out.println("Cinema 'Kino' created with theatres.");
    }
}
