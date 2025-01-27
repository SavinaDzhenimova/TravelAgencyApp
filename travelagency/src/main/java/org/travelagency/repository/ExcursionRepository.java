package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Excursion;

@Repository
public interface ExcursionRepository extends JpaRepository<Excursion, Long> {


}