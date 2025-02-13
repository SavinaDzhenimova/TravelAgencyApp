package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.ExcursionExportDTO;
import org.travelagency.model.exportDTO.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;
import org.travelagency.service.interfaces.ExcursionService;
import org.travelagency.service.interfaces.ReservationService;

import java.util.Map;

@Controller
@RequestMapping("/excursions")
public class ReservationController {

    private final ReservationService reservationService;
    private final ExcursionService excursionService;

    public ReservationController(ReservationService reservationService, ExcursionService excursionService) {
        this.reservationService = reservationService;
        this.excursionService = excursionService;
    }

    @GetMapping("/reservations")
    public ModelAndView viewAllReservations() {
        Map<String, ReservationViewInfo> reservationsMap = this.reservationService
                .getAllReservationsGroupedByExcursionsNames();

        ModelAndView modelAndView = new ModelAndView("reservations");

        modelAndView.addObject("reservationsMap", reservationsMap);

        return modelAndView;
    }


    @GetMapping("/reserve/{excursionName}")
    public ModelAndView reserve(@PathVariable("excursionName") String excursionName, Model model) {

        ExcursionExportDTO excursionExportDTO = this.excursionService.getExcursionDetailsByName(excursionName);

        ModelAndView modelAndView = new ModelAndView("reservation-form");

        modelAndView.addObject("excursionName", excursionExportDTO.getName());
        modelAndView.addObject("excursionDestination", excursionExportDTO.getDestination());
        modelAndView.addObject("excursionDate", excursionExportDTO.getDate());
        modelAndView.addObject("excursionEndurance", excursionExportDTO.getEndurance());
        modelAndView.addObject("excursionTransport", excursionExportDTO.getTransport());

        if (!model.containsAttribute("addReservationDTO")) {
            model.addAttribute("addReservationDTO", new AddReservationDTO());
        }

        return modelAndView;
    }

    @PostMapping("/reserve")
    public ModelAndView reserve(@Valid @ModelAttribute("addReservationDTO") AddReservationDTO addReservationDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addReservationDTO", addReservationDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addReservationDTO",
                            bindingResult);

            return new ModelAndView("redirect:/excursions");
        }

        Result result = this.reservationService.addReservation(addReservationDTO, addReservationDTO.getExcursionName());

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/excursions");
    }
}
