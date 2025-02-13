package org.travelagency.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.excursion.ExcursionExportDTO;
import org.travelagency.model.exportDTO.reservation.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;
import org.travelagency.service.interfaces.ExcursionService;
import org.travelagency.service.interfaces.ReservationService;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Controller
@RequestMapping("/excursions")
public class ReservationController {

    private final ReservationService reservationService;
    private final ExcursionService excursionService;
    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

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

        String decodedExcursionName = URLDecoder.decode(excursionName, StandardCharsets.UTF_8);

        ExcursionExportDTO excursionExportDTO = this.excursionService.getExcursionDetailsByName(decodedExcursionName);

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

        String decodedExcursionName = URLDecoder.decode(addReservationDTO.getExcursionName(), StandardCharsets.UTF_8);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addReservationDTO", addReservationDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addReservationDTO",
                            bindingResult);

            String encodedName = URLEncoder.encode(addReservationDTO.getExcursionName(), StandardCharsets.UTF_8);
            return new ModelAndView("redirect:/excursions/reserve/" + encodedName);
        }

        Result result = this.reservationService.addReservation(addReservationDTO, decodedExcursionName);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        String encodedName = URLEncoder.encode(addReservationDTO.getExcursionName(), StandardCharsets.UTF_8);
        return new ModelAndView("redirect:/excursions/reserve/" + encodedName);
    }
}
