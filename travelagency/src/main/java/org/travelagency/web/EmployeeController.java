package org.travelagency.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.CandidatesViewInfo;
import org.travelagency.model.exportDTO.EmployeesViewInfo;
import org.travelagency.model.user.EmployeeProfileDTO;
import org.travelagency.model.user.UserDetailsDTO;
import org.travelagency.service.interfaces.EmployeeService;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees")
    public ModelAndView employees(@AuthenticationPrincipal UserDetails userDetails) {

        ModelAndView modelAndView = new ModelAndView("employees");

        EmployeesViewInfo employeesViewInfo = this.employeeService.getAllEmployees();
        Long loggedEmployeeId = this.employeeService.getLoggedEmployeeId();

        modelAndView.addObject("loggedEmployeeId", loggedEmployeeId);
        modelAndView.addObject("employees", employeesViewInfo);

        return modelAndView;
    }

    @GetMapping("/employees/profile")
    public ModelAndView profile(@AuthenticationPrincipal UserDetails userDetails) {

        ModelAndView modelAndView = new ModelAndView("profile");

        if (userDetails instanceof UserDetailsDTO userDetailsDTO) {
            EmployeeProfileDTO employeeProfileDTO = this.employeeService.getEmployeeInfo(UserDetailsDTO.getId());

            modelAndView.addObject("employee", employeeProfileDTO);
        }

        return modelAndView;
    }

    @PutMapping("/employees/promote-employee/{id}")
    public ModelAndView promoteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        Result result = this.employeeService.promoteEmployee(id);

        if (result.isSuccess()) {
            redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
        } else {
            redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
        }

        return new ModelAndView("redirect:/employees");
    }

    @DeleteMapping("/employees/delete-employee/{id}")
    public ModelAndView deleteEmployee(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean isEmployeeDeleted = this.employeeService.deleteEmployeeById(id);

        if (isEmployeeDeleted) {
            redirectAttributes.addFlashAttribute("successMessage",
                    "Успешно уволнихте този служител!");
        } else {
            redirectAttributes.addFlashAttribute("failureMessage",
                    "Нещо се обърка! Служителят не беше уволнен!");
        }

        return new ModelAndView("redirect:/employees");
    }
}