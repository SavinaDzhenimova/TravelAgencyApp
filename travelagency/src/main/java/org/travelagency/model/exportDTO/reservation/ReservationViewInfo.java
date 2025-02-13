package org.travelagency.model.exportDTO.reservation;

import java.util.List;

public class ReservationViewInfo {

    List<ReservationViewDTO> reservations;

    public ReservationViewInfo(List<ReservationViewDTO> reservations) {
        this.reservations = reservations;
    }

    public List<ReservationViewDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationViewDTO> reservations) {
        this.reservations = reservations;
    }
}
