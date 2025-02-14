package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.importDTO.AddSubscriberDTO;
import org.travelagency.service.interfaces.SubscriberService;

@Controller
public class SubscriberController {

    private final SubscriberService subscriptionService;

    public SubscriberController(SubscriberService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @PostMapping("/subscribe")
    public ModelAndView subscribe(@Valid @ModelAttribute("addSubscriberDTO") AddSubscriberDTO addSubscriberDTO,
                                  BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addSubscriberDTO", addSubscriberDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.addSubscriberDTO",
                            bindingResult);

            return new ModelAndView("redirect:/#subscribe");
        }

        Result result = this.subscriptionService.addSubscriber(addSubscriberDTO);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/#subscribe");
    }
}
