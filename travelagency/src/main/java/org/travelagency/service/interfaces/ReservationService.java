package org.travelagency.service.interfaces;

import org.travelagency.model.entity.Result;
import org.travelagency.model.importDTO.AddReservationDTO;

public interface ReservationService {

    Result addReservation(AddReservationDTO addReservationDTO, String excursionName);
}
