package org.travelagency.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.service.interfaces.EmployeeService;

@Controller
@RequestMapping("/employees")
public class LoginController {

    private final EmployeeService employeeService;

    public LoginController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/login")
    public ModelAndView login() {

        return new ModelAndView("login-form");
    }

    @GetMapping("/login-error")
    public ModelAndView loginError(RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("failureMessage",
                "Невалидно потребителско име или парола!");

        return new ModelAndView("redirect:/employees/login");
    }

    @GetMapping("/login/forgot-password")
    public ModelAndView showForgotPasswordPage() {

        return new ModelAndView("forgot-password");
    }

    @PostMapping("/login/forgot-password")
    public ModelAndView sendForgotPasswordEmail(@RequestParam("emailOrPhoneNumber") String emailOrPhoneNumber,
                                                RedirectAttributes redirectAttributes) {

        Result result = this.employeeService.sendEmailForForgottenPassword(emailOrPhoneNumber);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());

            return new ModelAndView("redirect:/employees/login/forgot-password");
        }

        return new ModelAndView("redirect:/employees/login");
    }
}
