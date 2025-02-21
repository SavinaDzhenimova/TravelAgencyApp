package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.reservation.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;

import java.util.Map;

public interface ReservationService {

    Map<String, ReservationViewInfo> getAllReservationsGroupedByExcursionsNames();

    Result addReservation(AddReservationDTO addReservationDTO, String excursionName);
}
