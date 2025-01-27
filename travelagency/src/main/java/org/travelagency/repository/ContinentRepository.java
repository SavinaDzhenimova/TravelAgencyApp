package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Continent;

@Repository
public interface ContinentRepository  extends JpaRepository<Continent, Long> {


}