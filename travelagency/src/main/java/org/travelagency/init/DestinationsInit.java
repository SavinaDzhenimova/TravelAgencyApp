package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.travelagency.repository.CountryRepository;
import org.travelagency.repository.DestinationRepository;

@Component
public class DestinationsInit implements CommandLineRunner {

    private final CountryRepository countryRepository;
    private final DestinationRepository destinationRepository;

    public DestinationsInit(CountryRepository countryRepository, DestinationRepository destinationRepository) {
        this.countryRepository = countryRepository;
        this.destinationRepository = destinationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (this.destinationRepository.count() == 0) {


        }

    }
}