package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Language;
import org.travelagency.repository.LanguageRepository;
import org.travelagency.service.interfaces.LanguageService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public Set<Language> processLanguages(String input) {
        if (input == null || input.isBlank()) {
            return Collections.emptySet();
        }

        return Arrays.stream(input.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .distinct()
                .map(languageRepository::findByNameIgnoreCase)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Language> findByNameIgnoreCase(String name) {
        return this.languageRepository.findByNameIgnoreCase(name);
    }
}
