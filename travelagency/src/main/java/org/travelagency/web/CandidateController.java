package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.service.interfaces.CandidateService;

@Controller
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/register")
    public ModelAndView register(Model model) {

        if (!model.containsAttribute("addCandidateDTO")) {
            model.addAttribute("addCandidateDTO", new AddCandidateDTO());
        }

        return new ModelAndView("register-form");
    }

    @PostMapping("/register")
    public ModelAndView registerCandidate(@Valid @ModelAttribute("addCandidateDTO") AddCandidateDTO addCandidateDTO,
                                BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addCandidateDTO", addCandidateDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addCandidateDTO",
                            bindingResult);

            return new ModelAndView("register-form");
        }

        boolean isAdded = this.candidateService.addCandidate(addCandidateDTO);

        if (isAdded) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Кандидатурата Ви беше изпратена успешно!" +
                            "Моля изчакайте търпеливо да бъде разгледана и наш служител ще се свърже с Вас!");

        } else {
            redirectAttributes.addFlashAttribute("failureMessage",
                    "Нещо се обърка! Моля проверете дали сте попълнили всички необходими полета " +
                            "и опитайте да изпратите кандидатурата си отново!");

        }

        return new ModelAndView("redirect:/register");
    }
}