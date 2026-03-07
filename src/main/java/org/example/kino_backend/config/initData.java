package org.example.kino_backend.config;

import org.example.kino_backend.model.Category;
import org.example.kino_backend.model.Cinema;
import org.example.kino_backend.model.Movie;
import org.example.kino_backend.model.Theatre;
import org.example.kino_backend.repository.CinemaRepository;
import org.example.kino_backend.repository.MovieRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class initData implements CommandLineRunner {

    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;

    public initData(CinemaRepository cinemaRepository, MovieRepository movieRepository) {
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        initCinemaWithTheatres();
        initMovies();
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

    public void initMovies() {

        List<Movie> movies = List.of(
                new Movie("The Silent Horizon", 12, 118,
                        Set.of(Category.DRAMA, Category.THRILLER),
                        "A tense drama about a family stranded in the Arctic."),
                new Movie("Galactic Drift", 10, 132,
                        Set.of(Category.SCIENCEFICTION, Category.ACTION),
                        "A rogue pilot uncovers a conspiracy spanning galaxies."),
                new Movie("Love in Copenhagen", 7, 104,
                        Set.of(Category.ROMANCE, Category.COMEDY),
                        "Two strangers meet by chance and change each other's lives."),
                new Movie("Shadow of the Past", 15, 121,
                        Set.of(Category.THRILLER, Category.DRAMA),
                        "A detective confronts his own history while solving a murder."),
                new Movie("Dragonspire", 11, 140,
                        Set.of(Category.FANTASY, Category.ADVENTURE),
                        "A young mage must unite rival kingdoms to stop an ancient evil."),
                new Movie("Midnight Carnival", 16, 109,
                        Set.of(Category.HORROR),
                        "A cursed traveling carnival arrives in a quiet town."),
                new Movie("The Last Melody", 0, 95,
                        Set.of(Category.MUSICAL, Category.DRAMA),
                        "A composer struggles to finish his final masterpiece."),
                new Movie("Operation Ironclad", 13, 128,
                        Set.of(Category.ACTION, Category.HISTORICAL),
                        "A WWII commando team attempts an impossible rescue."),
                new Movie("Pixel Pals", 0, 88,
                        Set.of(Category.ANIMATED, Category.COMEDY),
                        "Two video‑game characters escape into the real world."),
                new Movie("Neon Streets", 15, 115,
                        Set.of(Category.ACTION, Category.THRILLER),
                        "A courier becomes the target of a cyber‑crime syndicate."),
                new Movie("The Forgotten Garden", 7, 102,
                        Set.of(Category.DRAMA, Category.FANTASY),
                        "A girl discovers a magical garden hidden behind her school."),
                new Movie("Beneath the Waves", 10, 124,
                        Set.of(Category.ADVENTURE),
                        "A marine biologist searches for a lost deep‑sea station."),
                new Movie("The Comedian’s Curse", 12, 99,
                        Set.of(Category.COMEDY, Category.FANTASY),
                        "A stand‑up comic accidentally summons a mischievous spirit."),
                new Movie("Red Moon Rising", 16, 137,
                        Set.of(Category.SCIENCEFICTION, Category.THRILLER),
                        "A lunar colony faces sabotage during a solar storm."),
                new Movie("The Chef’s Table", 0, 93,
                        Set.of(Category.COMEDY, Category.DRAMA),
                        "A stubborn chef tries to save his failing restaurant."),
                new Movie("Whispers in the Dark", 15, 112,
                        Set.of(Category.HORROR, Category.THRILLER),
                        "A journalist investigates a series of supernatural events."),
                new Movie("Starlight Academy", 0, 101,
                        Set.of(Category.ANIMATED, Category.FANTASY),
                        "A young girl trains to become a guardian of the cosmos."),
                new Movie("The Great Heist", 13, 125,
                        Set.of(Category.ACTION, Category.COMEDY),
                        "A group of amateurs attempt the biggest bank heist in Europe."),
                new Movie("Echoes of Time", 12, 119,
                        Set.of(Category.SCIENCEFICTION, Category.DRAMA),
                        "A physicist receives messages from her future self."),
                new Movie("The Winter King", 11, 142,
                        Set.of(Category.HISTORICAL, Category.DRAMA),
                        "A retelling of the rise of a legendary Nordic ruler.")
        );

        for (Movie movie : movies) {
            if (!movieRepository.existsByTitle(movie.getTitle())) {
                movieRepository.save(movie);
            }
        }

        System.out.println("Movie initialization complete.");
    }
}
