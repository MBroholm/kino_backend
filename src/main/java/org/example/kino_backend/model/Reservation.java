package org.example.kino_backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;

    private String referenceNumber;
    private String customerName;
    private String customerPhone;
    private String customerEmail;

    @OneToMany(mappedBy = "reservation")
    private Set<ReservationSeat> reservationSeats = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "showing_id")
    private Showing showing;
}
