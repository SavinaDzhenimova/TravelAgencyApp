package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.CountryRepository;
import org.travelagency.service.interfaces.CountryService;

@Service
public class CountryServiceImpl implements CountryService {

    private final CountryRepository countryRepository;

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
}
