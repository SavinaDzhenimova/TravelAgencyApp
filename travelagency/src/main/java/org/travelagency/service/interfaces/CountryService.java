package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Country;
import org.travelagency.model.enums.ContinentName;
import org.travelagency.model.exportDTO.CountryMenuDTO;
import org.travelagency.model.exportDTO.CountryMenuInfo;

import java.util.List;
import java.util.Optional;

public interface CountryService {
    CountryMenuInfo getAllCountries();

    List<CountryMenuDTO> getAllCountriesByContinentName(ContinentName continentName);

    void saveAndFlushCountry(Country country);

    Optional<Country> findCountryByName(String name);
}
