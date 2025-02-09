package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Continent;
import org.travelagency.repository.ContinentRepository;
import org.travelagency.service.interfaces.ContinentService;

import java.util.Optional;

@Service
public class ContinentServiceImpl implements ContinentService {

    private final ContinentRepository continentRepository;

    public ContinentServiceImpl(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    @Override
    public Optional<Continent> findContinentByName(String name) {
        return this.continentRepository.findByName(name);
    }
}