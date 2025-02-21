package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
