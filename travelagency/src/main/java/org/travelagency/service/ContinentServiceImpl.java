package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.ContinentRepository;
import org.travelagency.service.interfaces.ContinentService;

@Service
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;

    public ContinentServiceImpl(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }
}