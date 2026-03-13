package org.example.kino_backend.config;

import org.example.kino_backend.model.*;
import org.example.kino_backend.repository.*;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Component
public class InitData implements CommandLineRunner {

    private final CinemaRepository cinemaRepository;
    private final MovieRepository movieRepository;
    private final EmployeeRepository employeeRepository;
    private final ShowingRepository showingRepository;
    private final TheatreRepository theatreRepository;

    public InitData(CinemaRepository cinemaRepository, MovieRepository movieRepository, EmployeeRepository employeeRepository, ShowingRepository showingRepository, TheatreRepository theatreRepository) {
        this.cinemaRepository = cinemaRepository;
        this.movieRepository = movieRepository;
        this.employeeRepository = employeeRepository;
        this.showingRepository = showingRepository;
        this.theatreRepository = theatreRepository;
    }

    @Override
    public void run(String @NonNull ... args) throws Exception {
        initCinemaWithTheatres();
        initMovies();
        initAdmin();
        initShowings();
    }

    private void initCinemaWithTheatres() {
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

    private void initMovies() {

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

    private void initAdmin() {
        boolean exists = employeeRepository.existsByUsername("admin");

        if (exists) {
            System.out.println("Employee 'admin' already exists — skipping init.");
            return;
        }

        Employee admin = new Employee();
        admin.setUsername("admin");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String passwordHash = encoder.encode("admin");
        admin.setPasswordHash(passwordHash);

        employeeRepository.save(admin);

        System.out.println("Employee 'admin' initialized");
    }

    private void initShowings() {
        if (showingRepository.count() > 0) {
            System.out.println("Showings already exist — skipping init.");
            return;
        }

        List<Movie> movies = movieRepository.findAll();
        List<Theatre> theatres = theatreRepository.findAll();

        if (movies.size() < 20 || theatres.size() < 2) {
            System.out.println("Not enough movies or theatres to create showings.");
            return;
        }

        Theatre theatre1 = theatres.get(0);
        Theatre theatre2 = theatres.get(1);

        LocalDate baseDate = LocalDate.now().plusDays(1);

        List<Showing> showings = List.of(
                // ---------- DAY 1 ----------
                createShowing(movies.get(0), theatre1, baseDate.atTime(10, 0), 95),
                createShowing(movies.get(1), theatre2, baseDate.atTime(10, 30), 110),
                createShowing(movies.get(2), theatre1, baseDate.atTime(13, 0), 85),
                createShowing(movies.get(3), theatre2, baseDate.atTime(13, 30), 100),
                createShowing(movies.get(4), theatre1, baseDate.atTime(16, 0), 120),
                createShowing(movies.get(5), theatre2, baseDate.atTime(16, 30), 105),
                createShowing(movies.get(6), theatre1, baseDate.atTime(19, 0), 80),
                createShowing(movies.get(7), theatre2, baseDate.atTime(19, 30), 115),
                createShowing(movies.get(8), theatre1, baseDate.atTime(21, 30), 75),
                createShowing(movies.get(9), theatre2, baseDate.atTime(22, 0), 110),

                // ---------- DAY 2 ----------
                createShowing(movies.get(10), theatre1, baseDate.plusDays(1).atTime(10, 0), 90),
                createShowing(movies.get(11), theatre2, baseDate.plusDays(1).atTime(10, 30), 100),
                createShowing(movies.get(12), theatre1, baseDate.plusDays(1).atTime(13, 0), 85),
                createShowing(movies.get(13), theatre2, baseDate.plusDays(1).atTime(13, 30), 120),
                createShowing(movies.get(14), theatre1, baseDate.plusDays(1).atTime(16, 0), 80),
                createShowing(movies.get(15), theatre2, baseDate.plusDays(1).atTime(16, 30), 105),
                createShowing(movies.get(16), theatre1, baseDate.plusDays(1).atTime(19, 0), 75),
                createShowing(movies.get(17), theatre2, baseDate.plusDays(1).atTime(19, 30), 110),
                createShowing(movies.get(18), theatre1, baseDate.plusDays(1).atTime(22, 0), 95),
                createShowing(movies.get(19), theatre2, baseDate.plusDays(1).atTime(22, 30), 125),

                // ---------- DAY 3 (reuse movies 0–9) ----------
                createShowing(movies.get(0), theatre1, baseDate.plusDays(2).atTime(10, 0), 95),
                createShowing(movies.get(1), theatre2, baseDate.plusDays(2).atTime(10, 30), 110),
                createShowing(movies.get(2), theatre1, baseDate.plusDays(2).atTime(13, 0), 85),
                createShowing(movies.get(3), theatre2, baseDate.plusDays(2).atTime(13, 30), 100),
                createShowing(movies.get(4), theatre1, baseDate.plusDays(2).atTime(16, 0), 120),
                createShowing(movies.get(5), theatre2, baseDate.plusDays(2).atTime(16, 30), 105),
                createShowing(movies.get(6), theatre1, baseDate.plusDays(2).atTime(19, 0), 80),
                createShowing(movies.get(7), theatre2, baseDate.plusDays(2).atTime(19, 30), 115),
                createShowing(movies.get(8), theatre1, baseDate.plusDays(2).atTime(21, 30), 75),
                createShowing(movies.get(9), theatre2, baseDate.plusDays(2).atTime(22, 0), 110),

                // ---------- DAY 4 (reuse movies 10–19) ----------
                createShowing(movies.get(10), theatre1, baseDate.plusDays(3).atTime(10, 0), 90),
                createShowing(movies.get(11), theatre2, baseDate.plusDays(3).atTime(10, 30), 100),
                createShowing(movies.get(12), theatre1, baseDate.plusDays(3).atTime(13, 0), 85),
                createShowing(movies.get(13), theatre2, baseDate.plusDays(3).atTime(13, 30), 120),
                createShowing(movies.get(14), theatre1, baseDate.plusDays(3).atTime(16, 0), 80),
                createShowing(movies.get(15), theatre2, baseDate.plusDays(3).atTime(16, 30), 105),
                createShowing(movies.get(16), theatre1, baseDate.plusDays(3).atTime(19, 0), 75),
                createShowing(movies.get(17), theatre2, baseDate.plusDays(3).atTime(19, 30), 110),
                createShowing(movies.get(18), theatre1, baseDate.plusDays(3).atTime(22, 0), 95),
                createShowing(movies.get(19), theatre2, baseDate.plusDays(3).atTime(22, 30), 125)
        );

        showingRepository.saveAll(showings);
        System.out.println("40 showings created.");
    }

    private Showing createShowing(Movie movie, Theatre theatre, LocalDateTime startTime, double price) {
        Showing showing = new Showing();
        showing.setMovie(movie);
        showing.setTheatre(theatre);
        showing.setStartTime(startTime);
        showing.setEndTime(startTime.plusMinutes(movie.getDuration()));
        showing.setPrice(price);
        return showing;
    }
}
