package org.example.kino_backend.service;

import org.example.kino_backend.dto.CreateReservationRequest;
import org.example.kino_backend.model.*;
import org.example.kino_backend.repository.ReservationRepository;
import org.example.kino_backend.repository.SeatRepository;
import org.example.kino_backend.repository.ShowingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReservationService extends CrudServiceImpl<Reservation, Long> {

    private final ReservationRepository reservationRepository;
    private final ShowingRepository showingRepository;
    private final SeatRepository seatRepository;

    public ReservationService(
            ReservationRepository reservationRepository,
            ShowingRepository showingRepository,
            SeatRepository seatRepository
    ) {
        super(reservationRepository);
        this.reservationRepository = reservationRepository;
        this.showingRepository = showingRepository;
        this.seatRepository = seatRepository;
    }

    @Transactional
    public Reservation create(CreateReservationRequest req) {

        Showing showing = loadShowing(req.showingId());
        List<Seat> seats = loadSeat(req.seatIds());

        Set<Long> occupiedSeatIds = showing.getReservationSeats().stream()
                .filter(r -> r.getStatus() != ReservationStatus.CANCELLED)
                .flatMap(r -> r.getReservationSeats().stream())
                .map(rs -> rs.getSeat().getSeatId())
                .collect(Collectors.toSet());

        boolean hasConflict = seats.stream()
                .anyMatch(seat -> occupiedSeatIds.contains(seat.getSeatId()));

        if (hasConflict) {
            throw new IllegalArgumentException("One or more seats are already reserved");
        }
            Reservation reservation = new Reservation();
            reservation.setShowing(showing);
            for (Seat seat : seats) {
                ReservationSeat reservationSeat = new ReservationSeat();
                reservationSeat.setSeat(seat);
                reservationSeat.setReservation(reservation);
                reservation.getReservationSeats().add(reservationSeat);
            }
        return reservationRepository.save(reservation);
    }

    private Showing loadShowing(Long showingId) {
        return showingRepository.findById(showingId)
                .orElseThrow(() -> new IllegalArgumentException("Showing not found: " + showingId));
    }

    private List<Seat> loadSeat(List<Long> seatId) {
        return seatRepository.findAllById(seatId);
    }
}
