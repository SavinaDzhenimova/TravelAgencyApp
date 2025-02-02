package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.CandidateRepository;
import org.travelagency.service.interfaces.CandidateService;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }
}
