package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Embassy;

import java.util.Optional;

@Repository
public interface EmbassyRepository extends JpaRepository<Embassy, Long> {

    Optional<Embassy> findByName(String name);

    @Modifying
    @Query("DELETE FROM Embassy e WHERE e.name = :destinationName")
    void deleteByName(@Param("destinationName") String destinationName);
}