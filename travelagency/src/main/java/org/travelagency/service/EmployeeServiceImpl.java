package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Employee;
import org.travelagency.model.entity.Language;
import org.travelagency.model.entity.Result;
import org.travelagency.model.entity.Role;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.enums.RoleName;
import org.travelagency.model.exportDTO.employee.EmployeeDTO;
import org.travelagency.model.exportDTO.employee.EmployeesViewInfo;
import org.travelagency.model.importDTO.UpdatePasswordDTO;
import org.travelagency.model.user.EmployeeProfileDTO;
import org.travelagency.repository.EmployeeRepository;
import org.travelagency.repository.RoleRepository;
import org.travelagency.service.events.PromoteEmployeeEvent;
import org.travelagency.service.interfaces.EmployeeService;
import org.travelagency.service.interfaces.LanguageService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LanguageService languageService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, LanguageService languageService,
                               RoleRepository roleRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper,
                               ApplicationEventPublisher applicationEventPublisher) {
        this.employeeRepository = employeeRepository;
        this.languageService = languageService;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.applicationEventPublisher = applicationEventPublisher;
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
    public Result updateEmployeeInfo(Long id, String infoToUpdate, String updatedInfo) {

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            return new Result(false, "Този потребител не съществува!");
        }

        Employee employee = optionalEmployee.get();

        switch (infoToUpdate) {
            case "email" -> {
                Optional<Employee> optionalEmployeeByEmail = this.employeeRepository.findByEmail(updatedInfo);

                if (optionalEmployeeByEmail.isPresent()) {
                    return new Result(false, "Този имейл вече съществува в базата данни!");
                }

                if (!this.isValidEmail(updatedInfo)) {
                    return new Result(false, "Въведеният от вас имейл не е във валиден формат!");
                }

                employee.setEmail(updatedInfo);
            }
            case "phoneNumber" -> {
                Optional<Employee> optionalEmployeeByPhoneNumber = this.employeeRepository.findByPhoneNumber(updatedInfo);

                if (optionalEmployeeByPhoneNumber.isPresent()) {
                    return new Result(false, "Този телефонен номер вече съществува в базата данни!");
                }

                if (updatedInfo.length() < 7 || updatedInfo.length() > 15) {
                    return new Result(false,
                            "Въведеният от вас телефонен номер трябва да съдържа между 7 и 15 символа!");
                }

                employee.setPhoneNumber(updatedInfo);
            }
            case "address" -> {

                if (updatedInfo.length() < 3 || updatedInfo.length() > 70) {
                    return new Result(false,
                            "Въведеният от вас адрес трябва да съдържа между 3 и 70 символа!");
                }

                employee.setAddress(updatedInfo);
            }
            case "education" -> {
                String educationLevel = this.mapEducationLevel(employee.getEducation());

                if (!updatedInfo.equalsIgnoreCase("Основно") &&
                        !updatedInfo.equalsIgnoreCase("Средно") &&
                        !updatedInfo.equalsIgnoreCase("Висше")) {
                    return new Result(false,
                            "Новата степен на образование, която сте посочили, не е разпозната!");
                }

                if (educationLevel.equalsIgnoreCase(updatedInfo)) {
                    return new Result(false, "Вашето образование вече е " + educationLevel);
                }

                if (educationLevel.equals("Основно") && updatedInfo.equalsIgnoreCase("Средно")) {
                    employee.setEducation(EducationLevel.SECONDARY);

                    this.employeeRepository.saveAndFlush(employee);

                    return new Result(true, "Успешно променихте образованието си от Основно на Средно!");
                } else if (educationLevel.equals("Средно") && updatedInfo.equalsIgnoreCase("Висше")) {
                    employee.setEducation(EducationLevel.UNIVERSITY_DEGREE);
                    employee.setSpecialty("Моля въведете своята специалност!");

                    this.employeeRepository.saveAndFlush(employee);

                    return new Result(true, "Успешно променихте образованието си от Средно на Висше! " +
                            "Моля посочете и специалността, която сте завършили!");
                }

                return new Result(false, "Не можете да преминете към по-ниска степен на образование от настоящата!");
            }
            case "specialty" -> {
                if (employee.getSpecialty().equalsIgnoreCase(updatedInfo)) {
                    return new Result(false, "Не сте въвели нова специалност!");
                }

                if (!employee.getEducation().equals(EducationLevel.UNIVERSITY_DEGREE)) {
                    return new Result(false,
                            "Не може да редактирате специалността понеже образованието ви не е Висше!");
                }

                if (updatedInfo.length() < 10 || updatedInfo.length() > 80) {
                    return new Result(false,
                            "Въведената от вас специалност трябва да съдържа между 10 и 80 символа!");
                }

                employee.setSpecialty(updatedInfo);
            }
            case "languages" -> {
                Set<Language> employeeLanguages = employee.getLanguages();

                String employeeLanguagesToString = this.mapLanguagesToStringFormat(employeeLanguages);

                if (employeeLanguagesToString.equalsIgnoreCase(updatedInfo)) {
                    return new Result(false, "Не сте въвели нов говорим език!");
                }

                Set<Language> newLanguages = this.languageService.processLanguages(updatedInfo);

                employee.setLanguages(newLanguages);
            }
        }

        this.employeeRepository.saveAndFlush(employee);

        return new Result(true, "Вие успешно актуализирахте своите лични данни!");
    }

    @Override
    public Result updateEmployeePassword(Long id, UpdatePasswordDTO updatePasswordDTO) {

        Optional<Employee> optionalEmployee = this.employeeRepository.findById(id);

        if (optionalEmployee.isEmpty()) {
            return new Result(false, "Този потребител не съществува!");
        }

        Employee employee = optionalEmployee.get();

        boolean matches = this.passwordEncoder.matches(updatePasswordDTO.getOldPassword(), employee.getPassword());

        if (!matches) {
            return new Result(false, "Старата Ви парола е грешна!");
        }

        if (!updatePasswordDTO.getNewPassword().equals(updatePasswordDTO.getConfirmNewPassword())) {
            return new Result(false, "Новата Ви парола не съвпада с потвърждението!");
        }

        employee.setPassword(this.passwordEncoder.encode(updatePasswordDTO.getNewPassword()));
        this.employeeRepository.saveAndFlush(employee);

        return new Result(true, "Вие успешно променихте своята парола!");
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9]+[_.]?[a-zA-Z0-9]+@[a-zA-Z]+.+[a-zA-Z]+$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
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

        if (employee.getRole().getRoleName().equals(RoleName.MANAGER)) {
            return new Result(false, "Този служител вече е мениджър и не можете да го повишите отново!");
        }

        Role role = optionalRole.get();
        employee.setRole(role);

        this.employeeRepository.saveAndFlush(employee);

        this.applicationEventPublisher.publishEvent(
                new PromoteEmployeeEvent(this, employee.getFullName(), employee.getEmail()));

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

    @Override
    public Optional<Employee> findEmployeeByEmail(String email) {
        return this.employeeRepository.findByEmail(email);
    }

    @Override
    public Optional<Employee> findEmployeeByPhoneNumber(String phoneNumber) {
        return this.employeeRepository.findByPhoneNumber(phoneNumber);
    }

    private String mapLanguagesToStringFormat(Set<Language> languages) {
        return languages.stream()
                .map(Language::getName)
                .collect(Collectors.joining(", "));
    }

    @Override
    public Long getLoggedEmployeeId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName();

        Optional<Employee> optionalEmployee = this.employeeRepository.findByEmail(email);

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
