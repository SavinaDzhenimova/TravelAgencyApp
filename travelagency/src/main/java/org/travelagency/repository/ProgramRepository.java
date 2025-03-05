package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Modifying
    @Query("DELETE FROM Program p WHERE p.id = :id")
    void deleteProgramById(@Param("id") Long id);
}