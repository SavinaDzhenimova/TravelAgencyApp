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
import org.travelagency.model.exportDTO.ExcursionViewInfo;
import org.travelagency.model.importDTO.AddExcursionDTO;
import org.travelagency.service.interfaces.ExcursionService;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {

    private final ExcursionService excursionService;

    public ExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
    }

    @GetMapping
    public ModelAndView excursions() {

        ExcursionViewInfo excursionViewInfo = this.excursionService.getAllExcursions();

        ModelAndView modelAndView = new ModelAndView("excursions");

        if (excursionViewInfo == null || excursionViewInfo.getExcursions().isEmpty()) {
            modelAndView.addObject("failureMessage", "Все още няма добавени екскурзии за разглеждане!");
        } else {
            modelAndView.addObject("excursions", excursionViewInfo.getExcursions());
        }

        return modelAndView;
    }

    @GetMapping("/{destinationName}")
    public ModelAndView getExcursions(@PathVariable("destinationName") String destinationName) {

        ExcursionViewInfo excursionViewInfo = this.excursionService.getExcursionsByDestinationName(destinationName);

        ModelAndView modelAndView = new ModelAndView("excursions");

        modelAndView.addObject("destinationName", destinationName);

        if (excursionViewInfo == null || excursionViewInfo.getExcursions().isEmpty()) {
            modelAndView.addObject("failureMessage", "Все още няма добавени екскурзии за разглеждане!");
        } else {
            modelAndView.addObject("excursions", excursionViewInfo.getExcursions());
        }

        return modelAndView;
    }

    @GetMapping("/excursion-details/{excursionName}")
    public ModelAndView getExcursion(@PathVariable("excursionName") String excursionName) {

        ExcursionExportDTO excursionExportDTO = this.excursionService.getExcursionByName(excursionName);

        ModelAndView modelAndView = new ModelAndView("excursion-details");

        modelAndView.addObject("excursion", excursionExportDTO);

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
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/excursions/add-excursion");
    }
}
