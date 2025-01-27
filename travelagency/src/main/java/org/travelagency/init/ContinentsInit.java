package org.travelagency.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Continent;
import org.travelagency.model.enums.ContinentName;
import org.travelagency.repository.ContinentRepository;

import java.util.ArrayList;
import java.util.Arrays;

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

            Arrays.stream(ContinentName.values())
                    .forEach(continentName -> {
                        Continent continent = new Continent();
                        continent.setContinentName(continentName);

                        String name = switch (continentName) {
                            case EUROPE -> "Европа";
                            case ASIA -> "Азия";
                            case AFRICA -> "Африка";
                            case NORTH_AMERICA -> "Северна Америка";
                            case SOUTH_AMERICA -> "Южна Америка";
                            case AUSTRALIA -> "Австралия";
                        };

                        continent.setName(name);
                        continent.setCountries(new ArrayList<>());
                        this.continentRepository.saveAndFlush(continent);
                    });
        }
    }
}