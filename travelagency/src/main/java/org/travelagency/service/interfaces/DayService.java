package org.travelagency.service.interfaces;

import jakarta.transaction.Transactional;
import org.travelagency.model.entity.Day;

import java.util.List;

public interface DayService {
    void saveAndFlushDay(Day day);

    List<Day> findAllDaysByProgramId(Long programId);

    @Transactional
    void deleteAllDaysByProgramId(Long id);
}
