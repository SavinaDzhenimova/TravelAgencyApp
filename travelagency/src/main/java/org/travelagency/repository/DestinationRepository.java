package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Destination;

import java.util.Optional;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    Optional<Destination> findByName(String countryName);

    @Modifying
    @Query("DELETE FROM Destination d WHERE d.name = :destinationName")
    void deleteByName(@Param("destinationName") String destinationName);
}