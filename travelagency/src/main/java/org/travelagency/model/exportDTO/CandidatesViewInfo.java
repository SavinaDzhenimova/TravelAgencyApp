package org.travelagency.model.exportDTO;

import java.util.List;

public class CandidatesViewInfo {

    List<CandidateDTO> candidates;

    public CandidatesViewInfo(List<CandidateDTO> candidates) {
        this.candidates = candidates;
    }

    public List<CandidateDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidateDTO> candidates) {
        this.candidates = candidates;
    }
}