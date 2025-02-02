package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Language;

import java.util.Optional;
import java.util.Set;

public interface LanguageService {
    Set<Language> processLanguages(String input);

    Optional<Language> findByNameIgnoreCase(String name);
}
