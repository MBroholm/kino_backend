package org.example.kino_backend.repository;

import org.example.kino_backend.model.Showing;
import org.example.kino_backend.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ShowingRepository extends JpaRepository<Showing, Long> {
    @Query("""
        SELECT s FROM Showing s
        WHERE s.theatre = :theatre
          AND s.startTime < :end
          AND s.endTime > :start
    """)
    List<Showing> findOverlappingShowings(@Param("theatre") Theatre theatre,
                                          @Param("start") LocalDateTime start,
                                          @Param("end") LocalDateTime end
    );
}
