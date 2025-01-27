package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.ExcursionRepository;
import org.travelagency.service.interfaces.ExcursionService;

@Service
public class ExcursionServiceImpl implements ExcursionService {

    private final ExcursionRepository excursionRepository;

    public ExcursionServiceImpl(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }
}
