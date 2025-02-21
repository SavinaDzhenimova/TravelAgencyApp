package org.travelagency.model.exportDTO.reservation;

import java.util.List;

public class ReservationViewInfo {

    List<ReservationViewDTO> reservations;

    private int totalTourists;

    private String excursionName;

    public ReservationViewInfo(List<ReservationViewDTO> reservations) {
        this.reservations = reservations;
        this.totalTourists = 0;
    }

    public List<ReservationViewDTO> getReservations() {
        return reservations;
    }

    public void setReservations(List<ReservationViewDTO> reservations) {
        this.reservations = reservations;
    }

    public int getTotalTourists() {
        return totalTourists;
    }

    public void setTotalTourists(int totalTourists) {
        this.totalTourists = totalTourists;
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }
}
