package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
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

    @PostMapping("/add-destination")
    public ModelAndView addDestination(@Valid @ModelAttribute("addDestinationDTO") AddDestinationDTO addDestinationDTO,
                                          BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addDestinationDTO", addDestinationDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addDestinationDTO",
                            bindingResult);

            return new ModelAndView("add-destination");
        }

        Result result = this.destinationService.addDestination(addDestinationDTO);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/add-destination");
    }
}