package org.travelagency.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.travelagency.model.exportDTO.DestinationViewDTO;
import org.travelagency.model.exportDTO.DestinationViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.model.importDTO.AddDestinationDTO;
import org.travelagency.service.interfaces.DestinationService;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;

    public DestinationController(DestinationService destinationService) {
        this.destinationService = destinationService;
    }

    @GetMapping("/{countryName}")
    public ModelAndView getDestinationDetails(@PathVariable("countryName") String countryName) {
        DestinationViewInfo destinationViewInfo = this.destinationService.getDestinationInfo(countryName);

        ModelAndView modelAndView = new ModelAndView("destination-details");

        modelAndView.addObject("destination", destinationViewInfo);

        return modelAndView;
    }

    @GetMapping("/add-destination")
    public ModelAndView addDestination(Model model) {

        if (!model.containsAttribute("addDestinationDTO")) {
            model.addAttribute("addDestinationDTO", new AddDestinationDTO());
        }

        return new ModelAndView("add-destination");
    }
}