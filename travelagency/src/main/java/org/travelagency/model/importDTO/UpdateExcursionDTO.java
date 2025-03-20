package org.travelagency.model.importDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.travelagency.model.enums.TransportType;
import org.travelagency.model.exportDTO.day.DayExportDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

public class UpdateExcursionDTO {

    @NotNull(message = "Въведете име на екскурзията!")
    private String excursionName;

    @NotNull(message = "Въведете цена за един човек!")
    @Positive(message = "Цената не може да бъде по-малка или равна на 0 лв.")
    private BigDecimal price;

    @NotNull(message = "Трябва да посочите ръководител на екскурзията!")
    private String guideName;

    @NotNull(message = "Трябва да посочите ръководител на екскурзията!")
    @Positive(message = "Id на ръководителя не може да бъде по-малко от 1!")
    private Long guideId;

    @NotNull(message = "Изберете дестинация!")
    private String destination;

    @NotNull(message = "Изберете тип транспорт!")
    private TransportType transport;

    @NotEmpty(message = "Трябва да добавите поне една дата за екскурзията!")
    private List<String> dates;

    @NotEmpty(message = "Трябва да добавите поне един ден от програмата!")
    private List<@NotNull DayExportDTO> days;

    public UpdateExcursionDTO() {
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getGuideName() {
        return guideName;
    }

    public void setGuideName(String guideName) {
        this.guideName = guideName;
    }

    public Long getGuideId() {
        return guideId;
    }

    public void setGuideId(Long guideId) {
        this.guideId = guideId;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public TransportType getTransport() {
        return transport;
    }

    public void setTransport(TransportType transport) {
        this.transport = transport;
    }

    public List<String> getDates() {
        return dates;
    }

    public void setDates(List<String> dates) {
        this.dates = dates;
    }

    public List<DayExportDTO> getDays() {
        return days;
    }

    public void setDays(List<DayExportDTO> days) {
        this.days = days;
    }
}
