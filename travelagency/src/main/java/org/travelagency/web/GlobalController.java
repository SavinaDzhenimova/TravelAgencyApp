package org.travelagency.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.travelagency.model.exportDTO.country.CountryMenuInfo;
import org.travelagency.model.exportDTO.destination.DestinationMenuInfo;
import org.travelagency.model.exportDTO.employee.EmployeesViewInfo;
import org.travelagency.service.interfaces.CountryService;
import org.travelagency.service.interfaces.DestinationService;
import org.travelagency.service.interfaces.EmployeeService;

@ControllerAdvice
public class GlobalController {

    private final CountryService countryService;
    private final DestinationService destinationService;

    private final EmployeeService employeeService;

    public GlobalController(CountryService countryService, DestinationService destinationService,
                            EmployeeService employeeService) {
        this.countryService = countryService;
        this.destinationService = destinationService;
        this.employeeService = employeeService;
    }

    @ModelAttribute("countries")
    public CountryMenuInfo getCountriesForMenu() {
        return this.countryService.getAllCountries();
    }

    @ModelAttribute("destinations")
    public DestinationMenuInfo getDestinationsForMenu() {
        return this.destinationService.getAllDestinations();
    }

    @ModelAttribute("employees")
    public EmployeesViewInfo getEmployeesForMenu() {
        return this.employeeService.getAllEmployeesForSelectMenu();
    }

}
