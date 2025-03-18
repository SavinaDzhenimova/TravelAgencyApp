package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.excursion.ExcursionExportDTO;
import org.travelagency.model.exportDTO.excursion.ExcursionViewDTO;
import org.travelagency.model.importDTO.AddExcursionDTO;
import org.travelagency.model.importDTO.AddInquiryDTO;
import org.travelagency.model.importDTO.UpdateExcursionDTO;
import org.travelagency.model.user.UserDetailsDTO;
import org.travelagency.service.interfaces.ExcursionService;
import org.travelagency.service.utils.ExcursionDeletionManager;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {

    private final ExcursionService excursionService;
    private final ExcursionDeletionManager excursionDeletionManager;

    public ExcursionController(ExcursionService excursionService, ExcursionDeletionManager excursionDeletionManager) {
        this.excursionService = excursionService;
        this.excursionDeletionManager = excursionDeletionManager;
    }

    @GetMapping
    public ModelAndView getAllExcursions(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "6") int size) {

        ModelAndView modelAndView = new ModelAndView("excursions");

        Page<ExcursionViewDTO> excursionsPage = this.excursionService
                .getAllExcursions(PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

        if (excursionsPage.isEmpty()) {
            modelAndView.addObject("warningMessage", "Все още няма добавени екскурзии за разглеждане!");
        } else {
            modelAndView.addObject("excursions", excursionsPage.getContent());
        }

        modelAndView.addObject("currentPage", excursionsPage.getNumber());
        modelAndView.addObject("totalPages", excursionsPage.getTotalPages());
        modelAndView.addObject("size", size);

        return modelAndView;
    }

    @GetMapping("/my-excursions")
    public ModelAndView getAllExcursionsForLoggedEmployee(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "6") int size,
                                                          @AuthenticationPrincipal UserDetails userDetails) {

        ModelAndView modelAndView = new ModelAndView("excursions");

        if (userDetails instanceof UserDetailsDTO userDetailsDTO) {
            modelAndView.addObject("guideName", userDetailsDTO.getFullName());
        }

        Page<ExcursionViewDTO> excursionsPage = this.excursionService
                .getAllExcursionsForLoggedEmployee(PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

        if (excursionsPage.isEmpty()) {
            modelAndView.addObject("warningMessage",
                    "Все още не сте назначен/а за ръководител на нито една екскурзия!");
        } else {
            modelAndView.addObject("excursions", excursionsPage.getContent());
        }

        modelAndView.addObject("currentPage", excursionsPage.getNumber());
        modelAndView.addObject("totalPages", excursionsPage.getTotalPages());
        modelAndView.addObject("size", size);

        return modelAndView;
    }

    @GetMapping("/{destinationName}")
    public ModelAndView getExcursionsByDestination(@PathVariable("destinationName") String destinationName,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "6") int size) {

        ModelAndView modelAndView = new ModelAndView("excursions");

        Page<ExcursionViewDTO> excursionsPage = this.excursionService
                .getAllExcursionsByDestinationName(destinationName,
                        PageRequest.of(page, size, Sort.by(Sort.Order.desc("id"))));

        if (excursionsPage.isEmpty()) {
            modelAndView.addObject("warningMessage", "Все още няма добавени екскурзии за разглеждане!");
        } else {
            modelAndView.addObject("excursions", excursionsPage.getContent());
        }

        modelAndView.addObject("currentPage", excursionsPage.getNumber());
        modelAndView.addObject("totalPages", excursionsPage.getTotalPages());
        modelAndView.addObject("size", size);

        return modelAndView;
    }

    @GetMapping("/excursion-details/{excursionName}")
    public ModelAndView getExcursionDetails(@PathVariable("excursionName") String excursionName, Model model) {

        if (!model.containsAttribute("addInquiryDTO")) {
            model.addAttribute("addInquiryDTO", new AddInquiryDTO());
        }

        String decodedExcursionName = URLDecoder.decode(excursionName, StandardCharsets.UTF_8);

        ExcursionExportDTO excursionExportDTO = this.excursionService.getExcursionDetailsByName(decodedExcursionName);

        ModelAndView modelAndView = new ModelAndView("excursion-details");

        modelAndView.addObject("excursion", excursionExportDTO);
        modelAndView.addObject("dates", excursionExportDTO.getDates());
        modelAndView.addObject("days", excursionExportDTO.getDays());

        return modelAndView;
    }

    @GetMapping("/add-excursion")
    public ModelAndView addExcursion(Model model) {

        if (!model.containsAttribute("addExcursionDTO")) {
            model.addAttribute("addExcursionDTO", new AddExcursionDTO());
        }

        return new ModelAndView("add-excursion");
    }

    @PostMapping("/add-excursion")
    public ModelAndView addExcursion(@Valid @ModelAttribute("addExcursionDTO") AddExcursionDTO addExcursionDTO,
                                     BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addExcursionDTO", addExcursionDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addExcursionDTO",
                            bindingResult);

            return new ModelAndView("add-excursion");
        }

        Result result = this.excursionService.addExcursion(addExcursionDTO);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
            return new ModelAndView("redirect:/excursions");
        }

        redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        return new ModelAndView("redirect:/excursions/add-excursion");
    }

    @DeleteMapping("/delete-excursion/{id}")
    public ModelAndView deleteExcursion(@PathVariable("id") Long excursionId,
                                        RedirectAttributes redirectAttributes) throws IOException {

        Result result = this.excursionDeletionManager.deleteExcursion(excursionId);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/excursions");
    }

    @PostMapping("/excursion-details/{excursionName}/send-inquiry")
    public ModelAndView sendInquiry(@PathVariable("excursionName") String excursionName,
                                    @Valid @ModelAttribute AddInquiryDTO addInquiryDTO,
                                    BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String encodedName = URLEncoder.encode(excursionName, StandardCharsets.UTF_8);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addInquiryDTO", addInquiryDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addInquiryDTO",
                            bindingResult);

            return new ModelAndView("redirect:/excursions/excursion-details/" + encodedName);
        }

        this.excursionService.sendInquiryEmail(addInquiryDTO, excursionName);

        redirectAttributes.addFlashAttribute("successMessage",
                "Вашето запитване беше изпратено успешно!");

        return new ModelAndView("redirect:/excursions/excursion-details/" + encodedName);
    }

    @GetMapping("/update/{excursionName}")
    public ModelAndView updateExcursion(@PathVariable("excursionName") String excursionName, Model model) {

        if (!model.containsAttribute("updateExcursionDTO")) {
            model.addAttribute("updateExcursionDTO", new UpdateExcursionDTO());
        }

        String decodedExcursionName = URLDecoder.decode(excursionName, StandardCharsets.UTF_8);

        UpdateExcursionDTO updateExcursionDTO = this.excursionService.getExcursionDetailsForUpdate(decodedExcursionName);

        ModelAndView modelAndView = new ModelAndView("update-excursion");

        modelAndView.addObject("updateExcursionDTO", updateExcursionDTO);
        modelAndView.addObject("excursionName", updateExcursionDTO.getExcursionName());
        modelAndView.addObject("price", updateExcursionDTO.getPrice());
        modelAndView.addObject("guideName", updateExcursionDTO.getGuideName());
        modelAndView.addObject("destination", updateExcursionDTO.getDestination());
        modelAndView.addObject("endurance", updateExcursionDTO.getEndurance());
        modelAndView.addObject("transport", updateExcursionDTO.getTransport());
        modelAndView.addObject("dates", updateExcursionDTO.getDates());
        modelAndView.addObject("days", updateExcursionDTO.getDays());

        return modelAndView;
    }

    @PutMapping("/update/{excursionName}")
    public ModelAndView updateExcursion(@PathVariable("excursionName") String excursionName,
                                        @Valid @ModelAttribute("updateExcursionDTO") UpdateExcursionDTO updateExcursionDTO,
                                        BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        String decodedExcursionName = URLDecoder.decode(excursionName, StandardCharsets.UTF_8);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updateExcursionDTO", updateExcursionDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updateExcursionDTO",
                            bindingResult);

            String encodedName = URLEncoder.encode(excursionName, StandardCharsets.UTF_8);
            return new ModelAndView("redirect:/excursions/update/" + encodedName);
        }

        Result result = this.excursionService.updateExcursion(updateExcursionDTO, decodedExcursionName);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        String encodedName = URLEncoder.encode(updateExcursionDTO.getExcursionName(), StandardCharsets.UTF_8)
                .replace("+", "%20");
        return new ModelAndView("redirect:/excursions/excursion-details/" + encodedName);
    }
}
