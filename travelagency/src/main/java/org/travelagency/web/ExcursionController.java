package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.importDTO.AddExcursionDTO;
import org.travelagency.service.interfaces.ExcursionService;

@Controller
@RequestMapping("/excursions")
public class ExcursionController {

    private final ExcursionService excursionService;

    public ExcursionController(ExcursionService excursionService) {
        this.excursionService = excursionService;
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
