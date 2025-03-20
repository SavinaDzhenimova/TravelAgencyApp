package org.travelagency.service.interfaces;

import jakarta.transaction.Transactional;
import org.travelagency.model.entity.Day;

import java.util.List;
import java.util.Optional;

public interface DayService {
    void saveAndFlushDay(Day day);

    Optional<Day> findDayById(Long id);

    List<Day> findAllDaysByProgramId(Long programId);

    @Transactional
    void deleteAllDaysByProgramId(Long id);

    void deleteDayById(Long id);
}
