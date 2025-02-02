package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.EmployeeRepository;
import org.travelagency.service.interfaces.EmployeeService;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
}
