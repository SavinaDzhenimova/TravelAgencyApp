package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Program;

@Repository
public interface ProgramRepository extends JpaRepository<Program, Long> {


}