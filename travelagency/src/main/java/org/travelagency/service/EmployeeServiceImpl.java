package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Language;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.user.EmployeeProfileDTO;
import org.travelagency.repository.EmployeeRepository;
import org.travelagency.service.interfaces.EmployeeService;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EmployeeProfileDTO getEmployeeInfo(Long id) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            return null;
        }

        Employee employee = optionalEmployee.get();

        EmployeeProfileDTO employeeProfileDTO = this.modelMapper.map(employee, EmployeeProfileDTO.class);

        String education = this.mapEducationLevel(employee.getEducation());

        employeeProfileDTO.setEducation(education);
        employeeProfileDTO.setRole(employee.getRole().getName());
        employeeProfileDTO.setLanguages(
                employee.getLanguages().stream()
                .map(Language::getName)
                .collect(Collectors.joining(", ")));

        return employeeProfileDTO;
    }

    private String mapEducationLevel(EducationLevel educationLevel) {
        if (educationLevel.equals(EducationLevel.UNIVERSITY_DEGREE)) {
            return "Висше";
        } else if (educationLevel.equals(EducationLevel.SECONDARY)) {
            return "Средно";
        } else if (educationLevel.equals(EducationLevel.PRIMARY)) {
            return "Основно";
        }

        return "";
    }
}
