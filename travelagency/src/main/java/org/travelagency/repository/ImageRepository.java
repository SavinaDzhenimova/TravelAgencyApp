package org.travelagency.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Image;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    @Modifying
    @Transactional
    @Query("DELETE FROM Image i WHERE i.excursion.id = :excursionId")
    void deleteAllByExcursionId(@Param("excursionId") Long excursionId);

    List<Image> findAllByExcursionId(Long excursionId);
}