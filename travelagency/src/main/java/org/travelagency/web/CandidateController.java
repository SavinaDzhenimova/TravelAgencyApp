package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.candidate.CandidatesViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.service.interfaces.CandidateService;

@Controller
public class CandidateController {

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/candidates")
    public ModelAndView candidates() {

        ModelAndView modelAndView = new ModelAndView("candidates");

        CandidatesViewInfo candidatesViewInfo = this.candidateService.getAllCandidates();

        modelAndView.addObject("candidates", candidatesViewInfo);

        return modelAndView;
    }

    @PostMapping("/candidates/add-employee/{id}")
    public ModelAndView addEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Result result = this.candidateService.getCandidateById(id);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/candidates");
    }

    @DeleteMapping("/candidates/delete-candidate/{id}")
    public ModelAndView deleteCandidate(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean isCandidateDeleted = this.candidateService.deleteCandidateById(id);

        if (isCandidateDeleted) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Успешно отхвърлихте този кандидат!");
        } else {
            redirectAttributes.addFlashAttribute("failureMessage",
                    "Нещо се обърка! Кандидатът не беше отхвърлен!");
        }

        return new ModelAndView("redirect:/candidates");
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

        Result result = this.candidateService.addCandidate(addCandidateDTO);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/register");
    }
}