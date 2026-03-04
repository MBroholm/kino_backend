package org.example.kino_backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Row {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rowId;

    @ManyToOne
    @JoinColumn(name = "theatre_id")
    private Theatre theatre;

    @OneToMany(mappedBy = "row")
    private Set<Seat> seats = new HashSet<>();

    private int rowNumber;
}
