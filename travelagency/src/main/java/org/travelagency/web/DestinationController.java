package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.destination.DestinationViewInfo;
import org.travelagency.model.importDTO.AddDestinationDTO;
import org.travelagency.model.importDTO.UpdateDestinationDTO;
import org.travelagency.service.interfaces.DestinationService;
import org.travelagency.service.utils.DestinationDeletionManager;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/destinations")
public class DestinationController {

    private final DestinationService destinationService;
    private final DestinationDeletionManager destinationDeletionManager;

    public DestinationController(DestinationService destinationService, DestinationDeletionManager destinationDeletionManager) {
        this.destinationService = destinationService;
        this.destinationDeletionManager = destinationDeletionManager;
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

        return new ModelAndView("redirect:/destinations/add-destination");
    }

    @DeleteMapping("/delete-destination/{destinationName}")
    public ModelAndView deleteDestination(@PathVariable("destinationName") String destinationName,
                                          RedirectAttributes redirectAttributes) {

        Result result = this.destinationDeletionManager.deleteDestination(destinationName);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/employees/profile");
    }

    @GetMapping("/update/{destinationName}")
    public ModelAndView updateDestination(@PathVariable("destinationName") String destinationName, Model model) {

        if (!model.containsAttribute("updateDestinationDTO")) {
            model.addAttribute("updateDestinationDTO", new UpdateDestinationDTO());
        }

        String decodedDestinationName = URLDecoder.decode(destinationName, StandardCharsets.UTF_8);

        UpdateDestinationDTO updateDestinationDTO = this.destinationService.getDestinationDetailsForUpdate(decodedDestinationName);

        ModelAndView modelAndView = new ModelAndView("update-destination");

        modelAndView.addObject("updateDestinationDTO", updateDestinationDTO);
        modelAndView.addObject("destinationName", updateDestinationDTO.getDestinationName());
        modelAndView.addObject("capital", updateDestinationDTO.getCapital());
        modelAndView.addObject("currency", updateDestinationDTO.getCurrency());
        modelAndView.addObject("timeDifference", updateDestinationDTO.getTimeDifference());
        modelAndView.addObject("description", updateDestinationDTO.getDescription());
        modelAndView.addObject("visaRequirements", updateDestinationDTO.getVisaRequirements());
        modelAndView.addObject("timeToVisit", updateDestinationDTO.getTimeToVisit());
        modelAndView.addObject("goodToKnow", updateDestinationDTO.getGoodToKnow());
        modelAndView.addObject("address", updateDestinationDTO.getAddress());
        modelAndView.addObject("phoneNumber", updateDestinationDTO.getPhoneNumber());
        modelAndView.addObject("fax", updateDestinationDTO.getFax());
        modelAndView.addObject("dutyPhone", updateDestinationDTO.getDutyPhone());
        modelAndView.addObject("email", updateDestinationDTO.getEmail());
        modelAndView.addObject("webpage", updateDestinationDTO.getWebpage());

        return modelAndView;
    }

    @PutMapping("/update/{destinationName}")
    public ModelAndView updateExcursion(@PathVariable("destinationName") String destinationName,
                                        @Valid @ModelAttribute("updateDestinationDTO") UpdateDestinationDTO updateDestinationDTO,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String decodedExcursionName = URLDecoder.decode(destinationName, StandardCharsets.UTF_8);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateDestinationDTO", updateDestinationDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updateDestinationDTO",
                            bindingResult);

            String encodedName = URLEncoder.encode(destinationName, StandardCharsets.UTF_8);
            return new ModelAndView("redirect:/destinations/update/" + encodedName);
        }

        Result result = this.destinationService.updateDestination(updateDestinationDTO, decodedExcursionName);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        String encodedName = URLEncoder.encode(updateDestinationDTO.getDestinationName(), StandardCharsets.UTF_8)
                .replace("+", "%20");

        return new ModelAndView("redirect:/destinations/" + encodedName);
    }
}