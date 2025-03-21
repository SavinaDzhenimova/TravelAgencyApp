package org.travelagency.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Excursion;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {

    @Query("SELECT e.name FROM Excursion e")
    Page<String> findAllExcursionsNames(Pageable pageable);

    Optional<Excursion> findByName(String excursionName);

    @Modifying
    @Query("DELETE FROM Excursion e WHERE e.id = :id")
    void deleteExcursionById(@Param("id") Long id);

    @Query("SELECT e FROM Excursion e JOIN e.destination d WHERE d.name = :destinationName")
    Page<Excursion> findExcursionByDestinationName(@Param("destinationName") String destinationName, Pageable pageable);

    @Query("SELECT e FROM Excursion e JOIN e.guide g WHERE g.id = :guideId")
    Page<Excursion> findAllByGuideId(@Param("guideId") Long guideId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM Excursion e WHERE e.destination.id = :destinationId")
    void deleteAllByDestinationId(@Param("destinationId") Long destinationId);

    @Query("SELECT e FROM Excursion e WHERE e.destination.id = :destinationId")
    List<Excursion> findAllByDestinationId(@Param("destinationId") Long destinationId);
}