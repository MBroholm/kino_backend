package org.example.kino_backend.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Reservation implements Identifiable<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReservationStatus status;

    private String referenceNumber;
    private String customerName;
    private String customerPhone;
    private String customerEmail;

    @OneToMany(mappedBy = "reservation", cascade = CascadeType.ALL)
    private Set<ReservationSeat> reservationSeats = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "showing_id")
    private Showing showing;

    @PrePersist
    private void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.status = ReservationStatus.RESERVED;
        this.referenceNumber = generateReferenceNumber();
    }

    @Override
    public void setId(Long id) {
        this.reservationId = id;
    }

    @Override
    public Long getId() {
        return reservationId;
    }

    private String generateReferenceNumber() {
        return UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0,10)
                .toUpperCase();
    }
}
