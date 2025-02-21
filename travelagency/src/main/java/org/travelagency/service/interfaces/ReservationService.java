package org.travelagency.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.reservation.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;

public interface ReservationService {

    Page<ReservationViewInfo> getAllReservationsGroupedByExcursionsNames(Pageable pageable);

    Result addReservation(AddReservationDTO addReservationDTO, String excursionName);
}
