package org.travelagency.web;

import org.springframework.stereotype.Controller;
import org.travelagency.service.interfaces.EmployeeService;

@Controller
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

}