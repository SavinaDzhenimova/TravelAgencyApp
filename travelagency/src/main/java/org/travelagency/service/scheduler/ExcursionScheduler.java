package org.travelagency.service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Excursion;
import org.travelagency.repository.ExcursionRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExcursionScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(ExcursionScheduler.class);
    private final ExcursionRepository excursionRepository;

    public ExcursionScheduler(ExcursionRepository excursionRepository) {
        this.excursionRepository = excursionRepository;
    }

    @Scheduled(cron = "0 0 3 * * *") // Всяка нощ в 03:00
    public void deletePastDates() {
        List<Excursion> excursions = this.excursionRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Excursion excursion : excursions) {
            List<LocalDate> futureDates = excursion.getDates()
                    .stream()
                    .filter(date -> !date.isBefore(currentDate))
                    .collect(Collectors.toList());

            if (excursion.getDates().size() != futureDates.size()) {
                if (futureDates.isEmpty()) {
                    this.excursionRepository.delete(excursion);

                    LOGGER.info("Deleted excursion with ID: {} because all dates were in the past.", excursion.getId());
                } else {
                    excursion.setDates(futureDates);
                    this.excursionRepository.saveAndFlush(excursion);

                    LOGGER.info("Updated excursion with ID: {} by removing past dates.", excursion.getId());
                }
            }
        }

        LOGGER.info("Checked and updated excursions with past dates.");
    }
}
