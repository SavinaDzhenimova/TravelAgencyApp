package org.travelagency.service;

import org.springframework.stereotype.Service;
import org.travelagency.model.entity.Day;
import org.travelagency.repository.DayRepository;
import org.travelagency.service.interfaces.DayService;

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
}
