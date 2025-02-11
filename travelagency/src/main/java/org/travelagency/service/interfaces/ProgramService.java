package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Program;

public interface ProgramService {
    void saveAndFlushProgram(Program program);
}
