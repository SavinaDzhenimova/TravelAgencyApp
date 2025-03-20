package org.travelagency.model.importDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

public class UpdateExcursionProgramDTO {

    @NotNull(message = "Посочете брой добавени дни към екскурзията!")
    @Positive(message = "Броят добавени дни към екскурзията не може да бъде по-малко от 1 ден!")
    private int daysCount;

    @NotEmpty(message = "Трябва да добавите поне един ден към програмата!")
    private List<@NotEmpty String> days;

    public UpdateExcursionProgramDTO() {
        this.daysCount = 1;
        this.days = new ArrayList<>();
    }

    public int getDaysCount() {
        return daysCount;
    }

    public void setDaysCount(int daysCount) {
        this.daysCount = daysCount;
    }

    public List<String> getDays() {
        return days;
    }

    public void setDays(List<String> days) {
        this.days = days;
    }
}
