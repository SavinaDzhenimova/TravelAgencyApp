package org.travelagency.model.importDTO;

import org.travelagency.model.enums.TransportType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AddExcursionDTO {

    private String name;

    private BigDecimal price;

    private TransportType transportType;

    private String destination;

    private LocalDateTime date;

    private int endurance;

    private String day;

    public AddExcursionDTO() {
        this.endurance = 1;
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

    public TransportType getTransportType() {
        return transportType;
    }

    public void setTransportType(TransportType transportType) {
        this.transportType = transportType;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
}
