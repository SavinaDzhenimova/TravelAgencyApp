package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.LanguageRepository;
import org.travelagency.service.interfaces.LanguageService;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }
}
