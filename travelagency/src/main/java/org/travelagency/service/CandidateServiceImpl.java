package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.*;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.enums.RoleName;
import org.travelagency.model.exportDTO.CandidateDTO;
import org.travelagency.model.exportDTO.CandidatesViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.repository.CandidateRepository;
import org.travelagency.repository.RoleRepository;
import org.travelagency.service.interfaces.CandidateService;
import org.travelagency.service.interfaces.EmployeeService;
import org.travelagency.service.interfaces.LanguageService;

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

    public CandidateServiceImpl(CandidateRepository candidateRepository, EmployeeService employeeService,
                                LanguageService languageService, RoleRepository roleRepository,
                                ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.candidateRepository = candidateRepository;
        this.employeeService = employeeService;
        this.languageService = languageService;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
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

        boolean isEducationLevelValid = this.isEducationLevelValid(addCandidateDTO.getEducation());

        if (!isEducationLevelValid) {
            return new Result(false, "Невалидно образователно ниво!");
        }

        Candidate candidate = this.modelMapper.map(addCandidateDTO, Candidate.class);

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
    public Result getCandidateById(Long id) {
        Optional<Candidate> optionalCandidate = this.candidateRepository.findById(id);

        if (optionalCandidate.isEmpty()) {
            return new Result(false, "Не е намерен кандидат с това id.");
        }

        Candidate candidate = optionalCandidate.get();

        Employee employee = this.mapCandidateToEmployee(candidate);

        if (employee == null) {
            return new Result(false, "Нещо се обърка! Този кандидат не беше успешно назначен на работа");
        }

        boolean isCandidateDeleted = this.deleteCandidateById(id);

        if (!isCandidateDeleted) {
            return new Result(false, "Нещо се обърка! Кандидатът не беше успешно изтрит от базата данни.");
        }

        this.employeeService.saveAndFlushEmployee(employee);
        return new Result(true, "Този кандидат беше успешно назначен на работа!");
    }

    @Override
    public boolean deleteCandidateById(Long id) {
        this.candidateRepository.deleteById(id);

        Optional<Candidate> optionalCandidate = this.candidateRepository.findById(id);

        return optionalCandidate.isEmpty();
    }

    private Employee mapCandidateToEmployee(Candidate candidate) {
        Employee employee = new Employee();

        Optional<Role> optionalRole = this.roleRepository.findByRoleName(RoleName.EMPLOYEE);

        if (optionalRole.isEmpty()) {
            return null;
        }

        String fullName = candidate.getFirstName() + " " + candidate.getLastName();
        String username = this.cyrillicToLatinUsername(fullName);
        String password = username + candidate.getPhoneNumber();

        employee.setFullName(fullName);
        employee.setRole(optionalRole.get());
        employee.setUsername(username);
        employee.setEmail(candidate.getEmail());
        employee.setPhoneNumber(candidate.getPhoneNumber());
        employee.setAddress(candidate.getAddress());
        employee.setPassword(this.passwordEncoder.encode(password));
        employee.setEducation(candidate.getEducation());
        employee.setSpecialty(candidate.getSpecialty());
        employee.setLanguages(candidate.getLanguages());

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

    private String cyrillicToLatinUsername(String input) {
        Map<Character, String> cyrillicToLatin = new HashMap<>();

        cyrillicToLatin.put('А', "A");
        cyrillicToLatin.put('Б', "B");
        cyrillicToLatin.put('В', "V");
        cyrillicToLatin.put('Г', "G");
        cyrillicToLatin.put('Д', "D");
        cyrillicToLatin.put('Е', "E");
        cyrillicToLatin.put('Ж', "Zh");
        cyrillicToLatin.put('З', "Z");
        cyrillicToLatin.put('И', "I");
        cyrillicToLatin.put('Й', "Y");
        cyrillicToLatin.put('К', "K");
        cyrillicToLatin.put('Л', "L");
        cyrillicToLatin.put('М', "M");
        cyrillicToLatin.put('Н', "N");
        cyrillicToLatin.put('О', "O");
        cyrillicToLatin.put('П', "P");
        cyrillicToLatin.put('Р', "R");
        cyrillicToLatin.put('С', "S");
        cyrillicToLatin.put('Т', "T");
        cyrillicToLatin.put('У', "U");
        cyrillicToLatin.put('Ф', "F");
        cyrillicToLatin.put('Х', "Kh");
        cyrillicToLatin.put('Ц', "Ts");
        cyrillicToLatin.put('Ч', "Ch");
        cyrillicToLatin.put('Ш', "Sh");
        cyrillicToLatin.put('Щ', "Sht");
        cyrillicToLatin.put('Ъ', "A");
        cyrillicToLatin.put('Ь', "Y");
        cyrillicToLatin.put('Ю', "Yu");
        cyrillicToLatin.put('Я', "Ya");

        cyrillicToLatin.put('а', "a");
        cyrillicToLatin.put('б', "b");
        cyrillicToLatin.put('в', "v");
        cyrillicToLatin.put('г', "g");
        cyrillicToLatin.put('д', "d");
        cyrillicToLatin.put('е', "e");
        cyrillicToLatin.put('ж', "zh");
        cyrillicToLatin.put('з', "z");
        cyrillicToLatin.put('и', "i");
        cyrillicToLatin.put('й', "y");
        cyrillicToLatin.put('к', "k");
        cyrillicToLatin.put('л', "l");
        cyrillicToLatin.put('м', "m");
        cyrillicToLatin.put('н', "n");
        cyrillicToLatin.put('о', "o");
        cyrillicToLatin.put('п', "p");
        cyrillicToLatin.put('р', "r");
        cyrillicToLatin.put('с', "s");
        cyrillicToLatin.put('т', "t");
        cyrillicToLatin.put('у', "u");
        cyrillicToLatin.put('ф', "f");
        cyrillicToLatin.put('х', "kh");
        cyrillicToLatin.put('ц', "ts");
        cyrillicToLatin.put('ч', "ch");
        cyrillicToLatin.put('ш', "sh");
        cyrillicToLatin.put('щ', "sht");
        cyrillicToLatin.put('ъ', "a");
        cyrillicToLatin.put('ь', "y");
        cyrillicToLatin.put('ю', "yu");
        cyrillicToLatin.put('я', "ya");

        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder();

        for (char letter : input.toCharArray()) {
            if (cyrillicToLatin.containsKey(letter)) {
                result.append(cyrillicToLatin.get(letter));
            } else if (letter == ' ') {
                result.append('_');
            } else {
                result.append(letter);
            }
        }

        return result.toString();
    }
}
