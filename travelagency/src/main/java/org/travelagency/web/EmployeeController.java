package org.travelagency.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import org.travelagency.model.user.EmployeeProfileDTO;
import org.travelagency.model.user.UserDetailsDTO;
import org.travelagency.service.interfaces.EmployeeService;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
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
}