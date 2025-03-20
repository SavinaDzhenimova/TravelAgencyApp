package org.travelagency.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Day;

import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Day d WHERE d.program.id = :programId")
    void deleteAllByProgramId(@Param("programId") Long programId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Day d WHERE d.id = :id")
    void deleteById(@Param("id") Long id);

    List<Day> findAllByProgramId(Long programId);
}