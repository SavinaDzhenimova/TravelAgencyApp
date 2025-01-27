package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Day;

@Repository
public interface DayRepository extends JpaRepository<Day, Long> {


}