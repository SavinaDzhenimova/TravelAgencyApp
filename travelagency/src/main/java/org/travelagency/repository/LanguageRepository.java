package org.travelagency.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.travelagency.model.entity.Language;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
