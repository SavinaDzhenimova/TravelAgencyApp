package org.travelagency.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.travelagency.model.exportDTO.country.CountryMenuInfo;
import org.travelagency.model.exportDTO.destination.DestinationMenuInfo;
import org.travelagency.service.interfaces.CountryService;
import org.travelagency.service.interfaces.DestinationService;

@ControllerAdvice
public class GlobalController {

    private final CountryService countryService;
    private final DestinationService destinationService;

    public GlobalController(CountryService countryService, DestinationService destinationService) {
        this.countryService = countryService;
        this.destinationService = destinationService;
    }

    @ModelAttribute("countries")
    public CountryMenuInfo getCountriesForMenu() {
        return this.countryService.getAllCountries();
    }

    @ModelAttribute("destinations")
    public DestinationMenuInfo getDestinationsForMenu() {
        return this.destinationService.getAllDestinations();
    }

}
