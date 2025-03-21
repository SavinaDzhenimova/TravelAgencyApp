package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.*;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.enums.RoleName;
import org.travelagency.model.exportDTO.candidate.CandidateDTO;
import org.travelagency.model.exportDTO.candidate.CandidatesViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.repository.CandidateRepository;
import org.travelagency.repository.RoleRepository;
import org.travelagency.service.events.AddCandidateEvent;
import org.travelagency.service.events.HireEmployeeEvent;
import org.travelagency.service.interfaces.CandidateService;
import org.travelagency.service.interfaces.EmployeeService;
import org.travelagency.service.interfaces.LanguageService;
import org.travelagency.service.utils.PasswordGenerator;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final EmployeeService employeeService;
    private final LanguageService languageService;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final PasswordGenerator passwordGenerator;
    private String rawPassword;

    public CandidateServiceImpl(CandidateRepository candidateRepository, EmployeeService employeeService,
                                LanguageService languageService, RoleRepository roleRepository, ModelMapper modelMapper,
                                PasswordEncoder passwordEncoder, ApplicationEventPublisher applicationEventPublisher,
                                PasswordGenerator passwordGenerator) {
        this.candidateRepository = candidateRepository;
        this.employeeService = employeeService;
        this.languageService = languageService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.applicationEventPublisher = applicationEventPublisher;
        this.passwordGenerator = passwordGenerator;
    }

    @Override
    public Result addCandidate(AddCandidateDTO addCandidateDTO) {
        if (addCandidateDTO == null) {
            return new Result(false, "Кандидатът не съществува.");
        }

        Optional<Candidate> optionalByEmail = this.candidateRepository.findByEmail(addCandidateDTO.getEmail());
        Optional<Candidate> optionalByPhoneNumber = this.candidateRepository
                .findByPhoneNumber(addCandidateDTO.getPhoneNumber());

        if (optionalByEmail.isPresent()) {
            return new Result(false, "Кандидат с този имейл вече съществува!");
        }

        if (optionalByPhoneNumber.isPresent()) {
            return new Result(false, "Кандидат с този телефонен номер вече съществува!");
        }

        Optional<Employee> optionalEmployeeByEmail = this.employeeService.findEmployeeByEmail(addCandidateDTO.getEmail());
        Optional<Employee> optionalEmployeeByPhoneNumber = this.employeeService
                .findEmployeeByPhoneNumber(addCandidateDTO.getPhoneNumber());

        if (optionalEmployeeByEmail.isPresent()) {
            return new Result(false, "Служител с този имейл вече съществува!");
        }

        if (optionalEmployeeByPhoneNumber.isPresent()) {
            return new Result(false, "Служител с този телефонен номер вече съществува!");
        }

        boolean isEducationLevelValid = this.isEducationLevelValid(addCandidateDTO.getEducation());

        if (!isEducationLevelValid) {
            return new Result(false, "Невалидно образователно ниво!");
        }

        Candidate candidate = this.modelMapper.map(addCandidateDTO, Candidate.class);
        candidate.setDate(LocalDate.now());

        if (candidate.getEducation().equals(EducationLevel.UNIVERSITY_DEGREE)) {
            if (addCandidateDTO.getSpecialty().isBlank()) {
                return new Result(false, "След като сте посочили Висше като степен на образование " +
                        "моля посочете и специалността, която сте завършили!");
            }

            candidate.setSpecialty(addCandidateDTO.getSpecialty());
        } else {
            candidate.setSpecialty("");
        }

        Set<Language> languages = this.languageService.processLanguages(addCandidateDTO.getLanguages());

        candidate.setLanguages(languages);

        this.candidateRepository.saveAndFlush(candidate);

        this.applicationEventPublisher.publishEvent(
                new AddCandidateEvent(this, candidate.getFirstName(),
                        candidate.getLastName(),
                        candidate.getEmail(),
                        candidate.getPhoneNumber(),
                        candidate.getAddress(),
                        this.mapEducationLevel(candidate.getEducation()),
                        candidate.getSpecialty(),
                        this.mapLanguagesToStringFormat(candidate.getLanguages()),
                        candidate.getDate()));

        return new Result(true, "Кандидатурата Ви беше изпратена успешно! " +
                "Моля изчакайте търпеливо да бъде разгледана и наш служител ще се свърже с Вас!");
    }

    @Override
    public CandidatesViewInfo getAllCandidates() {
        List<Candidate> candidates = this.candidateRepository.findAll();

        List<CandidateDTO> candidateDTOList = candidates.stream()
                .map(candidate -> {
                    CandidateDTO candidateDTO = this.modelMapper.map(candidate, CandidateDTO.class);

                    String education = this.mapEducationLevel(candidate.getEducation());
                    candidateDTO.setEducation(education);

                    String languages = this.mapLanguagesToStringFormat(candidate.getLanguages());
                    candidateDTO.setLanguages(languages);

                    return candidateDTO;
                })
                .toList();

        return new CandidatesViewInfo(candidateDTOList);
    }

    @Override
    public Result hireEmployee(Long id) {
        Optional<Candidate> optionalCandidate = this.candidateRepository.findById(id);

        if (optionalCandidate.isEmpty()) {
            return new Result(false, "Не е намерен кандидат с това id.");
        }

        Candidate candidate = optionalCandidate.get();

        Employee employee = this.mapCandidateToEmployee(candidate);

        if (employee == null) {
            return new Result(false, "Нещо се обърка! Този кандидат не беше успешно назначен на работа");
        }

        this.candidateRepository.deleteById(id);

        Optional<Candidate> optionalCandidateAfterDeletion = this.candidateRepository.findById(id);

        if (optionalCandidateAfterDeletion.isPresent()) {
            return new Result(false, "Нещо се обърка! Кандидатът не беше успешно изтрит от базата данни.");
        }

        this.employeeService.saveAndFlushEmployee(employee);

        this.applicationEventPublisher.publishEvent(
                new HireEmployeeEvent(this, employee.getFullName(),
                        employee.getEmail(),
                        employee.getPhoneNumber(),
                        employee.getAddress(),
                        this.mapEducationLevel(employee.getEducation()),
                        employee.getSpecialty(),
                        this.mapLanguagesToStringFormat(employee.getLanguages()),
                        this.rawPassword,
                        employee.getHiredOn()));

        return new Result(true, "Този кандидат беше успешно назначен на работа!");
    }

    @Override
    public Result deleteCandidateById(Long id) {
        Optional<Candidate> optionalCandidate = this.candidateRepository.findById(id);

        if (optionalCandidate.isEmpty()) {
            return new Result(false, "Не е намерен кандидат с това id.");
        }

        this.candidateRepository.deleteById(id);

        Optional<Candidate> optionalCandidateAfterDeletion = this.candidateRepository.findById(id);

        if (optionalCandidateAfterDeletion.isPresent()) {
            return new Result(false, "Нещо се обърка! Кандидатът не беше успешно изтрит от базата данни!");
        }

        return new Result(true, "Успешно отхвърлихте този кандидат!");
    }

    private Employee mapCandidateToEmployee(Candidate candidate) {
        Employee employee = new Employee();

        Optional<Role> optionalRole = this.roleRepository.findByRoleName(RoleName.EMPLOYEE);

        if (optionalRole.isEmpty()) {
            return null;
        }

        String fullName = candidate.getFirstName() + " " + candidate.getLastName();
        this.rawPassword = this.passwordGenerator.generatePassword();

        employee.setFullName(fullName);
        employee.setRole(optionalRole.get());
        employee.setEmail(candidate.getEmail());
        employee.setPhoneNumber(candidate.getPhoneNumber());
        employee.setAddress(candidate.getAddress());
        employee.setPassword(this.passwordEncoder.encode(this.rawPassword));
        employee.setEducation(candidate.getEducation());
        employee.setSpecialty(candidate.getSpecialty());
        employee.setLanguages(candidate.getLanguages());
        employee.setHiredOn(LocalDate.now());

        return employee;
    }

    private String mapLanguagesToStringFormat(Set<Language> languages) {
        return languages.stream()
                .map(Language::getName)
                .collect(Collectors.joining(", "));
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

    private boolean isEducationLevelValid(EducationLevel educationLevel) {
        return Arrays.asList(EducationLevel.values()).contains(educationLevel);
    }
}
