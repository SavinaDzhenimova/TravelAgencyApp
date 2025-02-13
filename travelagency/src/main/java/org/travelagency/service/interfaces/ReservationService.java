package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.exportDTO.ReservationViewInfo;
import org.travelagency.model.importDTO.AddReservationDTO;

import java.util.Map;

public interface ReservationService {

    Result addReservation(AddReservationDTO addReservationDTO, String excursionName);

    Map<String, ReservationViewInfo> getAllReservationsGroupedByExcursionsNames();
}
