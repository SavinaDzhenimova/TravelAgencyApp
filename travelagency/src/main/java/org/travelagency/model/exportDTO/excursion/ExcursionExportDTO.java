package org.travelagency.model.exportDTO.excursion;

import org.travelagency.model.exportDTO.day.DayExportDTO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExcursionExportDTO {

    private String name;

    private BigDecimal price;

    private LocalDate date;

    private String transport;

    private String destination;

    private List<String> images;

    private int endurance;

    private int reservations;

    private List<DayExportDTO> days;

    public ExcursionExportDTO() {
        this.days = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public int getReservations() {
        return reservations;
    }

    public void setReservations(int reservations) {
        this.reservations = reservations;
    }

    public List<DayExportDTO> getDays() {
        return days;
    }

    public void setDays(List<DayExportDTO> days) {
        this.days = days;
    }
}