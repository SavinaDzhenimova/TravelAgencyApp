package org.travelagency.model.exportDTO.day;

public class DayExportDTO {

    private Long id;

    private String description;

    public DayExportDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
