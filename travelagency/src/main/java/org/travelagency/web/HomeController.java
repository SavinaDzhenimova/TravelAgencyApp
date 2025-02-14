package org.travelagency.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.travelagency.model.exportDTO.destination.DestinationsExportListDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewInfo;
import org.travelagency.service.interfaces.DestinationService;
import org.travelagency.service.interfaces.ExcursionService;

@Controller
public class HomeController {

    private final DestinationService destinationService;
    private final ExcursionService excursionService;

    public HomeController(DestinationService destinationService, ExcursionService excursionService) {
        this.destinationService = destinationService;
        this.excursionService = excursionService;
    }

    @GetMapping("/")
    public ModelAndView index() {

        ModelAndView modelAndView = new ModelAndView("index");

        DestinationsExportListDTO destinationsExportListDTO = this.destinationService.getDestinationsForIndexPage();
        ExcursionViewInfo excursionsForIndexPage = this.excursionService.getExcursionsForIndexPage();

        modelAndView.addObject("destinations", destinationsExportListDTO.getDestinations());
        modelAndView.addObject("excursions", excursionsForIndexPage.getExcursions());

        return modelAndView;
    }

    @GetMapping("/about-us")
    public ModelAndView about() {

        return new ModelAndView("about-us");
    }

    @GetMapping("/contacts")
    public ModelAndView contacts() {

        return new ModelAndView("contacts");
    }

    @GetMapping("/faq")
    public ModelAndView faq() {

        return new ModelAndView("faq");
    }

    @GetMapping("/privacy-policy")
    public ModelAndView privacyPolicy() {

        return new ModelAndView("privacy-policy");
    }

    @GetMapping("/general-conditions")
    public ModelAndView generalConditions() {

        return new ModelAndView("general-conditions");
    }

}