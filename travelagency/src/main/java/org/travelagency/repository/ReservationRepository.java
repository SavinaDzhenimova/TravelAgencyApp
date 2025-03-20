package org.travelagency.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Reservation;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query("SELECT r FROM Reservation r JOIN r.excursion e WHERE e.name = :excursionName")
    List<Reservation> findByExcursionName(@Param("excursionName") String excursionName);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.excursion.id = :excursionId")
    void deleteAllByExcursionId(@Param("excursionId") Long excursionId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Reservation r WHERE r.excursionDate = :excursionDate")
    void deleteAllByExcursionDate(@Param("excursionDate") LocalDate excursionDate);

    List<Reservation> findAllByExcursionId(Long excursionId);

    List<Reservation> findAllByExcursionDate(LocalDate excursionDate);
}
