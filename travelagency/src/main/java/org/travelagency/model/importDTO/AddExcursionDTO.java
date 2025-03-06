package org.travelagency.model.importDTO;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.web.multipart.MultipartFile;
import org.travelagency.model.enums.TransportType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AddExcursionDTO {

    @NotNull(message = "Въведете име на екскурзията!")
    private String name;

    @NotNull(message = "Въведете цена за един човек!")
    @Positive(message = "Цената не може да бъде по-малка или равна на 0 лв.")
    private BigDecimal price;

    @NotNull(message = "Изберете тип транспорт!")
    private TransportType transportType;

    @NotNull(message = "Изберете дестинация!")
    private String destination;

    @NotNull(message = "Посочете рой дати за екскурзията!")
    @Positive(message = "Датите за екскурзията не може да бъдат по-малко от една!")
    private int datesCount;

    @NotEmpty(message = "Трябва да добавите поне една дата за екскурзията!")
    private List<@FutureOrPresent LocalDate> dates;

    @NotNull(message = "Посочете продължителност на екскурзията!")
    @Positive(message = "Продължителността на екскурзията не може да бъде по-малко от 1 ден!")
    private int endurance;

    @NotEmpty(message = "Трябва да добавите поне един ден от програмата!")
    private List<@NotNull String> days;

    @NotEmpty(message = "Трябва да добавите поне една снимка свързана с екскурзията!")
    private List<MultipartFile> images;

    @NotNull(message = "Трябва да посочите ръководител на екскурзията!")
    @Positive(message = "Id на ръководителя не може да бъде по-малко от 1!")
    private Long guideId;

    public AddExcursionDTO() {
        this.endurance = 1;
        this.datesCount = 1;
        this.days = new ArrayList<>();
        this.images = new ArrayList<>();
        this.dates = new ArrayList<>();
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

    public int getDatesCount() {
        return datesCount;
    }

    public void setDatesCount(int datesCount) {
        this.datesCount = datesCount;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public void setDates(List<LocalDate> dates) {
        this.dates = dates;
    }

    public int getEndurance() {
        return endurance;
    }

    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }
}
