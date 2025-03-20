package org.travelagency.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Day;
import org.travelagency.repository.DayRepository;
import org.travelagency.service.interfaces.DayService;

import java.util.List;
import java.util.Optional;

@Service
public class DayServiceImpl implements DayService {

    private final DayRepository dayRepository;

    public DayServiceImpl(DayRepository dayRepository) {
        this.dayRepository = dayRepository;
    }

    @Override
    public void saveAndFlushDay(Day day) {
        this.dayRepository.saveAndFlush(day);
    }

    @Override
    public Optional<Day> findDayById(Long id) {
        return this.dayRepository.findById(id);
    }

    @Override
    @Transactional
    public void deleteDayById(Long id) {
        this.dayRepository.deleteById(id);
    }

    @Override
    public List<Day> findAllDaysByProgramId(Long programId) {
        return this.dayRepository.findAllByProgramId(programId);
    }

    @Override
    @Transactional
    public void deleteAllDaysByProgramId(Long id) {
        this.dayRepository.deleteAllByProgramId(id);
    }
}
