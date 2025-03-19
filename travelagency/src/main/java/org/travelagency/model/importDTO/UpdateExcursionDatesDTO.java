package org.travelagency.model.importDTO;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class UpdateExcursionDatesDTO {

    private String excursionName;

    @NotNull(message = "Посочете брой дати за екскурзията!")
    @Positive(message = "Датите за екскурзията не може да бъдат по-малко от една!")
    private int datesCount;

    @NotEmpty(message = "Трябва да добавите поне една дата за екскурзията!")
    private Set<@FutureOrPresent LocalDate> dates;

    public UpdateExcursionDatesDTO() {
        this.datesCount = 1;
        this.dates = new HashSet<>();
    }

    public String getExcursionName() {
        return excursionName;
    }

    public void setExcursionName(String excursionName) {
        this.excursionName = excursionName;
    }

    public int getDatesCount() {
        return datesCount;
    }

    public void setDatesCount(int datesCount) {
        this.datesCount = datesCount;
    }

    public Set<LocalDate> getDates() {
        return dates;
    }

    public void setDates(Set<LocalDate> dates) {
        this.dates = dates;
    }
}
