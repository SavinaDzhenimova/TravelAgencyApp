package org.travelagency.web;

import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.employee.EmployeesViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.model.importDTO.UpdatePasswordDTO;
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
    public ModelAndView profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {

        if (!model.containsAttribute("updatePasswordDTO")) {
            model.addAttribute("updatePasswordDTO", new UpdatePasswordDTO());
        }

        ModelAndView modelAndView = new ModelAndView("profile");

        if (userDetails instanceof UserDetailsDTO userDetailsDTO) {
            EmployeeProfileDTO employeeProfileDTO = this.employeeService.getEmployeeInfo(UserDetailsDTO.getId());

            modelAndView.addObject("employee", employeeProfileDTO);
            modelAndView.addObject("employeeId", UserDetailsDTO.getId());
        }

        return modelAndView;
    }

    @PutMapping("/employees/profile/update/{info}")
    public ModelAndView updateProfile(@PathVariable("info") String infoToUpdate,
                                      @AuthenticationPrincipal UserDetails userDetails,
                                      @RequestParam("updatedInfo") String updatedInfo,
                                      RedirectAttributes redirectAttributes) {

        ModelAndView modelAndView = new ModelAndView("profile");

        if (userDetails instanceof UserDetailsDTO userDetailsDTO) {
            Result result = this.employeeService.updateEmployeeInfo(UserDetailsDTO.getId(), infoToUpdate, updatedInfo);

            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
            }
        }

        return new ModelAndView("redirect:/employees/profile");
    }

    @PutMapping("/employees/profile/update/password")
    public ModelAndView updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                                       @Valid @ModelAttribute UpdatePasswordDTO updatePasswordDTO,
                                       BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("updatePasswordDTO", updatePasswordDTO)
                    .addFlashAttribute("org.springframework.validation.BindingResult.updatePasswordDTO",
                            bindingResult);

            return new ModelAndView("redirect:/employees/profile");
        }

        if (userDetails instanceof UserDetailsDTO userDetailsDTO) {
            Result result = this.employeeService
                    .updateEmployeePassword(UserDetailsDTO.getId(), updatePasswordDTO);

            if (result.isSuccess()) {
                redirectAttributes.addFlashAttribute("successMessage", result.getMessage());
            } else {
                redirectAttributes.addFlashAttribute("failureMessage", result.getMessage());
            }
        }

        return new ModelAndView("redirect:/employees/profile");
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