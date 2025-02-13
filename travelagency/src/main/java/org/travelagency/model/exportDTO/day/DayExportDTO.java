package org.travelagency.model.exportDTO.day;

public class DayExportDTO {

    private int dayNumber;

    private String description;

    public DayExportDTO() {
    }

    public int getDayNumber() {
        return dayNumber;
    }

    public void setDayNumber(int dayNumber) {
        this.dayNumber = dayNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
