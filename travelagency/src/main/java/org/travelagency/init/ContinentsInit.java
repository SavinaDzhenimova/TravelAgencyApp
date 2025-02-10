package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Continent;
import org.travelagency.repository.ContinentRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@Component
@Order(1)
public class ContinentsInit implements CommandLineRunner {

    private final ContinentRepository continentRepository;

    public ContinentsInit(ContinentRepository continentRepository) {
        this.continentRepository = continentRepository;
    }

    @Override
    public void run(String... args) {

        if (this.continentRepository.count() == 0) {

            createContinent("Европа");
            createContinent("Азия");
            createContinent("Африка");
            createContinent("Северна Америка");
            createContinent("Южна Америка");
            createContinent("Австралия");
        }
    }

    private void createContinent(String name) {
        Optional<Continent> optionalContinent = this.continentRepository.findByName(name);

        if (optionalContinent.isEmpty()) {
            Continent continent = new Continent();

            continent.setName(name);
            continent.setCountries(new ArrayList<>());

            this.continentRepository.saveAndFlush(continent);
        }
    }
}