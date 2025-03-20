package org.travelagency.service.interfaces;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.travelagency.model.entity.Reservation;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.reservation.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;

import java.time.LocalDate;
import java.util.List;

public interface ReservationService {

    Page<ReservationViewInfo> getAllReservationsGroupedByExcursionsNames(Pageable pageable);

    Result addReservation(AddReservationDTO addReservationDTO, String excursionName);

    void deleteAllReservationsByExcursionId(Long excursionId);

    @Transactional
    void deleteAllReservationsByExcursionDate(LocalDate date);

    List<Reservation> findAllReservationsByExcursionId(Long excursionId);

    List<Reservation> findAllReservationsByExcursionDate(LocalDate date);
}