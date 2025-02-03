package org.travelagency.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Candidate;
import org.travelagency.model.entity.Language;
import org.travelagency.model.enums.EducationLevel;
import org.travelagency.model.importDTO.AddCandidateDTO;
import org.travelagency.repository.CandidateRepository;
import org.travelagency.service.interfaces.CandidateService;
import org.travelagency.service.interfaces.LanguageService;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;

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
    public boolean addCandidate(AddCandidateDTO addCandidateDTO) {
        if (addCandidateDTO == null) {
            return false;
        }

        Optional<Candidate> optionalCandidate = this.candidateRepository.findByEmail(addCandidateDTO.getEmail());

        if (optionalCandidate.isPresent()) {
            return false;
        }

        boolean isEducationLevelValid = this.isEducationLevelValid(addCandidateDTO.getEducation());

        if (!isEducationLevelValid) {
            return false;
        }

        Candidate candidate = this.modelMapper.map(addCandidateDTO, Candidate.class);

        if (candidate.getEducation().equals(EducationLevel.UNIVERSITY_DEGREE)) {
            if (addCandidateDTO.getSpecialty().isBlank()) {
                return false;
            }

            candidate.setSpecialty(addCandidateDTO.getSpecialty());
        } else {
            candidate.setSpecialty(null);
        }

        Set<Language> languages = this.languageService.processLanguages(addCandidateDTO.getLanguages());

        candidate.setLanguages(languages);

        this.candidateRepository.saveAndFlush(candidate);
        return true;
    }

    private boolean isEducationLevelValid(EducationLevel educationLevel) {
        return Arrays
                .asList(EducationLevel.values()).contains(educationLevel);
    }
}
