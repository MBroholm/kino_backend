package org.example.kino_backend.model;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Theatre implements Identifiable<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theatreId;

    @Column(nullable = false)
    private int theatreNumber;

    @ManyToOne
    @JoinColumn(name = "cinema_id")
    private Cinema cinema;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL)
    private Set<SeatRow> seatRows = new HashSet<>();

    @OneToMany(mappedBy = "theatre")
    private Set<Showing> showings = new HashSet<>();


    @Override
    public void setId(Long id) {
        this.theatreId = id;
    }

    @Override
    public Long getId() {
        return theatreId;
    }
}
