package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Candidate;
import org.travelagency.model.entity.Language;
import org.travelagency.model.entity.Result;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.exportDTO.CandidateDTO;
import org.travelagency.model.exportDTO.CandidatesViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.repository.CandidateRepository;
import org.travelagency.service.interfaces.CandidateService;
import org.travelagency.service.interfaces.LanguageService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final LanguageService languageService;
    private final ModelMapper modelMapper;

    public CandidateServiceImpl(CandidateRepository candidateRepository, LanguageService languageService, ModelMapper modelMapper) {
        this.candidateRepository = candidateRepository;
        this.languageService = languageService;
        this.modelMapper = modelMapper;
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
