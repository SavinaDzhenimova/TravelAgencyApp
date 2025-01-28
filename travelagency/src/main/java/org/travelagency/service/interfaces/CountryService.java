package org.travelagency.service.interfaces;

import org.travelagency.model.enums.ContinentName;
import org.travelagency.model.exportDTO.CountryMenuDTO;
import org.travelagency.model.exportDTO.CountryMenuInfo;

import java.util.List;

public interface CountryService {
    CountryMenuInfo getAllCountries();

    List<CountryMenuDTO> getAllCountriesByContinentName(ContinentName continentName);
}
