package org.travelagency.service.interfaces;

import jakarta.transaction.Transactional;
import org.travelagency.model.entity.Program;

import java.util.Optional;

public interface ProgramService {
    void saveAndFlushProgram(Program program);

    Optional<Program> findProgramById(Long id);

    @Transactional
    void deleteProgramById(Long id);
}
