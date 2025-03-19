package org.travelagency.service.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.travelagency.model.entity.Excursion;
import org.travelagency.model.entity.Reservation;
import org.travelagency.repository.ExcursionRepository;
import org.travelagency.repository.ReservationRepository;
import org.travelagency.service.utils.ExcursionDeletionManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CronScheduler {

    private final Logger LOGGER = LoggerFactory.getLogger(CronScheduler.class);
    private final ExcursionRepository excursionRepository;
    private final ReservationRepository reservationRepository;
    private final ExcursionDeletionManager excursionDeletionManager;

    public CronScheduler(ExcursionRepository excursionRepository, ReservationRepository reservationRepository,
                         ExcursionDeletionManager excursionDeletionManager) {
        this.excursionRepository = excursionRepository;
        this.reservationRepository = reservationRepository;
        this.excursionDeletionManager = excursionDeletionManager;
    }

    @Scheduled(cron = "0 0 4 * * *") // Всяка нощ в 04:00
    public void deletePastDates() throws IOException {
        List<Excursion> excursions = this.excursionRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Excursion excursion : excursions) {
            Set<LocalDate> futureDates = excursion.getDates()
                    .stream()
                    .filter(date -> !date.isBefore(currentDate))
                    .collect(Collectors.toSet());

            if (excursion.getDates().size() != futureDates.size()) {
                if (futureDates.isEmpty()) {
                    this.excursionDeletionManager.deleteExcursion(excursion.getId());

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

    @Scheduled(cron = "0 0 3 * * *") // Всяка нощ в 03:00
    public void deletePastReservations() {
        List<Reservation> reservations = this.reservationRepository.findAll();
        LocalDate currentDate = LocalDate.now();

        for (Reservation reservation : reservations) {
            if (reservation.getExcursionDate().isBefore(currentDate)) {
                this.reservationRepository.deleteById(reservation.getId());

                LOGGER.info("Deleted reservation with ID: {} because the date is in the past.", reservation.getId());
            }
        }

        LOGGER.info("Checked and deleted reservations with past dates.");
    }
}
