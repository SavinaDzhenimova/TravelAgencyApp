package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Country;
import org.travelagency.model.exportDTO.country.CountryMenuDTO;
import org.travelagency.model.exportDTO.country.CountryMenuInfo;

import java.util.List;
import java.util.Optional;

public interface CountryService {

    CountryMenuInfo getAllCountries();

    List<CountryMenuDTO> getAllCountriesByContinentName(String continentName);

    void saveAndFlushCountry(Country country);

    Optional<Country> findCountryByName(String name);

    void deleteCountryByName(String destinationName);
}
