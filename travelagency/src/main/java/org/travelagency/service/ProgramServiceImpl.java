package org.travelagency.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Program;
import org.travelagency.repository.ProgramRepository;
import org.travelagency.service.interfaces.ProgramService;

import java.util.Optional;

@Service
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository programRepository;

    public ProgramServiceImpl(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Override
    public void saveAndFlushProgram(Program program) {
        this.programRepository.saveAndFlush(program);
    }

    @Override
    public Optional<Program> findProgramById(Long id) {
        return this.programRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteProgramById(Long id) {
        this.programRepository.deleteProgramById(id);
    }
}
