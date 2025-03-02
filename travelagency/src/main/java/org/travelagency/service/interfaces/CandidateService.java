package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.candidate.CandidatesViewInfo;
import org.travelagency.model.importDTO.AddCandidateDTO;

public interface CandidateService {

    Result addCandidate(AddCandidateDTO addCandidateDTO);

    CandidatesViewInfo getAllCandidates();

    Result hireEmployee(Long id);

    Result deleteCandidateById(Long id);
}
