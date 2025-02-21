package org.travelagency.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Excursion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    @Query("SELECT e.name FROM Excursion e")
    List<String> findAllExcursionsNames();

    Optional<Excursion> findByName(String excursionName);

    @Query("SELECT e FROM Excursion e JOIN e.destination d WHERE d.name = :destinationName")
    Page<Excursion> findExcursionByDestinationName(@Param("destinationName") String destinationName, Pageable pageable);
}