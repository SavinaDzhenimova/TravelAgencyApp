package org.travelagency.web;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
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

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

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
    public ModelAndView viewAllReservations(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "6") int size) {
        ModelAndView modelAndView = new ModelAndView("reservations");

        Page<ReservationViewInfo> reservationPage = this.reservationService
                .getAllReservationsGroupedByExcursionsNames(PageRequest.of(page, size));

        if (reservationPage.isEmpty()) {
            modelAndView.addObject("warningMessage", "Все още няма направени резервации");
        } else {
            modelAndView.addObject("excursions", reservationPage.getContent());
        }

        modelAndView.addObject("currentPage", reservationPage.getNumber());
        modelAndView.addObject("totalPages", reservationPage.getTotalPages());
        modelAndView.addObject("size", size);

        return modelAndView;
    }

    @GetMapping("/excursion-details/{excursionName}/reserve/{date}")
    public ModelAndView reserve(@PathVariable("excursionName") String excursionName,
                                @PathVariable("date") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
                                Model model) {

        String decodedExcursionName = URLDecoder.decode(excursionName, StandardCharsets.UTF_8);

        ExcursionExportDTO excursionExportDTO = this.excursionService.getExcursionDetailsByName(decodedExcursionName);

        ModelAndView modelAndView = new ModelAndView("reservation-form");

        modelAndView.addObject("excursionName", excursionExportDTO.getName());
        modelAndView.addObject("excursionDestination", excursionExportDTO.getDestination());
        modelAndView.addObject("excursionDate", date);
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
            return new ModelAndView("redirect:/excursions/excursion-details/" + encodedName +
                    "/reserve/" + addReservationDTO.getExcursionDate());
        }

        Result result = this.reservationService.addReservation(addReservationDTO, decodedExcursionName);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        String encodedName = URLEncoder.encode(addReservationDTO.getExcursionName(), StandardCharsets.UTF_8);
        return new ModelAndView("redirect:/excursions/excursion-details/" + encodedName +
                "/reserve/" + addReservationDTO.getExcursionDate());
    }
}
