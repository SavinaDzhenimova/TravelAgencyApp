package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {


}