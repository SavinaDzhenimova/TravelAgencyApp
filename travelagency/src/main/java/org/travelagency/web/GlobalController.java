package org.travelagency.web;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.travelagency.model.exportDTO.CountryMenuInfo;
import org.travelagency.service.interfaces.CountryService;

@ControllerAdvice
public class GlobalController {

    private final CountryService countryService;

    public GlobalController(CountryService countryService) {
        this.countryService = countryService;
    }

    @ModelAttribute("countries")
    public CountryMenuInfo getCountriesForMenu() {
        return this.countryService.getAllCountries();
    }

}
