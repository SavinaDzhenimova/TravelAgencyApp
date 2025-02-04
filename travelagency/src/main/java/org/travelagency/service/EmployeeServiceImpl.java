package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Language;
import org.travelagency.model.entity.Result;
import org.travelagency.model.entity.Role;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.enums.RoleName;
import org.travelagency.model.exportDTO.EmployeeDTO;
import org.travelagency.model.exportDTO.EmployeesViewInfo;
import org.travelagency.model.user.EmployeeProfileDTO;
import org.travelagency.repository.EmployeeRepository;
import org.travelagency.repository.RoleRepository;
import org.travelagency.service.interfaces.EmployeeService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, RoleRepository roleRepository,
                               ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeesViewInfo getAllEmployees() {
        List<Employee> employees = this.employeeRepository.findAll();

        List<EmployeeDTO> employeeDTOList  = employees.stream()
                .map(employee -> {
                    EmployeeDTO employeeDTO = this.modelMapper.map(employee, EmployeeDTO.class);

                    String education = this.mapEducationLevel(employee.getEducation());
                    employeeDTO.setEducation(education);

                    String languages = this.mapLanguagesToStringFormat(employee.getLanguages());
                    employeeDTO.setLanguages(languages);

                    employeeDTO.setRole(employee.getRole().getName());

                    return employeeDTO;
                })
                .toList();

        return new EmployeesViewInfo(employeeDTOList);
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

        String languages = this.mapLanguagesToStringFormat(employee.getLanguages());
        employeeProfileDTO.setLanguages(languages);

        return employeeProfileDTO;
    }

    @Override
    public Result promoteEmployee(Long id) {
        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            return new Result(false, "Този служител не съществува!");
        }

        Employee employee = optionalEmployee.get();

        Optional<Role> optionalRole = this.roleRepository.findByRoleName(RoleName.MANAGER);

        if (optionalRole.isEmpty()) {
            return new Result(false, "Ролята, в която искате да повишите служителя, не съществува!");
        }

        Role role = optionalRole.get();
        employee.setRole(role);

        this.employeeRepository.saveAndFlush(employee);
        return new Result(true, "Успешно повишихте този служител! Той вече е мениджър!");
    }

    @Override
    public boolean deleteEmployeeById(Long id) {
        this.employeeRepository.deleteById(id);

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        return optionalEmployee.isEmpty();
    }

    @Override
    public void saveAndFlushEmployee(Employee employee) {
        this.employeeRepository.saveAndFlush(employee);
    }

    private String mapLanguagesToStringFormat(Set<Language> languages) {
        return languages.stream()
                .map(Language::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public Long getLoggedEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();

        Optional<Employee> optionalEmployee = this.employeeRepository.findByUsername(username);

        if (optionalEmployee.isEmpty()) {
            return 0L;
        }

        return optionalEmployee.get().getId();
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
