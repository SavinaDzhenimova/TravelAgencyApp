package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.DestinationRepository;
import org.travelagency.service.interfaces.DestinationService;

@Service
public class DestinationServiceImpl implements DestinationService {

    private final DestinationRepository destinationRepository;

    public DestinationServiceImpl(DestinationRepository destinationRepository) {
        this.destinationRepository = destinationRepository;
    }
}
