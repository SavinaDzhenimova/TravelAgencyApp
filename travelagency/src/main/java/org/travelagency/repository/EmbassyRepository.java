package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Embassy;

@Repository
public interface EmbassyRepository extends JpaRepository<Embassy, Long> {


}