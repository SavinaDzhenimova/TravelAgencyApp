package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.repository.ProgramRepository;
import org.travelagency.service.interfaces.ProgramService;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }
}
